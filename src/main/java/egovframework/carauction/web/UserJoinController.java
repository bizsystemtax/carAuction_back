package egovframework.carauction.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.UserLoginVO;
import egovframework.carauction.service.UserJoinService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 회원가입 컨트롤러
 */
@RestController
@Transactional
@RequestMapping("/userLogin")
public class UserJoinController {
	
    @Resource(name = "userJoinService")
    private UserJoinService userJoinService;
	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);
	

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	
	/**
	 * 회원구분_코드 조회
	 */
	@PostMapping(value = "/userGbCdList")
	public ResultVO userGbCdList() throws Exception {
		UserLoginVO userLoginVO = new UserLoginVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = userJoinService.userGbCdList(userLoginVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	

	/**
	 * 은행코드 조회
	 */
	@PostMapping(value = "/bankCdList")
	public ResultVO bankCdList() throws Exception {
		UserLoginVO userLoginVO = new UserLoginVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = userJoinService.bankCdList(userLoginVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 사용자 등록
	 */
	@Operation(
			summary = "사용자 등록",
			description = "사용자 등록",
			security = {@SecurityRequirement(name = "Authorization")},
			tags = {"EgovBBSManageApiController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "등록 성공"),
			@ApiResponse(responseCode = "900", description = "입력값 무결성 오류")
	})
	@PostMapping(value ="/insertUser")
	public ResultVO insertUser(@RequestBody UserLoginVO userLoginVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		
		System.out.println("==============================" + userLoginVO.toString());

		logger.info("request >>>>>>>>>", request);
		
		// 입찰유효일(마감일)
//		String bidExpDt = "";
//		bidExpDt = userLoginVO.getBidExpDt();
//		bidExpDt = bidExpDt.replace("-", "");
//		userLoginVO.setBidExpDt(bidExpDt);

		userJoinService.insertUser(userLoginVO);
		

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}
}
