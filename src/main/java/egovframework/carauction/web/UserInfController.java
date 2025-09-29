package egovframework.carauction.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.UserInfVO;
import egovframework.carauction.service.UserInfService;
import egovframework.com.cmm.DateUtil;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
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
	 * @param  requestParams - inVltDtStrt, inVltDtEnd, inVltKindCd, inSendPlcCd, inFineUploadCd, inCfmtYn, inGdCd, inCsNm, inVhclNo
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
		
		System.out.println("\n\n");
		System.out.println("[회원관리 목록 조회 결과]\n" + "ResultCode : " + resultVO.getResultCode());
		System.out.println("\n\n");
		
		return resultVO;
	}
}