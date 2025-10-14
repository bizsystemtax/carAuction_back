package egovframework.carauction.web;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.service.CmmCdMngeService;

@RestController
@RequestMapping("/cmmCdMnge")
public class CmmCdMngeController {
	
	@Resource(name = "CmmCdMngeService")
	private CmmCdMngeService cmmCdMngeService;
	
	@GetMapping("/")
	public Object getCmmCd() {
		return null;
	}
	

}
