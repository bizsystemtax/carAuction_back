package egovframework.carauction.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CarMngeVO;
import egovframework.carauction.service.CarMngeService;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/carMnge")
public class CarMngeController {

	@Resource(name = "CarMngeService")
	private CarMngeService carMngeService;

	// 차량관리 검색조건(제조사) 조회
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/mkrList")
	public ResultVO selMkrList(@RequestBody Map<String, String> requestParams) throws Exception {

		ResultVO resultVO = new ResultVO();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.selMkrList(requestParams.get("locImpGb"));

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	// 차량관리 검색조건(모델, 등급명, 세부등급명) 조회
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/srchCondList")
	public ResultVO selSrchCondList(@RequestBody Map<String, String> requestParams) throws Exception {

		CarMngeVO carMngeVO = new CarMngeVO();
		ResultVO resultVO = new ResultVO();

		carMngeVO.setMkrCd(requestParams.get("mkrCd"));
		carMngeVO.setCarModelCd(requestParams.get("carModelCd"));
		carMngeVO.setCarLevelCd(requestParams.get("carLevelCd"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.selSrchCondList(carMngeVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	// 차량관리 검색조건(연료) 조회
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/fuelList")
	public ResultVO selFuelList(@RequestBody Map<String, String> requestParams) throws Exception {

		ResultVO resultVO = new ResultVO();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.selFuelList();

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	// 차량관리 리스트 조회
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/carMngList")
	public ResultVO getCarMngeList(@RequestBody Map<String, String> requestParams) throws Exception {

		CarMngeVO carMngeVO = new CarMngeVO();
		ResultVO resultVO = new ResultVO();

		carMngeVO.setLocImpGb(requestParams.get("locImpGb"));
		carMngeVO.setMkrCd(requestParams.get("mkrCd"));
		carMngeVO.setCarModelCd(requestParams.get("carModelCd"));
		carMngeVO.setCarLevelCd(requestParams.get("carLevelCd"));
		carMngeVO.setCarDtLevelCd(requestParams.get("carDtLevelCd"));
		carMngeVO.setCarEngCd(requestParams.get("carEngCd"));
		carMngeVO.setStartCarCc(requestParams.get("carCc1"));
		carMngeVO.setEndCarCc(requestParams.get("carCc2"));
		carMngeVO.setCarModelNm(requestParams.get("vehicleNm"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.getCarMngeList(carMngeVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	// 차량관리 상세 조회
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/carMng")
	public ResultVO getCarMnge(@RequestBody Map<String, String> requestParams) throws Exception {

		ResultVO resultVO = new ResultVO();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.getCarMnge(requestParams.get("carMainNo"));

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	// 차량관리 등록
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/insCarMng")
	public ResultVO insCarMnge(@RequestBody Map<String, String> requestParams) throws Exception {

		CarMngeVO carMngeVO = new CarMngeVO();
		ResultVO resultVO = new ResultVO();

		int carPrice = 0;
		int carCc = 0;

		try {
			carPrice = Integer.parseInt(requestParams.get("carPrice"));
		} catch (NumberFormatException e) {
		}

		try {
			carCc = Integer.parseInt(requestParams.get("carCc"));
		} catch (NumberFormatException e) {
		}

		carMngeVO.setLocImpGb(requestParams.get("locImpGb"));
		carMngeVO.setMkrCd(requestParams.get("mkrCd"));
		carMngeVO.setCarModelCd(requestParams.get("carModelCd"));
		carMngeVO.setCarModelNm(requestParams.get("carModelNm"));
		carMngeVO.setCarLevelCd(requestParams.get("carLevelCd"));
		carMngeVO.setCarLevelNm(requestParams.get("carLevelNm"));
		carMngeVO.setCarDtLevelCd(requestParams.get("carDtLevelCd"));
		carMngeVO.setCarDtLevelNm(requestParams.get("carDtLevelNm"));
		carMngeVO.setCarEngCd(requestParams.get("carEngCd"));
		carMngeVO.setCarTransGb(requestParams.get("carTransGb"));
		carMngeVO.setCarCc(carCc);
		carMngeVO.setCarPrice(carPrice);
		carMngeVO.setUseYn(requestParams.get("useYn"));
		carMngeVO.setEntryIdno(requestParams.get("idno"));
		carMngeVO.setUpdatIdno(requestParams.get("idno"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.insCarMnge(carMngeVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	// 차량관리 수정
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님") })
	@PostMapping("/updCarMnge")
	public ResultVO updCarMnge(@RequestBody Map<String, String> requestParams) throws Exception {

		CarMngeVO carMngeVO = new CarMngeVO();
		ResultVO resultVO = new ResultVO();

		int carPrice = 0;
		int carCc = 0;

		try {
			carPrice = Integer.parseInt(requestParams.get("carPrice"));
		} catch (NumberFormatException e) {
		}

		try {
			carCc = Integer.parseInt(requestParams.get("carCc"));
		} catch (NumberFormatException e) {
		}

		carMngeVO.setCarMainNo(requestParams.get("carMainNo"));
		carMngeVO.setCarEngCd(requestParams.get("carEngCd"));
		carMngeVO.setCarTransGb(requestParams.get("carTransGb"));
		carMngeVO.setCarCc(carCc);
		carMngeVO.setCarPrice(carPrice);
		carMngeVO.setUseYn(requestParams.get("useYn"));
		carMngeVO.setUpdatIdno(requestParams.get("idno"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = carMngeService.updCarMnge(carMngeVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}
}