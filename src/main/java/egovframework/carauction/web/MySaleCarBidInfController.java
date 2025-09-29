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

import egovframework.carauction.MyPageVO;
import egovframework.carauction.service.MyPageservice;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/myPage")
public class MySaleCarBidInfController {
	
	private static final Logger logger = LoggerFactory.getLogger(MySaleCarBidInfController.class);
	
	@Resource(name = "myPageservice")
	private MyPageservice myPageService;
	
	/**
	 * 인터넷 차량경매(공매) 시스템 MyPage 조회 컨트롤러
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	
	//콤보박스 진행상태 조회
	@PostMapping(value = "/comboBoxList")
	public ResultVO comboBoxList() throws Exception {
		MyPageVO myPageVO = new MyPageVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = myPageService.comboBoxList(myPageVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	@PostMapping(value = "/list") 
	public ResultVO myPageList(@RequestBody Map<String, String> requestParams) throws Exception{
		MyPageVO myPageVO = new MyPageVO();
		ResultVO resultVO = new ResultVO();
		
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();  
		
		String regStrDt = requestParams.get("fromDt").replaceAll("-", "");	//등록시작일자
		String regEndDt = requestParams.get("toDt").replaceAll("-", "");	//등록종료일자
		String proState = requestParams.get("codeNo");					//진행상태
			
		myPageVO.setRegStrDt(regStrDt);	//등록시작일자
		myPageVO.setRegEndDt(regEndDt); //등록종료일자
		myPageVO.setProState(proState); //진행상태
		myPageVO.setEntryIdno(loginVO.getId());
		
		logger.info("regStrDt ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", regStrDt);
		logger.info("regEndDt ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", regEndDt);
		logger.info("getId 	  ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", loginVO.getId());
		logger.info("proState ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", proState);
		logger.info("myPageVO ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", myPageVO);
		
		Map<String, Object> resultMap = myPageService.myPageList(myPageVO);
		
		logger.info("resultMap ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>> {} ", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
