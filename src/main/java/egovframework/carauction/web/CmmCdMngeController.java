package egovframework.carauction.web;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CmmCdMngeVO;
import egovframework.carauction.service.CmmCdMngeService;
import egovframework.carauction.service.impl.MyPageServiceImpl;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;


/*
 *  get
 *  post
 *  
 */

@RestController
@RequestMapping("/cmmCdMnge")
public class CmmCdMngeController {

	@Resource(name = "CmmCdMngeService")
	private CmmCdMngeService cmmCdMngeService;
	
	private final Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);
	
	/*
	 * 코드관리 조회
	 */
	@GetMapping("/{code}")
	public ResultVO getCmmCd(@PathVariable("code") String code,
            @RequestParam(value = "name", required = false) String codeName)
            throws Exception {
		
		ResultVO resultVO = new ResultVO();
		CmmCdMngeVO input = new CmmCdMngeVO();
		
		logger.info("getCmmCd 컨트롤러 ▶▶▶▶▶▶ {}", code);
		
		String codeFirst = code.substring(0, 1);
		String codeSecond = code.substring(1,3);
		
		input.setCodeFirst(codeFirst);
		input.setCodeSecond(codeSecond);
		input.setCodeHname(codeName);
		
		/*
		 * 조회
		 */
		Map<String, Object> resultMap = cmmCdMngeService.findCmmCdByCodeFirstAndCodeSecond(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/*
	 * 코드관리 입력
	 */
	@PostMapping("")
	public ResultVO insertCmmCd(CmmCdMngeVO input) {
		logger.info("insertCmmCd 컨트롤러 ▶▶▶▶▶▶ {}", input.getCodeNo());
		
		insertCmmCd(input);
		
		return null;
	}
	
	/*
	 * 코드관리 중복체크
	 */

	@GetMapping("/check")
	public ResultVO checkDuplicatedCd(@RequestParam(value = "frist") String codeFirst,
			@RequestParam(value = "second") String codeSecond,
			@RequestParam(value = "no") String codeNo) throws Exception {
		ResultVO resultVO = new ResultVO();
		CmmCdMngeVO input = new CmmCdMngeVO();
		
		logger.info("checkDuplicatedCd 컨트롤러 ▶▶▶▶▶▶ {}", codeNo);
		
		input.setCodeNo(codeNo);
		
		//중복 체크
		cmmCdMngeService.existCode(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		
		return resultVO;
		
	}
	
	

}
