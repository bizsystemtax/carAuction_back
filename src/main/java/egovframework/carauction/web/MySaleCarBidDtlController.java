package egovframework.carauction.web;


import java.util.List;
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

import egovframework.carauction.MyPageSelectedVO;
import egovframework.carauction.MyPageUnSeledctedVO;
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
		
		Map<String, Object> resultMap = myPageService.mySaleCarAuctionRegList(myPageVO);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
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
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
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
		
		Map<String, Object> resultMap = myPageService.mySaleCarBidList(myPageVO);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
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
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
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
			@RequestBody MyPageVO requestBody
			) throws Exception {
		
		ResultVO resultVO = new ResultVO();
		
		List<MyPageSelectedVO> selected = requestBody.getSelected();
	    List<MyPageUnSeledctedVO> unselected = requestBody.getUnselected();
		
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		//입찰 현황에서 선택한 값
		for (int i = 0; i < selected.size(); i++) {
			MyPageSelectedVO item = selected.get(i);
			
			MyPageVO bidVO = new MyPageVO();
			
			bidVO.setAucRegNo(item.getAucRegNo());   		//경매등록번호
			bidVO.setAucRegSeq(item.getAucRegSeq()); 		//경매등록순번
			bidVO.setBidPrice(item.getBidPrice().replace("원", "").replace(",", ""));         	//입찰금액
			bidVO.setDepmnNm(item.getDepmnNm());  			//낙찰자명
			bidVO.setAucProgStatCd(item.getAucProgStatCd()); //진행상태
			bidVO.setPomPayYn(item.getPomPayYn());  		//대금납부완납여부
			bidVO.setUpdatIdno(loginVO.getId()); 			//수정자ID
			bidVO.setFlag(item.getFlag());                   //구분
			
			myPageService.myBidInfoSelectedUpdate(bidVO);

		}
		
		//입찰 현황에서 선택되지 않은 값
		for (int i = 0; i < unselected.size(); i++) {
			MyPageUnSeledctedVO item = unselected.get(i);
			
			MyPageVO bidVO = new MyPageVO();
			
			bidVO.setAucRegNo(item.getAucRegNo());   		//경매등록번호
			bidVO.setAucRegSeq(item.getAucRegSeq()); 		//경매등록순번
			bidVO.setDepmnNm(item.getDepmnNm());  			//낙찰자명
			bidVO.setAucProgStatCd(item.getAucProgStatCd()); //진행상태
			bidVO.setPomPayYn(item.getPomPayYn());  		//대금납부완납여부
			bidVO.setUpdatIdno(loginVO.getId()); 			//수정자ID
			bidVO.setFlag(item.getFlag());                   //구분
			
			myPageService.myBidInfoUnSelectedUpdate(bidVO);
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		
		return resultVO;
	}

}
