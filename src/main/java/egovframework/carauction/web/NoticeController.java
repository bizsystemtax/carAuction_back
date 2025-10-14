package egovframework.carauction.web;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.NoticeVO;
import egovframework.carauction.service.NoticeService;
import egovframework.com.cmm.FileUploadService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.utl.fcc.service.EgovFormBasedFileVo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/notice")
public class NoticeController {
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	
	@Resource(name = "fileUploadService")
	private FileUploadService fileUploadService;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileService;
	
	/**
	 * 공지사항 목록 조회
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	
	@PostMapping(value = "/list")
	public ResultVO noticeList(@RequestBody Map<String, String> requestParams) throws Exception{
		
		NoticeVO noticeVO = new NoticeVO();
		ResultVO resultVO = new ResultVO();
		
		// 조회 조건
		String startDt = requestParams.get("startDate");	// 시작일자
		String endDt = requestParams.get("endDate");		// 종료일자
		String title = requestParams.get("noticeTit");		// 제목
		
		noticeVO.setStartDt(startDt);
		noticeVO.setEndDt(endDt);
		noticeVO.setNoticeTit(title);
		
		Map<String, Object> resultMap = noticeService.noticeList(noticeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 공지사항 상세조회 
	 * @param  requestParams - noticeId
	 * @return resultVO
	 * @throws BizException
	 */
	@GetMapping(value = "/noticeDetail/{noticeId}")
	public ResultVO getNoticeDetail(@PathVariable("noticeId") int noticeId) throws Exception {
		
		NoticeVO noticeVO = new NoticeVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		noticeVO.setNoticeId(noticeId); 
		
		resultMap = noticeService.getNoticeDetail(noticeVO);
		
//		// 첨부파일 조회
//		AttachFileVO fileVO = new AttachFileVO();
//		fileVO.setTargetId(noticeId);
//		List<AttachFileVO> resultFiles = noticeService.getNotiFileList(fileVO);
//		
//		resultMap.put("resultFiles", resultFiles);
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	/**
	 * 공지사항 등록 
	 * @return resultVO
	 * @throws BizException
	 */
	@PostMapping(value = "/insert")
	public ResultVO insNotice(@RequestBody Map<String, Object> requestParams, @AuthenticationPrincipal LoginVO user) throws Exception{
		
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> param = (Map<String, Object>) requestParams.get("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String userId = "bizsystem";
		
		param.put("entryIdno", userId);
		param.put("updatIdno", userId);
		
		int result = noticeService.insNotice(param);
		
		if(result < 1) {
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ErrorCode.ERR005.getMessage());
		}
		
		///////////////////////////////////////////// 파일 업로드
		
		
		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	
	/**
	 * 공지사항 수정
	 * @param  requestParams - noticeId
	 * @return resultVO
	 * @throws BizException
	 */
	@PatchMapping(value = "/update/{noticeId}")
	public ResultVO updNotice(@RequestBody Map<String, Object> requestParams, @AuthenticationPrincipal LoginVO user) throws Exception{
		
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> param = (Map<String, Object>) requestParams.get("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
//		String userId = user.getId();
		String userId = "bizsystem";
		
		param.put("updatIdno", userId);
		
		int result = noticeService.updNotice(param);
		
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
	 * 공지사항 삭제 
	 * @param  requestParams - noticeId
	 * @return resultVO
	 * @throws BizException
	 */
	@PatchMapping(value = "/delNotice")
	public ResultVO delNotice(@RequestBody List<NoticeVO> noticeId, @AuthenticationPrincipal LoginVO user) throws Exception {
		
		NoticeVO noticeVO = new NoticeVO();
		ResultVO resultVO = new ResultVO();
		
//		String userId = user.getId();
		String userId = "bizsystem";
		
		for(NoticeVO notice : noticeId) {
			
			noticeVO.setUpdatIdno(userId);
			noticeVO.setNoticeId(notice.getNoticeId());
			noticeService.delNotice(noticeVO);
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	
}