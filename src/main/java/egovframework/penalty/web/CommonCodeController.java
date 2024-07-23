package egovframework.penalty.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.penalty.service.CommonCodeService;



@RestController
@RequestMapping("/code")
public class CommonCodeController {
	
	@Resource(name = "CommonCodeService")
	private CommonCodeService commService;

	
//	@PostMapping("/organizationList")
//	public List<String> getCommonCodesByCategory() {
//        return commonCodeRepository.findByCategory(category);
//	}
//	
}
