package egovframework.penalty.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.penalty.FineStdManageVO;
import egovframework.penalty.service.FineStdManageService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/fineStd")
public class FineStdManageController {
	@Resource(name = "FineStdManageService")
	private FineStdManageService fineStdManageService;
	
	/**
	 * 범칙금 발송처 기준관리 콤보박스 값을 조회한다.
	 * 
	 *
	 * @param 
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/comboBoxList")
	public ResultVO selectComboBoxList() throws Exception {
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = fineStdManageService.selectComboBoxList();
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 범칙금 발송처 기준관리 리스트를 조회한다.
	 * 
	 *
	 * @param requestParams
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/list")
	public ResultVO selectFineStdManageList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		FineStdManageVO paramVO = new FineStdManageVO();
		
		paramVO.setSendPlcCd(requestParams.get("send_plc_cd"));
		paramVO.setDocTypCd(requestParams.get("doc_typ_cd"));
		paramVO.setSendPlcKindCd(requestParams.get("send_plc_kind_cd"));
		paramVO.setRcptTypCd(requestParams.get("rcpt_typ_cd"));
		paramVO.setHdlgTypCd(requestParams.get("hdlg_typ_cd"));
		
		Map<String, Object> resultMap = fineStdManageService.selectFineStdManageList(paramVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 범칙금 발송처 기준관리 리스트를 조회한다.
	 * 
	 *
	 * @param requestParams
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/modalList")
	public ResultVO selectNtcdocSendPlcList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("ntcdocSendPlcNm", requestParams.get("ntcdoc_send_plc_nm"));
		
		Map<String, Object> resultMap = fineStdManageService.selectNtcdocSendPlcList(paramMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 범칙금 발송처를 저장한다.
	 * 
	 *
	 * @param requestParams
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/saveSendPlcData")
	public ResultVO insertSendPlcData(@RequestBody Map<String, Object> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String type = (String) requestParams.get("type");
		
		if (type.equals("insert")) {
			resultMap = fineStdManageService.insertSendPlcData((Map<String, Object>) requestParams.get("data"));
		} else if (type.equals("update")) {
			resultMap = fineStdManageService.updateSendPlcData((Map<String, Object>) requestParams.get("data"));
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 범칙금 발송처를 삭제한다.
	 * 
	 *
	 * @param requestParams
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/deleteSendPlcData")
	public ResultVO deleteSendPlcData(@RequestBody Map<String, Object> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = fineStdManageService.deleteSendPlcData((Map<String, Object>) requestParams);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
