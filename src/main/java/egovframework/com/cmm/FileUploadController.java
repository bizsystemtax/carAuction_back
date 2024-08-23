package egovframework.com.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import egovframework.let.utl.fcc.service.EgovFormBasedFileVo;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/file")
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> handleFileUpload(@RequestBody String jsonPayload) {
        Map<String, Object> response = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonPayload);
            if (jsonNode == null) {
                throw new Exception("Invalid JSON payload");
            }

            String fileName = jsonNode.has("fileName") ? jsonNode.get("fileName").asText() : null;
            String contentType = jsonNode.has("contentType") ? jsonNode.get("contentType").asText() : null;
            long size = jsonNode.has("size") ? jsonNode.get("size").asLong() : 0;
            String base64Data = jsonNode.has("data") ? jsonNode.get("data").asText() : null;

            if (fileName == null || contentType == null || base64Data == null) {
                throw new Exception("Missing required fields in JSON payload");
            }

            if (!contentType.equals("application/pdf")) {
                throw new Exception("Only PDF files are supported");
            }

            byte[] fileData = Base64.getDecoder().decode(base64Data);
            EgovFormBasedFileVo fileVo = new EgovFormBasedFileVo();
            fileVo.setFileName(fileName);
            fileVo.setContentType(contentType);
            fileVo.setSize(size);
            fileVo.setData(fileData);

            String uploadedFilePath = fileUploadService.saveFile(fileVo);
            fileUploadService.uploadFileToFTP(uploadedFilePath, fileName);

            response.put("resultCode", 200);
            response.put("message", "PDF 파일 업로드 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("File upload error", e);
            response.put("resultCode", 500);
            response.put("message", "서버 오류: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = "/upload")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}