package egovframework.carauction.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.BidInfoVO;
import egovframework.carauction.CarInfoVO;
import egovframework.carauction.service.CarAucInfService;

@RestController
@RequestMapping("/carAucInf")
@CrossOrigin(origins = "http://localhost:3000")
public class CarBidRegController {

	@Resource(name = "carAucInfService")
	private CarAucInfService carAucInfService;

	/**
	 * 차량 상세 정보 조회 (키값 기반)
	 */
	@GetMapping("/car/{aucRegNo}")
	public CarInfoVO getCarDetail(@PathVariable String aucRegNo) throws Exception {
	    return carAucInfService.getCarDetail(aucRegNo);
	}

	/**
	 * 은행 목록 조회
	 */
	@GetMapping("/banks")
	public List<String> getBanks() throws Exception {
	    return carAucInfService.getBanks();
	}

	/**
	 * 계좌번호 목록 조회
	 */
	@GetMapping("/accounts")
	public List<String> getAccountNumbers(@RequestParam String bank) throws Exception {
	    return carAucInfService.getAccountNumbers(bank);
	}


}