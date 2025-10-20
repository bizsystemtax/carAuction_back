package egovframework.carauction.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.CommonFileViewService;

@RestController
@Transactional
@RequestMapping("/fileView")
public class CommonFileViewController {
	
	@Value("${file.upload.path}")
	private String uploadRootPath;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFileViewController.class);

	@Resource(name = "commonFileViewService")
	private CommonFileViewService commonFileViewService; 

	
	@GetMapping("/fileDownload/{fileId}")
    public void downloadFile(
    		@PathVariable("fileId") String fileId, 
    		@RequestParam(value = "inline", required = false, defaultValue = "false") boolean inline,
    		HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
		
		logger.info("파일 뷰 :::::: ");
		logger.info("파일 다운로드 요청: fileId={}, inline={}", fileId, inline);
		
		AttachFileVO fileInfo = commonFileViewService.getFileById(fileId);
        if (fileInfo == null) {
            logger.error("파일 정보를 찾을 수 없습니다. attFileId: {}", fileId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fullFilePath = fileInfo.getAttFilePath();
        File file = new File(fullFilePath);
        if (!file.exists() || !file.isFile()) {
            logger.error("파일이 존재하지 않거나 파일이 아님. 경로: {}", fullFilePath);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String originalFileName = fileInfo.getAttFileNm();
        String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String contentType = resolveMimeType(fileExt);
        response.setContentType(contentType);

        // 파일 스트림 응답 처리
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {

               byte[] buffer = new byte[8192];
               int bytesRead;
               while ((bytesRead = in.read(buffer)) != -1) {
                   out.write(buffer, 0, bytesRead);
               }
               out.flush();
               }
    }
	
	
	private String resolveMimeType(String typeOrExt) {
		
		String input = typeOrExt.toLowerCase();
		
		if (input.contains("/") && !input.contains(".")) {
	        return input;
	    }

	    switch (input) {
	        case "pdf": return "application/pdf";
	        case "png": return "image/png";
	        case "jpg":
	        case "jpeg": return "image/jpeg";
	        case "gif": return "image/gif";
	        case "xls": return "application/vnd.ms-excel";
	        case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	        case "hwp": return "application/x-hwp";
	        case "doc": return "application/msword";
	        case "docx": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	        default: return "application/octet-stream";
	    }
	}
}
