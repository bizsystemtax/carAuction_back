package egovframework.carauction.web;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CmmCdMngeVO;
import egovframework.carauction.service.CmmCdMngeService;
import egovframework.carauction.service.impl.MyPageServiceImpl;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;


/*
 *  /cmmCdMnge - CRUD
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
	@GetMapping(value = {"/{code}", ""})
	public ResultVO getCmmCd(@PathVariable(required = false) String code,
            @RequestParam(value = "name", required = false) String codeName)
            throws Exception {
		
		ResultVO resultVO = new ResultVO();
		CmmCdMngeVO input = new CmmCdMngeVO();
		
		
		if(code != null) {
			input.setCodeFirst(code.substring(0, 1));
			input.setCodeSecond(code.substring(1,3));
		}
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
	 * 코드관리 등록
	 */
	@PostMapping("")
	public ResultVO insertCmmCd(@RequestBody CmmCdMngeVO input) throws Exception {
		logger.info("insertCmmCd 컨트롤러 ▶▶▶▶▶▶ {}", input.getCodeNo());
		
		ResultVO resultVO = new ResultVO();
		
		cmmCdMngeService.insertCmmCd(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	/*
	 * 코드관리 수정
	 */
	@PutMapping("")
	public ResultVO updateCmmCd(@RequestBody CmmCdMngeVO input) throws Exception {
		logger.info("updateCmmCd 컨트롤러 ▶▶▶▶▶▶ {}", input.getCodeNo());
		
		ResultVO resultVO = new ResultVO();
		
		cmmCdMngeService.updateCmmCd(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}
	
	/*
	 * 코드관리 삭제//
	 */
	@PatchMapping("")
	public ResultVO deleteCmmCd(@RequestBody CmmCdMngeVO input) {
		logger.info("deleteCmmCd 컨트롤러 ▶▶▶▶▶▶ {}", input.getCodeNo());
		
		return null;
	}
	
	/*
	 * 코드관리 중복체크
	 */
	@GetMapping("/check")
	public ResultVO checkDuplicatedCd(@RequestParam(value = "first") String codeFirst,
			@RequestParam(value = "second") String codeSecond,
			@RequestParam(value = "no") String codeNo) throws Exception {
		
		ResultVO resultVO = new ResultVO();
		CmmCdMngeVO input = new CmmCdMngeVO();
		
		logger.info("checkDuplicatedCd 컨트롤러 ▶▶▶▶▶▶ {}", codeNo);
		
		input.setCodeNo(codeNo);
		input.setCodeFirst(codeFirst);
		input.setCodeSecond(codeSecond);
		
		//중복 체크
		cmmCdMngeService.existCode(input);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
			
		return resultVO;
	}

}
