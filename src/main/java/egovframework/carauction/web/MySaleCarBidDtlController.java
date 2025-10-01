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

import egovframework.carauction.MyPageVO;
import egovframework.carauction.service.MyPageservice;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

@RestController
@Transactional
@RequestMapping("/myPage")
public class MySaleCarBidDtlController {
	
	private static final Logger logger = LoggerFactory.getLogger(MySaleCarBidDtlController.class);
	
	@Resource(name = "myPageservice")
	private MyPageservice myPageService;
	
	/**
	 * 인터넷 차량경매(공매) 시스템 조회 컨트롤러
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용 )
	@PostMapping(value = "/mySaleCarAuctionRegList") 
	public ResultVO mySaleCarAuctionRegList(@RequestBody Map<String, String> requestParams) throws Exception{
		MyPageVO myPageVO = new MyPageVO();
		ResultVO resultVO = new ResultVO();
		
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		String aucRegNo = requestParams.get("aucRegNo");	//경매등록번호
		myPageVO.setEntryIdno(loginVO.getId());
		
		myPageVO.setAucRegNo(aucRegNo); //경매등록번호
		
		logger.info("aucRegNo ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", aucRegNo);
		
		Map<String, Object> resultMap = myPageService.mySaleCarAuctionRegList(myPageVO);
		
		logger.info("resultMap ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용) >>>> 유찰
	@PostMapping(value = "/faileBidUpdate") 
	public ResultVO faileBidUpdate(
			MyPageVO myPageVO,
			BindingResult bindingResult,
			HttpServletRequest request,
			@RequestBody Map<String, String> requestParams 
			) throws Exception {
		
		ResultVO resultVO = new ResultVO();
		
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		String aucRegNo = (String) requestParams.get("aucRegNo"); //경매등록번호

		myPageVO.setAucRegNo(aucRegNo); //경매등록번호
		
		myPageVO.setUpdatIdno(loginVO.getId());				//수정자

		Map<String, Object> resultMap = myPageService.faileBidUpdate(myPageVO);
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		
		return resultVO;
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황(입찰현황)
	@PostMapping(value = "/mySaleCarBidList") 
	public ResultVO mySaleCarBidList(@RequestBody Map<String, String> requestParams) throws Exception{
		MyPageVO myPageVO = new MyPageVO();
		ResultVO resultVO = new ResultVO();
		
		String aucRegNo = requestParams.get("aucRegNo");	//경매등록번호
		
		myPageVO.setAucRegNo(aucRegNo); //경매등록번호
		
		logger.info("aucRegNo ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", aucRegNo);
		
		Map<String, Object> resultMap = myPageService.mySaleCarBidList(myPageVO);
		
		logger.info("resultMap ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	
	//내 판매차량 입찰 상세 현황 - 콤보박스 진행상태 조회
	@PostMapping(value = "/statusComboBoxList")
	public ResultVO statusComboBoxList() throws Exception {
		MyPageVO myPageVO = new MyPageVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = myPageService.statusComboBoxList(myPageVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	//내 판매차량 입찰 상세 현황 - 저장
	@PostMapping(value = "/myBidInfoUpdate") 
	public ResultVO myBidInfoUpdate(
			MyPageVO myPageVO,
			BindingResult bindingResult,
			HttpServletRequest request,
			@RequestBody Map<String, String> requestParams 
			) throws Exception {
		
		ResultVO resultVO = new ResultVO();
		
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		String aucRegNo 		= (String) requestParams.get("aucRegNo"); 	 	//경매등록번호
		String aucRegSeq 		= (String) requestParams.get("aucRegSeq");  	//경매등록순번
		String bidPrice 		= (String) requestParams.get("bidPrice").replace("원", "").replace(",", "");    //입찰금액
		String depmnNm 			= (String) requestParams.get("depmnNm");      	//낙찰자명
		String aucProgStatCd 	= (String) requestParams.get("aucProgStatCd");  //진행상태
		String pomPayYn 		= (String) requestParams.get("pomPayYn");     	//대금납부완납여부
		
		logger.info("aucRegNo ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", aucRegNo);
		logger.info("aucRegSeq ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", aucRegSeq);
		logger.info("bidPrice ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", bidPrice);
		logger.info("depmnNm ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", depmnNm);
		logger.info("aucProgStatCd ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", aucProgStatCd);
		logger.info("pomPayYn ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", pomPayYn);
		
		myPageVO.setAucRegNo(aucRegNo);   			//경매등록번호
		myPageVO.setAucRegSeq(aucRegSeq); 			//경매등록순번
		myPageVO.setBidPrice(bidPrice);         	//입찰금액
		myPageVO.setDepmnNm(depmnNm);  				//낙찰자명
		myPageVO.setAucProgStatCd(aucProgStatCd);  	//진행상태
		myPageVO.setPomPayYn(pomPayYn);  			//대금납부완납여부
		myPageVO.setUpdatIdno(loginVO.getId()); 	//수정자ID

		Map<String, Object> resultMap = myPageService.myBidInfoUpdate(myPageVO);
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		
		return resultVO;
	}

}
