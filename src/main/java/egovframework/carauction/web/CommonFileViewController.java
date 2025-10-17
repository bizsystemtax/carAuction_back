package egovframework.carauction.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.CommonFileViewService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void downloadFile(@PathVariable("fileId") String fileId, 
    		@RequestParam(value = "inline", required = false, defaultValue = "false") boolean inline,
    		HttpServletResponse response) throws Exception {
		
		logger.info("여기는 들어오나?");
		
        // 실제 파일 정보 조회 (경로, 이름 등)
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

        String contentType = fileInfo.getAttFileType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = Files.probeContentType(file.toPath());  // 파일 경로로부터 MIME 타입 추측
        }
        if (contentType == null) {
            contentType = "application/octet-stream";  // MIME 타입 못찾으면 기본값 설정
        }
        response.setContentType(contentType);
        response.setContentLengthLong(file.length());

        String originalFileName = fileInfo.getAttFileNm();
        try {
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
            // inline == true 면 inline; 아니면 attachment
            String dispositionType = inline ? "inline" : "attachment";
            response.setHeader("Content-Disposition", dispositionType + "; filename=\"" + encodedFileName + "\"");
        } catch (UnsupportedEncodingException e) {
            logger.warn("파일명 인코딩 실패, 기본값 사용: {}", originalFileName, e);
            String dispositionType = inline ? "inline" : "attachment";
            response.setHeader("Content-Disposition", dispositionType + "; filename=\"" + originalFileName + "\"");
        }

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        	     BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
        	    byte[] buffer = new byte[8192];
        	    int bytesRead;
        	    while ((bytesRead = in.read(buffer)) != -1) {
        	        out.write(buffer, 0, bytesRead);
        	    }
        	    out.flush();
        } catch (IOException e) {
            logger.error("파일 다운로드 중 오류 발생: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
