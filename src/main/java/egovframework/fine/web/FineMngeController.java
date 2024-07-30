package egovframework.fine.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.fine.FineMngeVO;
import egovframework.fine.service.FineMngeService;
import egovframework.penalty.CommonCodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/code")
public class FineMngeController {
	
	@Resource(name = "FineMngeService")
	private FineMngeService fineMngeService;

	/**
	 * @author 범칙금관리 조회 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@Operation(
			summary = "범칙금관리 조회",
			description = "범칙금관리 목록 조회",
			tags = {"FineMngeController"}
	)
	@PostMapping(value = "/fineMnge")
	public ResultVO selectFineMnge(@RequestParam Map<String, Object> parmMap) throws Exception{
	
		ResultVO resultVO = new ResultVO();
		FineMngeVO fineMngeVO = new FineMngeVO();
		
		String inVltDtStrt = (String) parmMap.get("in_vlt_dt_strt");//위반일자시작
		String inVltDtEnd = (String) parmMap.get("in_vlt_dt_end");	//위반일자종료
		String inVltKindCd = (String) parmMap.get("in_vlt_kind_cd");//위반종류코드
		String inSendPlcCd = (String) parmMap.get("in_send_plc_cd");//발송처코드
		String inGdCd = (String) parmMap.get("in_gd_cd");			//상품코드
		String inCsNm = (String) parmMap.get("in_cs_nm");			//고객명
		String inVhclNo = (String) parmMap.get("in_vhcl_no");		//차량번호

		//조회조건 VO 세팅
		fineMngeVO.setInVltDtStrt(inVltDtStrt);
		fineMngeVO.setInVltDtEnd(inVltDtEnd);
		fineMngeVO.setInVltKindCd(inVltKindCd);
		fineMngeVO.setInSendPlcCd(inSendPlcCd);
		fineMngeVO.setInGdCd(inGdCd);
		fineMngeVO.setInCsNm(inCsNm);
		fineMngeVO.setInVhclNo(inVhclNo);
			
		//범칙금관리 조회 서비스 호출
		Map<String, Object> resultMap = fineMngeService.getRetrieveFineMnge(fineMngeVO);
		
		resultMap.put("fineMngeVO", fineMngeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
