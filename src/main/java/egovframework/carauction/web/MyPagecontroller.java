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

import egovframework.carauction.myPageVO;
import egovframework.carauction.service.MyPageservice;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/myPage")
public class MyPagecontroller {
	
	private static final Logger logger = LoggerFactory.getLogger(MyPagecontroller.class);
	
	@Resource(name = "MyPageservice")
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
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	@PostMapping(value = "/list")  ///carAuction_back/src/main/java/egovframework/com/security/SecurityConfig.java 해당 경로에 추기 후 Mapping 주소 추가 해줘야 함
	public ResultVO myPageList(@RequestBody Map<String, String> requestParams) throws Exception{
		myPageVO myPageVO = new myPageVO();
		ResultVO resultVO = new ResultVO();
		
		String regStrDt = requestParams.get("fromDt").replaceAll("-", "");	//등록시작일자
		String regEndDt = requestParams.get("toDt").replaceAll("-", "");	//등록종료일자
		String proState = requestParams.get("proState");					//진행상태
		
		myPageVO.setRegStrDt(regStrDt);	//등록시작일자
		myPageVO.setRegEndDt(regEndDt); //등록종료일자
		myPageVO.setProState(proState); //진행상태
		
		logger.debug("myPageVO ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>>", myPageVO);
		
		Map<String, Object> resultMap = myPageService.myPageList(myPageVO);
		
		logger.debug("resultMap ■■■■■■■■■■■■■■■■■■■■■■■■■>>>>>>>>>", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}

}
