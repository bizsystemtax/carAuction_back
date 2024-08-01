package egovframework.penalty.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.service.ResultVO;
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
	private CommonCodeService commService;

	
	/**
	 * 공통코드를 조회한다.
	 * 
	 * @param CommonCodeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@Operation(
			summary = "공통코드 조회",
			description = "공통코드에 대한 목록을 조회",
			tags = {"CommonCodeController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/commonCode")
	public ResultVO selectCommonCode(
			@Parameter(name = "cmmCd", description = "공통코드", in = ParameterIn.PATH, example="CMMCODE_000000000001")
			@PathVariable("cmmCd") String cmmCd,
			@Parameter(name = "cmmCdNm", description = "공통코드명", in = ParameterIn.PATH, example="1")
			@PathVariable("cmmCdNm") String cmmCdNm)
			//@Parameter(name = "nttId", description = "게시글 Id", in = ParameterIn.PATH, example="1")
			//@PathVariable("nttId") String nttId,
			//@Parameter(hidden = true) @AuthenticationPrincipal LoginVO user)
		throws Exception {
		
		ResultVO resultVO = new ResultVO();
		CommonCodeVO commoncodevo = new CommonCodeVO();
		
		commoncodevo.setCmmCd(cmmCd);
		commoncodevo.setCmmCdNm(cmmCdNm);
		
		CommonCodeVO vo = commService.selectCommonCode(commoncodevo);
		
		System.out.println("===================> 확인 " + resultVO);
	
		return resultVO;
	}
}
