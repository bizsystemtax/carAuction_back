package egovframework.carauction.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.CarSaleVO;
import egovframework.carauction.service.CarAucInfService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 경매 판매차량 컨트롤러
 */
@RestController
@Transactional
@RequestMapping("/carAucSaleReg")
public class CarSaleRegController {
	
    @Resource(name = "carAucInfService")
    private CarAucInfService carAucInfService;
	private static final Logger logger = LoggerFactory.getLogger(CarSaleRegController.class);
	
	/**
	 * 차량정보_제조사 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/manufacturList")
	public ResultVO manufacturList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();
		
		carSaleVO.setCodeFirst("C");
		carSaleVO.setCodeSecond("01");
		carSaleVO.setCodeKita5(requestParams.get("domesGb"));
		
		Map<String, Object> resultMap = carAucInfService.manufacturList(carSaleVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 차량정보_모델명 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/modNmList")
	public ResultVO modNmList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();

		carSaleVO.setMkrCd(requestParams.get("manufacturCd"));
		
		Map<String, Object> resultMap = carAucInfService.modNmList(carSaleVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 차량정보_등급명 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/grdNmList")
	public ResultVO grdNmList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();

		carSaleVO.setMkrCd(requestParams.get("manufacturCd"));
		carSaleVO.setCarModelCd(requestParams.get("modCd"));
		
		Map<String, Object> resultMap = carAucInfService.grdNmList(carSaleVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 차량정보_세부등급명 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/dtlGrdNmList")
	public ResultVO dtlGrdNmList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();

		carSaleVO.setMkrCd(requestParams.get("manufacturCd"));
		carSaleVO.setCarModelCd(requestParams.get("modCd"));
		carSaleVO.setCarLevelCd(requestParams.get("grdCd"));
		
		Map<String, Object> resultMap = carAucInfService.dtlGrdNmList(carSaleVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 차량정보_연료코드 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/getCarEngCd")
	public ResultVO getCarEngCd() throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();
		
		Map<String, Object> resultMap = carAucInfService.getCarEngCd(carSaleVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 차량정보_기타 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/getCarAucInfo")
	public ResultVO getCarAucInfo(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();

		carSaleVO.setCodeFirst("C");
		carSaleVO.setCodeSecond("04");
		
		carSaleVO.setMkrCd(requestParams.get("manufacturCd"));
		carSaleVO.setCarModelCd(requestParams.get("modCd"));
		carSaleVO.setCarLevelCd(requestParams.get("grdCd"));
		carSaleVO.setCarDtLevelCd(requestParams.get("dtlGrdCd"));
	
		Map<String, Object> resultMap = carAucInfService.getCarAucInfo(carSaleVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 경매 판매차량 등록
	 */
	@Operation(
			summary = "판매차량 등록",
			security = {@SecurityRequirement(name = "Authorization")},
			tags = {"EgovBBSManageApiController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "등록 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님"),
			@ApiResponse(responseCode = "900", description = "입력값 무결성 오류")
	})
	@PostMapping(value ="/insertCarSale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResultVO insertCarSale(
			//@RequestBody CarSaleDetailVO carSaleDetailVO,
			//BindingResult bindingResult,
			//HttpServletRequest request
			@ModelAttribute CarSaleDetailVO carSaleDetailVO,
			@RequestParam(value = "files", required = false) List<MultipartFile> files,
			BindingResult bindingResult,
			HttpServletRequest request
			)throws Exception {
		ResultVO resultVO = new ResultVO();
		
		System.out.println("==============================" + carSaleDetailVO.toString());

		logger.info("carSaleDetailVO >>>>>>>>> {}", carSaleDetailVO);
		
		// 입찰유효일(마감일)
		String bidExpDt = "";
		bidExpDt = carSaleDetailVO.getBidExpDt();
		bidExpDt = bidExpDt.replace("-", "");
		carSaleDetailVO.setBidExpDt(bidExpDt);
		
		// 경매진행상태코드
		carSaleDetailVO.setAucProgStatCd("0010");
		
		// 등록자, 수정자
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		carSaleDetailVO.setUpdatIdno(loginVO.getId()); //수정자
		carSaleDetailVO.setEntryIdno(loginVO.getId()); //등록자
		
//		List<FileVO> result = null;
//		String atchFileId = "";

//		final Map<String, MultipartFile> files = multiRequest.getFileMap();
//		if (!files.isEmpty()) {
//			result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
//			atchFileId = fileMngService.insertFileInfs(result);
//		}
		
		logger.info("여기까지 들어옴");
		logger.info("files ▶▶▶▶▶▶▶▶▶▶ {}", files);
		logger.info("carSaleDetailVO ▶▶▶▶▶▶▶▶▶▶ {}", carSaleDetailVO);
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("carSaleDetailVO", carSaleDetailVO);
		
		carAucInfService.insertCarSale(param, files);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}
	
	/**
	 * 경매 판매차량 상세 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/getCarSaleDetail")
	public ResultVO getCarSaleDetail(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();

		// 경매번호
		carSaleVO.setAucRegNo(requestParams.get("aucRegNo"));
		
		Map<String, Object> resultMap = carAucInfService.getCarSaleDetail(carSaleVO);
		
		logger.info("resultMap > " + resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 경매 판매차량 이미지 상세 조회
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/getCarSaleImgDetail")
	public ResultVO getCarSaleImgDetail(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		CarSaleVO carSaleVO = new CarSaleVO();

		// 경매번호
		carSaleVO.setAucRegNo(requestParams.get("aucRegNo"));
		
		Map<String, Object> resultMap = carAucInfService.getCarSaleImgDetail(carSaleVO);
		
		logger.info("resultMap > " + resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * 경매 판매차량 등록
	 */
	@Operation(
			summary = "판매차량 수정",
			security = {@SecurityRequirement(name = "Authorization")},
			tags = {"EgovBBSManageApiController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "수정 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님"),
			@ApiResponse(responseCode = "900", description = "입력값 무결성 오류")
	})
	@PostMapping(value ="/updateCarSale")
	public ResultVO updateCarSale(@RequestBody CarSaleDetailVO carSaleDetailVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		
		System.out.println("==============================" + carSaleDetailVO.toString());

		logger.info("carSaleDetailVO >>>>>>>>>", carSaleDetailVO);
		
		// 입찰유효일(마감일)
		String bidExpDt = "";
		bidExpDt = carSaleDetailVO.getBidExpDt();
		bidExpDt = bidExpDt.replace("-", "");
		carSaleDetailVO.setBidExpDt(bidExpDt);
		
		// 수정자
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		carSaleDetailVO.setUpdatIdno(loginVO.getId()); //수정자
		
//		List<FileVO> result = null;
//		String atchFileId = "";

//		final Map<String, MultipartFile> files = multiRequest.getFileMap();
//		if (!files.isEmpty()) {
//			result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
//			atchFileId = fileMngService.insertFileInfs(result);
//		}

		carAucInfService.updateCarSale(carSaleDetailVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}
	
	/**
	 * 경매 판매차량 등록
	 */
	@Operation(
			summary = "판매차량 삭제",
			security = {@SecurityRequirement(name = "Authorization")},
			tags = {"EgovBBSManageApiController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "수정 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님"),
			@ApiResponse(responseCode = "900", description = "입력값 무결성 오류")
	})
	@PostMapping(value ="/deleteCarSale")
	public ResultVO deleteCarSale(@RequestBody CarSaleDetailVO carSaleDetailVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		
		System.out.println("==============================" + carSaleDetailVO.toString());

		logger.info("carSaleDetailVO >>>>>>>>>", carSaleDetailVO);
		
		// 수정자
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		carSaleDetailVO.setUpdatIdno(loginVO.getId()); //수정자
		
		carAucInfService.deleteCarSale(carSaleDetailVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}
}
