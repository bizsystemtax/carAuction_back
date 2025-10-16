package egovframework.carauction.web;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CmmCdMngeVO;
import egovframework.carauction.service.CmmCdMngeService;
import egovframework.carauction.service.impl.MyPageServiceImpl;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;

@RestController
@RequestMapping("/cmmCdMnge")
public class CmmCdMngeController {

	@Resource(name = "CmmCdMngeService")
	private CmmCdMngeService cmmCdMngeService;
	
	private final Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);

	@GetMapping("/{code_first}/{code_second}")
	public ResultVO getCmmCd(@PathVariable("code_first") String codeFirst,
			@PathVariable("code_second") String codeSecond) throws Exception {
		
		ResultVO resultVO = new ResultVO();
		CmmCdMngeVO input = new CmmCdMngeVO();
		
		logger.info("getCmmCd 컨트롤러 ▶▶▶▶▶▶ {}", codeFirst);
		
		input.setCodeFirst(codeFirst);
		input.setCodeSecond(codeSecond);
		
		/*
		 * 조회
		 */
		Map<String, Object> resultMap = cmmCdMngeService.findCmmCdByCodeFirstAndCodeSecond(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}

	@GetMapping("/{code_no}")
	public ResultVO checkDuplicatedCd(@PathVariable("code_no") String codeNo) throws Exception {
		ResultVO resultVO = new ResultVO();
		CmmCdMngeVO input = new CmmCdMngeVO();
		
		logger.info("checkDuplicatedCd 컨트롤러 ▶▶▶▶▶▶ {}", codeNo);
		
		input.setCodeNo(codeNo);
		
		//중복 체크
		cmmCdMngeService.findCmmCdByCodeNo(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		
		return resultVO;
		
	}

	@PostMapping("")
	public ResultVO insertCmmCd() {
		return null;
	}

}
