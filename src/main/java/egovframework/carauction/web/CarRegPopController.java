package egovframework.carauction.web;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.service.CarMngeService;

@RestController
@RequestMapping("/carMnge")
public class CarRegPopController {
	
	@Resource(name = "CarMngeService")
	private CarMngeService carMngeService;
	
	@GetMapping("/regCarMnge")
	public Object regCarMnge() {
		return null;
	}

}