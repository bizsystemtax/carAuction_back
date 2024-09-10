package egovframework.penalty.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.penalty.service.FineOcrService;

@RestController
@RequestMapping("/ocr")
public class FineOcrController {

    private static final Logger logger = LoggerFactory.getLogger(FineOcrController.class);

    @Resource(name = "FineOcrService")
    private FineOcrService fineOcrService;

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processOcr(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> stringMap = fineOcrService.processOcrFile(file);
            Map<String, Object> objectMap = new HashMap<>(stringMap);
            return ResponseEntity.ok(objectMap);
        } catch (Exception e) {
            logger.error("OCR 처리 중 오류 발생", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "OCR 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}