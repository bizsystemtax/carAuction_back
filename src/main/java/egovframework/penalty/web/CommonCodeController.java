package egovframework.penalty.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.cop.bbs.service.BoardMasterVO;
import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.penalty.CommonCodeVO;
import egovframework.penalty.service.CommonCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("/code")

public class CommonCodeController {

	@Resource(name = "CommonCodeService")
	private  CommonCodeService commService;

	@Autowired
	private DefaultBeanValidator beanValidator;
	
	/**
	 * 공통코드를 조회한다.
	 * 
	 * @param CommonCodeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@GetMapping(value = "/commonCode")
	public ResultVO getSelectCode(@RequestParam Map<String, Object> parmMap) throws Exception{
	
		ResultVO resultVO = new ResultVO();
		CommonCodeVO commoncodeVO = new CommonCodeVO();
		
		String cmmCd = (String) parmMap.get("cmm_cd");
		String cmmCdNm = (String) parmMap.get("cmm_cd_nm"); 
	 
		commoncodeVO.setCmmCd(cmmCd);
		commoncodeVO.setCmmCdNm(cmmCdNm);
			
		Map<String, Object> resultMap = commService.getSelectCode(commoncodeVO);
		
		resultMap.put("commoncodeVO", commoncodeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}

}
