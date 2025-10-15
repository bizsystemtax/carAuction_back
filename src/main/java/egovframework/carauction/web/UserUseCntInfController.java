package egovframework.carauction.web;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.UserInfVO;
import egovframework.carauction.service.UserInfService;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/userInf")
public class UserUseCntInfController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserUseCntInfController.class);
	
	@Resource(name = "UserInfService")
	private UserInfService userInfService;
	
	/**
	 * 회원관리 회원 현황 조회
	 * @param  requestParams - startDate, endDate, selectCode
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	
	@PostMapping(value = "/cntList") 
	public ResultVO userUseCntInfList(@RequestBody Map<String, String> requestParams) throws Exception{
		UserInfVO userInfVO = new UserInfVO();
		ResultVO resultVO = new ResultVO();
		
		String regStrDt = requestParams.get("fromDt").replaceAll("-", "");	//등록시작일자
		String regEndDt = requestParams.get("toDt").replaceAll("-", "");	//등록종료일자
		String proState = requestParams.get("codeNo");						//조회구분코드
		String userId = requestParams.get("userId");							//조회등록자ID
		
		/**
		 * VO input 매핑
		 */
		userInfVO.setInRegStartDate(regStrDt);		//등록시작일자
		userInfVO.setInRegEndDate(regEndDt); 		//등록종료일자
		userInfVO.setInSearchCd(proState); 			//조회구분코드
		userInfVO.setInSearchUserId(userId);		//경매(공매)등록 회원사명
		logger.info("userInfVO =============== {} ", userInfVO);
		
		Map<String, Object> resultMap = userInfService.userUseCntInfList(userInfVO);
		logger.info("resultMap ================= {} ", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		System.out.println("\n\n");
		System.out.println("[회원관리 현황 건수 조회 결과]\n" + "ResultCode : " + resultVO.getResultCode());
		System.out.println("\n\n");
		
		return resultVO;
	}
	

}
