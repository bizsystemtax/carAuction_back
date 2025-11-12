package egovframework.carauction.web;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.service.CommonFileService;
import egovframework.com.cmm.service.ResultVO;

@RestController
@Transactional
@RequestMapping("/file")
public class CommonFileController {
	
	@Value("${file.upload.path}")
	private String uploadRootPath;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFileController.class);

	@Resource(name = "commonFileService")
	private CommonFileService commonFileService; 

	
	//파일 삭제 
	@PostMapping("/fileDelete")
    public ResultVO deleteFileInf(
    		HttpServletRequest request, 
    		//@RequestBody AttachFileVO attachFileVO
    		@RequestBody List<AttachFileVO> attachFileList
    		) throws Exception {
    	ResultVO resultVO = new ResultVO();
    	
    	logger.info("attachFileList  :::::::::::::: {}", attachFileList);
    	
    	
    	try {
    		for (AttachFileVO attachFileVO : attachFileList) {
    			commonFileService.deleteFile(attachFileVO);    			
    		}
    		resultVO.setResultCode(200);
			resultVO.setResultMessage("삭제 성공");
    	}catch (FileNotFoundException e) {
    		resultVO.setResultCode(404);
			resultVO.setResultMessage("파일이 존재하지 않습니다.");
		}catch (Exception e) {
			resultVO.setResultCode(500);
			resultVO.setResultMessage("파일 삭제 실패");
		}
    	
		
    	/*
    	String atchFileId = attachFileVO.getAttFileId().replaceAll(" ", "+");
    	
    	logger.info("request      :::::::::::::: {}", request);
    	logger.info("attachFileVO :::::::::::::: {}", attachFileVO);
    	logger.info("삭제가 되는지 확인  :::::::::::::: {}", atchFileId);
    	
    	try {
    		commonFileService.deleteFile(attachFileVO);
    		resultVO.setResultCode(200);
			resultVO.setResultMessage("삭제 성공");
    	}catch (FileNotFoundException e) {
    		resultVO.setResultCode(404);
			resultVO.setResultMessage("파일이 존재하지 않습니다.");
		}catch (Exception e) {
			resultVO.setResultCode(500);
			resultVO.setResultMessage("파일 삭제 실패");
		}
		*/
		return resultVO;
	}
}
