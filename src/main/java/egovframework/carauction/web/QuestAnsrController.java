package egovframework.carauction.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.QuestAnsrVO;
import egovframework.carauction.service.QuestAnsrService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/questAnsr")
public class QuestAnsrController {
	
	@Resource(name = "questAnsrService")
	private QuestAnsrService questAnsrService;
	
	/**
	 * Q&A 목록 조회
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	
	@PostMapping(value = "/list")
	public ResultVO questAnsrlist(@RequestBody Map<String, String> requestParams) throws Exception{
		
		QuestAnsrVO questAnsrVO = new QuestAnsrVO();
		ResultVO resultVO = new ResultVO();
		
		// 조회 조건
		String startDt = requestParams.get("startDate");	// 시작일자
		String endDt = requestParams.get("endDate");		// 종료일자
		String title = requestParams.get("questTit");		// 제목
		
		questAnsrVO.setStartDt(startDt);
		questAnsrVO.setEndDt(endDt);
		questAnsrVO.setQuestTit(title);
		
		Map<String, Object> resultMap = questAnsrService.questAnsrList(questAnsrVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * Q&A 상세조회 
	 * @param  requestParams - questId
	 * @return resultVO
	 * @throws BizException
	 */
	@GetMapping(value = "/questAnsrDetail/{questId}")
	public ResultVO getQuestAnsrDetail(@PathVariable("questId") int questId) throws Exception {
		
		QuestAnsrVO questAnsrVO = new QuestAnsrVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		questAnsrVO.setQuestId(questId); 
		
		resultMap = questAnsrService.getQuestAnsrDetail(questAnsrVO);
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	/**
	 * Q&A 등록 
	 * @return resultVO
	 * @throws BizException
	 */
	@PostMapping(value = "/insert")
	public ResultVO insQuestAnsr(@RequestBody Map<String, Object> requestParams, @AuthenticationPrincipal LoginVO user) throws Exception{
		
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> param = (Map<String, Object>) requestParams.get("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String userId = user.getId();
		
		param.put("entryIdno", userId);
		param.put("updatIdno", userId);
		
		int result = questAnsrService.insQuestAnsr(param);
		
		if(result < 1) {
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ErrorCode.ERR005.getMessage());
		}
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	
	/**
	 * Q&A 수정
	 * @param  requestParams - questId
	 * @return resultVO
	 * @throws BizException
	 */
	@PatchMapping(value = "/update/{questId}")
	public ResultVO updQuestAnsr(@RequestBody Map<String, Object> requestParams, @AuthenticationPrincipal LoginVO user) throws Exception{
		
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> param = (Map<String, Object>) requestParams.get("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String userId = user.getId();
		
		param.put("updatIdno", userId);
		
		int result = questAnsrService.updQuestAnsr(param);
		
		if(result < 1) {
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ErrorCode.ERR005.getMessage());
		}
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	} 
	
	
	/**
	 * Q&A 삭제 
	 * @param  requestParams - questId
	 * @return resultVO
	 * @throws BizException
	 */
	@PatchMapping(value = "/delQuestAnsr")
	public ResultVO delQuestAnsr(@RequestBody List<QuestAnsrVO> questId, @AuthenticationPrincipal LoginVO user) throws Exception {
		
		QuestAnsrVO questAnsrVO = new QuestAnsrVO();
		ResultVO resultVO = new ResultVO();
		
		String userId = user.getId();
		
		for(QuestAnsrVO quest : questId) {
			
			questAnsrVO.setUpdatIdno(userId);
			questAnsrVO.setQuestId(quest.getQuestId());
			questAnsrService.delQuestAnsr(questAnsrVO);
		
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	
}