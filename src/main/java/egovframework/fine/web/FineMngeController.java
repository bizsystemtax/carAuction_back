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
			description = "범칙금관리 목록을 조회",
			tags = {"FineMngeController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/fineMnge")
	public ResultVO selectFineMnge(@RequestParam Map<String, Object> parmMap) throws Exception{
	
		ResultVO resultVO = new ResultVO();
		FineMngeVO fineMngeVO = new FineMngeVO();
		
		String vltDt = (String) parmMap.get("vlt_dt");

		//조회조건 VO 세팅
		fineMngeVO.setVltDt(vltDt);
			
		//범칙금관리 조회 서비스 호출
		Map<String, Object> resultMap = fineMngeService.getRetrieveFineMnge(fineMngeVO);
		
		resultMap.put("fineMngeVO", fineMngeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
