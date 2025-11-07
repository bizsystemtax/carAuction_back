package egovframework.carauction.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		carSaleVO.setCodeKita5(requestParams.get("locImpGb"));
		
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
		
		//Map<String, Object> resultMap = carAucInfService.getCarSaleDetail(carSaleVO);
		
		Map<String, Object> resultMap = carAucInfService.getCarSaleDetail(carSaleVO);
		Map<String, Object> resultMap2 = carAucInfService.getCarSaleImgDetail(carSaleVO);
		Map<String, Object> totalResult = new HashMap<>();
		totalResult.put("carDetail", resultMap);
		totalResult.put("carImages", resultMap2);
		
		logger.info("resultMap > " + resultMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		//resultVO.setResult(resultMap);
		resultVO.setResult(totalResult);
		
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
	
	  // 2. fileSeq로 이미지 스트리밍
		@GetMapping("/image/{aucRegNo}/{fileSeq}")
	    public void getImage(
	    	    @PathVariable("aucRegNo") String aucRegNo,
	    	    @PathVariable("fileSeq") String fileSeq,
	    	    HttpServletResponse response
	    	) throws Exception {
			
	        FTPClient ftpClient = new FTPClient();
	        
	        CarSaleDetailVO carSaleDetailVO = new CarSaleDetailVO();
	        
	        carSaleDetailVO.setAucRegNo(aucRegNo);
	        carSaleDetailVO.setFileSeq(fileSeq);
	        
	        logger.debug("image 조회 fileSeq"+ fileSeq);
	        try {
	        	
	            Map<String, Object> fileInfo = carAucInfService.getFileInfo(carSaleDetailVO);
	          
	            if (fileInfo == null || fileInfo.get("result") == null) {
	                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                return;
	            }
	        	
	            CarSaleDetailVO fileDetail = (CarSaleDetailVO) fileInfo.get("result");
	            logger.info("fileDetail = {}", fileDetail);
	            
	            String fileSvrName = fileDetail.getFileSvrName();
	            
	            String ftpHost = "192.168.0.46";
	            int ftpPort = 21;
	            String ftpUser = "bizsystem_dev";
	            String ftpPassword = "bizsystem#99";
	            String remoteDir = "/remote/path";

	            ftpClient.connect(ftpHost, ftpPort);
	         // Windows FTP 서버용: CP949 사용
	            ftpClient.setControlEncoding("CP949");
	            ftpClient.login(ftpUser, ftpPassword);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	          
	         // 한글 파일명은 CP949 → ISO-8859-1로 변환
	            String remoteFilePath = remoteDir + "/" + new String(fileSvrName.getBytes("CP949"), "ISO-8859-1");
	            logger.info("remoteFilePath = {}", remoteFilePath);
	          
	            
	            // 확장자 추출
	            String ext = "";
	            if (fileSvrName.contains(".")) {
	                ext = fileSvrName.substring(fileSvrName.lastIndexOf(".") + 1).toLowerCase();

	                if ("jpg".equals(ext)) {
	                    ext = "jpeg";
	                }
	            }
	            
	            // 스트리밍
	            try (InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath)) {
	                if (inputStream == null) {
	                    int replyCode = ftpClient.getReplyCode();
	                    String replyString = ftpClient.getReplyString();
	                    logger.error("FTP retrieveFileStream 실패: replyCode={}, replyString={}", replyCode, replyString);
	                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                    return;
	                }

	                response.setContentType("image/" + ext);
	                IOUtils.copy(inputStream, response.getOutputStream());
	                response.flushBuffer();

	                boolean completed = ftpClient.completePendingCommand();
	                if (!completed) {
	                    logger.error("FTP completePendingCommand 실패");
	                }
	            }

	        } catch (IOException e) {
	            logger.error("FTP 이미지 조회 중 오류", e);
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException e) {
	                logger.warn("FTP 연결 종료 중 오류", e);
	            }
	        }
	    }
		
	
	/**
	 * 경매 판매차량 수정
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
	public ResultVO updateCarSale(
		@ModelAttribute CarSaleDetailVO carSaleDetailVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		
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
	 * 경매 판매차량 삭제
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
		carAucInfService.deleteCarSaleImgInfo(carSaleDetailVO);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}
}
