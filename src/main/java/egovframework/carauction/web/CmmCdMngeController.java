package egovframework.carauction.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.service.CmmCdMngeService;
import egovframework.com.cmm.service.ResultVO;

@RestController
@RequestMapping("/cmmCdMnge")
public class CmmCdMngeController {
	
	@Resource(name = "CmmCdMngeService")
	private CmmCdMngeService cmmCdMngeService;
	
	@GetMapping("/code/{code_fisrt}/{code_second}")
	public ResultVO getCmmCd() {
		return null;
	}
	
	@PostMapping("/")
	public ResultVO insertCmmCd() {
		return null;
	}
	

}
