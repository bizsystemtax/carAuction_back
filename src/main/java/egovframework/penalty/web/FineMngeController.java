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
import egovframework.penalty.FineMngeVO;
import egovframework.penalty.service.FineMngeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/fineMnge")
public class FineMngeController {
	
	@Resource(name = "FineMngeService")
	private FineMngeService fineMngeService;
	
	/**
	 * @author 범칙금관리 검색조건 콤보박스 조회 컨트롤러
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
		
		Map<String, Object> resultMap = fineMngeService.retrieveComboBoxList(paramVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}

	/**
	 * @author 범칙금관리 조회 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/list")
	public ResultVO selectFineMnge(@RequestBody Map<String, String> requestParams) throws Exception{
		ResultVO resultVO = new ResultVO();
		FineMngeVO fineMngeVO = new FineMngeVO();
		
		String inVltDtStrt = requestParams.get("in_vlt_dt_strt");//위반일자시작
		String inVltDtEnd = requestParams.get("in_vlt_dt_end");	//위반일자종료
		String inVltKindCd = requestParams.get("in_vlt_kind_cd");//위반종류코드
		String inSendPlcCd = requestParams.get("in_send_plc_cd");//발송처코드
		String inGdCd = requestParams.get("in_gd_cd");			//상품코드
		String inCsNm = requestParams.get("in_cs_nm");			//고객명
		String inVhclNo = requestParams.get("in_vhcl_no");		//차량번호

		//조회조건 VO 세팅
		fineMngeVO.setInVltDtStrt(inVltDtStrt);
		fineMngeVO.setInVltDtEnd(inVltDtEnd);
		fineMngeVO.setInVltKindCd(inVltKindCd);
		fineMngeVO.setInSendPlcCd(inSendPlcCd);
		fineMngeVO.setInGdCd(inGdCd);
		fineMngeVO.setInCsNm(inCsNm);
		fineMngeVO.setInVhclNo(inVhclNo);
			
		//범칙금관리 조회 서비스 호출
		Map<String, Object> resultMap = fineMngeService.retrieveFineMnge(fineMngeVO);
		
		resultMap.put("fineMngeVO", fineMngeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
