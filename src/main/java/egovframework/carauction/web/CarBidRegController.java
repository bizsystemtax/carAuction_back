package egovframework.carauction.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.service.CarAucInfService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * 차량 입찰 정보 조회 컨트롤러
 */
@RestController
@Transactional
@RequestMapping("/carAucInf")
public class CarBidRegController {
	
	private static final Logger logger = LoggerFactory.getLogger(CarBidRegController.class);
	
	@Resource(name = "carAucInfService")
	private CarAucInfService carAucInfService;
	
	/**
	 * 차량 상세 정보 조회 (키값 기반)
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/car")
	public ResultVO getCarDetail(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		String aucRegNo = requestParams.get("aucRegNo");
		
		CarInfoVO carInfo = carAucInfService.getCarDetail(aucRegNo);
		
		// 단일 객체를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", carInfo);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 은행 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/banks")
	public ResultVO getBanks(@RequestBody(required = false) Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		List<String> banks = carAucInfService.getBanks();
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", banks);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 계좌번호 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/accounts")
	public ResultVO getAccountNumbers(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		String bank = requestParams.get("bank");
		
		List<String> accounts = carAucInfService.getAccountNumbers(bank);
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", accounts);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 입찰 등록
	 */
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "등록 성공"),
	    @ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/bid")
	public ResultVO submitBid(
	        @RequestBody Map<String, Object> requestParams,
	        HttpServletRequest request,
	        @Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) throws Exception {
	    
	    ResultVO resultVO = new ResultVO();
	    
	    /**
	     * 사용자 로그인(세션) 확인
	     */
	    String userId = null;
	    String userIp = null;
	    
	    if (user != null && !StringUtils.defaultString(user.getId()).isEmpty()) {
	        userId = user.getId();
	        userIp = user.getIp();
	        logger.info("로그인 사용자: {} (IP: {})", userId, userIp);
	    } else {
	        // 로그인 안 되어 있으면 에러 처리
	        throw new BizException(ErrorCode.ERR300, "로그인이 필요합니다.");
	    }
	    
	    /**
	     * 필수 파라미터 확인
	     */
	    String aucRegNo = (String) requestParams.get("aucRegNo");
	    if (aucRegNo == null || aucRegNo.isEmpty()) {
	        throw new BizException(ErrorCode.ERR001, "차량번호가 없습니다.");
	    }
	    
	    /**
	     * 입찰 이력 확인 - 이미 입찰한 경우 차단
	     */
	    int bidHistoryCount = carAucInfService.checkUserBidHistory(aucRegNo, userId);
	    if (bidHistoryCount > 0) {
	        logger.warn("중복 입찰 시도 - 사용자: {}, 차량: {}", userId, aucRegNo);
	        throw new BizException(ErrorCode.ERR017);
	    }
	    
	    Integer bidAmount = (Integer) requestParams.get("bidAmount");
	    String depositorName = (String) requestParams.get("depositorName");
	    String bankName = (String) requestParams.get("bankName");
	    String accountNumber = (String) requestParams.get("accountNumber");
	    Integer bidPlnPrice = (Integer) requestParams.get("bidPlnPrice");
	    logger.info("bidPlnPrice 값 확인: {}", bidPlnPrice);
	    // 입찰 보증금 계산 (예정가의 10%)
	    Integer bidSdepPrice = (int) Math.floor(bidPlnPrice * 0.1);
	    String contCellNo = (String) requestParams.get("contCellNo");           
	    String contEmailAddr = (String) requestParams.get("contEmailAddr");     
	    
	    /**
	     * 입찰 데이터 설정
	     */
	    Map<String, Object> bidData = new HashMap<>();
	    bidData.put("aucRegNo", aucRegNo);
	    bidData.put("bidAmount", bidAmount);
	    bidData.put("depositorName", depositorName);
	    bidData.put("bankName", bankName);
	    bidData.put("accountNumber", accountNumber);
	    bidData.put("bidSdepPrice", bidSdepPrice);
	    bidData.put("contCellNo", contCellNo);                  
	    bidData.put("contEmailAddr", contEmailAddr);            
	    
	    // 로그인 사용자 정보
	    bidData.put("entryIdno", userId);
	    bidData.put("updatIdno", userId);
	    
	    logger.info("입찰 등록 데이터: {}", bidData);
	    
	    /**
	     * 입찰 등록 처리
	     */
	    carAucInfService.insertBid(bidData);
	    
	    resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
	    resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
	    
	    return resultVO;
	}
}
	