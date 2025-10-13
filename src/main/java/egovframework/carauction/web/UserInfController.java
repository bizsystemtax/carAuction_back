package egovframework.carauction.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.UserInfVO;
import egovframework.carauction.service.UserInfService;
import egovframework.com.cmm.DateUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.PasswordUtil;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.utl.sim.service.EgovFileScrty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/userInf")
public class UserInfController {
	
	@Resource(name = "UserInfService")
	private UserInfService userInfService;
	
	/**
	 * 회원관리 목록 조회
	 * @param  requestParams - inStartDate, inEndDate, inUserGbCd, inUserNm
	 * @return resultVO - 회원관리 목록
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/list")
	public ResultVO userInfListSelect(@RequestBody Map<String, String> requestParams) throws Exception{
		UserInfVO userInfVO = new UserInfVO();
		ResultVO resultVO = new ResultVO();
		
		String inStartDate = requestParams.get("inStartDate");	// 가입일자시작
		String inEndDate = requestParams.get("inEndDate");  	// 가입일자종료
		String inUserGbCd = requestParams.get("inUserGbCd");	// 회원구분코드
		String inUserNm = requestParams.get("inUserNm");		// 회원명
		
		// datepicker 날짜 형식 'YYYYMMDD'으로 변경
		inStartDate = DateUtil.dateFormatYyyyMmDd(inStartDate);
		inEndDate = DateUtil.dateFormatYyyyMmDd(inEndDate);
		
		// 회원구분코드 코드값 변경
		String inUserGbCdFormat = "";
		
		if(inUserGbCd != null && !inUserGbCd.trim().isEmpty()) {
			List<String> list = Arrays.stream(inUserGbCd.split(","))
						            .map(String::trim)      	// 앞뒤 공백 제거
						            .filter(s -> !s.isEmpty()) 	// 빈 문자열 제거
						            .collect(Collectors.toList());
			
			for(int i=0; i<list.size(); i++) {
				if(!"".equals(inUserGbCdFormat)) {
					inUserGbCdFormat += ",";
				}
				
				switch (list.get(i)) {
					case "1":
						inUserGbCdFormat += "'0020'";
						break;
					case "2":
						inUserGbCdFormat += "'0030'";
						break;
					case "3":
						inUserGbCdFormat += "'0040'";
						break;
					case "4":
						inUserGbCdFormat += "'0050'";
						break;
					case "5":
						inUserGbCdFormat += "'0060'";
						break;
				}
			}
		}
		
		/**
		 * VO input 매핑
		 */
		userInfVO.setInStartDate(inStartDate);
		userInfVO.setInEndDate(inEndDate);
		userInfVO.setInUserGbCd(inUserGbCdFormat);
		userInfVO.setInUserNm(inUserNm);

		/**
		 * 회원관리 목록 조회
		 */
		Map<String, Object> resultMap = userInfService.userInfListSelect(userInfVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}

	/**
	 * 회원 수정 모달 정보 조회
	 * @param  requestParams - inUserId
	 * @return resultVO - 회원정보
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/updateModalData")
	public ResultVO userInfUpdateModalDataSelect(@RequestBody Map<String, String> requestParams) throws Exception{
		UserInfVO userInfVO = new UserInfVO();
		ResultVO resultVO = new ResultVO();
		
		String inUserId = requestParams.get("inUserId"); // 사용자ID
		
		if(inUserId == null || "".equals(inUserId)) {
			throw new BizException(ErrorCode.ERR001, "");
		}
		
		/**
		 * VO input 매핑
		 */
		userInfVO.setInUserId(inUserId);
		
		/**
		 * 회원 수정 모달 정보 조회
		 */
		Map<String, Object> resultMap = userInfService.userInfUpdateModalDataSelect(userInfVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}

	/**
	 * 비밀번호 초기화
	 * @param  requestParams - inUserId
	 * @return resultVO - tempPw
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/pwInit")
	public ResultVO userInfPwInitUpdate(
			@RequestBody Map<String, String> requestParams,
			HttpServletRequest request,
			@Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) throws Exception{
		
		UserInfVO userInfVO = new UserInfVO();
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<>();
		
		/**
		 * 사용자 로그인(세션) 확인
		 */
		String userId = user.getId();
		String userIp = user.getIp();
		
		if(!StringUtils.defaultString(userId).isEmpty()) {
			userInfVO.setSessionId(userId);
			userInfVO.setSessionIp(userIp);
		} else {
			throw new BizException(ErrorCode.ERR300, "");
		}
		
		/**
		 * 필수 파라미터 확인
		 */
		String inUserId = requestParams.get("inUserId"); // 사용자ID
		
		if(inUserId == null || "".equals(inUserId)) {
			throw new BizException(ErrorCode.ERR001, "");
		}
		
		/**
		 * 임시 비밀번호 생성
		 */
		// 임시 비밀번호 발급
		String tempPw = PasswordUtil.makeTempPassword();
//		String tempPw = "bizsystem#99"; // 테스트용
		
		// 임시 비밀번호 암호화
		String enPw = EgovFileScrty.encryptPassword(tempPw, inUserId);
		
		/**
		 * VO input 매핑
		 */
		userInfVO.setInUserId(inUserId);
		userInfVO.setInUserPw(enPw);
		
		/**
		 * 임시 비밀번호로 USER_PW 변경
		 */
		int resultCnt = userInfService.userInfPwInitUpdate(userInfVO);
		
		// 변경된 데이터가 없으면 사용자ID 값이 잘못됐거나 회원탈퇴 했을 수 있음.
		if(resultCnt <= 0) {
			throw new BizException(ErrorCode.ERR001, "");
		}
		
		resultMap.put("tempPw", tempPw);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
}