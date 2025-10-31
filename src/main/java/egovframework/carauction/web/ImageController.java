package egovframework.carauction.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;


/**
 * 이미지 컨트롤러
 */
@RestController
@RequestMapping("/image")
public class ImageController {
	

    private final String basePath = "C:/upload/auc"; // 서버 업로드 기본 경로
	
    @GetMapping("/{date}/{fileName:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String date,
            @PathVariable String fileName,
            HttpServletRequest request) throws MalformedURLException {

        Path filePath = Paths.get(basePath, date, fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // MIME 타입 안전하게
        String contentType;
        try {
            contentType = Files.probeContentType(filePath);
            if (contentType == null) contentType = "application/octet-stream";
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
        
    }
	
}