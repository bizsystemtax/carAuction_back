package egovframework.penalty.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.penalty.ComnCdVO;
import egovframework.penalty.PenaltyStdManageVO;
import egovframework.penalty.service.PenaltyStdManageService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/penaltyStd")
public class PenaltyStdManageController {
	@Resource(name = "PenaltyStdManageService")
	private PenaltyStdManageService penaltyStdManageService;
	
	/**
	 * 범칙금 발송처 기준관리 콤보박스 값을 조회한다.
	 * 
	 *
	 * @param request
	 * @param paramVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/comboBoxList")
	public ResultVO selectComboBoxList() throws Exception {
		ComnCdVO paramVO = new ComnCdVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = penaltyStdManageService.selectComboBoxList(paramVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 범칙금 발송처 기준관리 리스트를 조회한다.
	 * 
	 *
	 * @param request
	 * @param paramVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/list")
	public ResultVO selectPenaltyStdManageList(@RequestBody Map<String, String> requestParams) throws Exception {
		String sendPldCd = requestParams.get("send_pld_cd");
		String docTypCd = requestParams.get("doc_typ_cd");
		String sendPlcKindCd = requestParams.get("send_plc_kind_cd");
		String rcptTypCd = requestParams.get("rcpt_typ_cd");
		String hdlgTypCd = requestParams.get("hdlg_typ_cd");
		
		PenaltyStdManageVO paramVO = new PenaltyStdManageVO();
		ResultVO resultVO = new ResultVO();
		
		paramVO.setSendPldCd(sendPldCd);
		paramVO.setDocTypCd(docTypCd);
		paramVO.setSendPlcKindCd(sendPlcKindCd);
		paramVO.setRcptTypCd(rcptTypCd);
		paramVO.setHdlgTypCd(hdlgTypCd);
		
		Map<String, Object> resultMap = penaltyStdManageService.selectPenaltyStdManageList(paramVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
