package egovframework.carauction.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;
import egovframework.carauction.service.CarAucInfService;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * 차량 경매 정보 컨트롤러
 */
@RestController
@Transactional
@RequestMapping("/carAucInf")
@CrossOrigin(origins = "http://localhost:3000")
public class CarAucInfController {
	
	private static final Logger logger = LoggerFactory.getLogger(CarAucInfController.class);
	
	@Resource(name = "carAucInfService")
	private CarAucInfService carAucInfService;
	
	/**
	 * 차량 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/cars")
	public ResultVO getCars(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		// Map을 CarSearchCriteriaVO로 변환
		CarSearchCriteriaVO criteria = new CarSearchCriteriaVO();
		criteria.setManufacturer(requestParams.get("manufacturer"));
		criteria.setModel(requestParams.get("model"));
		criteria.setSubModel(requestParams.get("subModel"));
		criteria.setFuelType(requestParams.get("fuelType"));
		criteria.setRegistrationCompany(requestParams.get("registrationCompany"));
		criteria.setStartYear(requestParams.get("startYear"));
		criteria.setEndYear(requestParams.get("endYear"));
		criteria.setStartDate(requestParams.get("startDate"));
		criteria.setEndDate(requestParams.get("endDate"));
		criteria.setMileageStart(requestParams.get("mileageStart"));
		criteria.setMileageEnd(requestParams.get("mileageEnd"));
		
		List<CarInfoVO> carList = carAucInfService.findCarsWithConditions(criteria);
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", carList);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 제조사 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/manufacturers")
	public ResultVO getManufacturers(@RequestBody(required = false) Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		List<Map<String, Object>> manufacturers = carAucInfService.findAllManufacturers();
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", manufacturers);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 모델 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/models")
	public ResultVO getModels(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		String manufacturer = requestParams.get("manufacturer");
		List<Map<String, Object>> models;
		
		if (manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer)) {
			models = carAucInfService.findModelsByManufacturer(manufacturer);
		} else {
			models = carAucInfService.findAllModels();
		}
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", models);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 세부모델 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/submodels")
	public ResultVO getSubModels(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		String manufacturer = requestParams.get("manufacturer");
		String model = requestParams.get("model");
		
		List<Map<String, Object>> subModels = carAucInfService.findSubModelsByManufacturerAndModel(manufacturer, model);
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", subModels);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 연료타입 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/fuelType")
	public ResultVO getFuelTypes(@RequestBody(required = false) Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		List<Map<String, Object>> fuelTypes = carAucInfService.findAllFuelTypes();
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", fuelTypes);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 등록회원사 목록 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping("/registrationCompanies")
	public ResultVO getRegistrationCompanies(@RequestBody(required = false) Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		
		List<Map<String, Object>> companies = carAucInfService.findAllRegistrationCompanies();
		
		// List를 Map으로 감싸기
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", companies);
		
		logger.info("resultMap ▶▶▶▶▶▶ {}", resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}