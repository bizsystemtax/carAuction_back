package egovframework.carauction.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.NoticeVO;
import egovframework.carauction.service.NoticeService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/notice")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	
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
		String title = requestParams.get("noticeTit");		// 제목
		
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
	@PostMapping(value = "/insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResultVO insNotice(
			@RequestParam(value = "noticeTit", required = false) String noticeTit,
	        @RequestParam(value = "noticeCtnt", required = false) String noticeCtnt,
			//@RequestPart(value = "files", required = false) List<MultipartFile> files,
	        @RequestParam(value = "files", required = false) List<MultipartFile> files,
		    @AuthenticationPrincipal LoginVO user
			) throws Exception{
		
		logger.info("noticeTit !!!!!!!!!!!!!!!!!!!!!!!!!!! {}", noticeTit);
		logger.info("noticeCtnt !!!!!!!!!!!!!!!!!!!!!!!!!!! {}", noticeCtnt);
		logger.info("files !!!!!!!!!!!!!!!!!!!!!!!!!!! {}", files);
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> param = new HashMap<>();		
		param.put("noticeTit", noticeTit);		//공지사항 제목
		param.put("noticeCtnt", noticeCtnt); 	//공지사항 내용
		param.put("entryIdno", user.getId());   //등록자ID
		param.put("updatIdno", user.getId());   //수정자ID
		
		int result = noticeService.insNotice(param, files);
		
		if(result < 1) {
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ErrorCode.ERR005.getMessage());
			return resultVO;
		}

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
	@PostMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResultVO updNotice(
			@RequestParam Map<String, String> params,
	        //@RequestPart(value = "files", required = false) List<MultipartFile> files,
			@RequestParam(value = "files", required = false) List<MultipartFile> files,
	        @AuthenticationPrincipal LoginVO user) throws Exception {
		
		logger.info("noticeTit !!!!!!!!!!!!!!!!!!!!!!!!!!! {}", params.get("noticeTit"));
		logger.info("noticeCtnt !!!!!!!!!!!!!!!!!!!!!!!!!!! {}", params.get("noticeCtnt"));
		logger.info("files !!!!!!!!!!!!!!!!!!!!!!!!!!! {}", files);
		
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> param = new HashMap<>();		
		param.put("noticeTit", params.get("noticeTit"));		//공지사항 제목
		param.put("noticeCtnt", params.get("noticeCtnt")); 	//공지사항 내용
		param.put("noticeId", Integer.parseInt((String) params.get("noticeId"))); 	//공지사항 내용
		param.put("entryIdno", user.getId());   //등록자ID
		param.put("updatIdno", user.getId());   //수정자ID
		
		param.put("updatIdno", user.getId());
		
		int result = noticeService.updNotice(param, files);
		
		if(result < 1) {
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ErrorCode.ERR005.getMessage());
			return resultVO;
		}
		
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
	@PatchMapping(value = "/delete/{noticeId}")
	public ResultVO delNotice(@PathVariable("noticeId") int noticeId, @AuthenticationPrincipal LoginVO user) throws Exception {
		
		NoticeVO noticeVO = new NoticeVO();
		ResultVO resultVO = new ResultVO();
		
		noticeVO.setNoticeId(noticeId);
		noticeVO.setUpdatIdno(user.getId());
		
		int result = noticeService.delNotice(noticeVO);
		
		if(result < 1) {
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ErrorCode.ERR005.getMessage());
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	
}