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

import egovframework.penalty.service.PenaltyOcrService;

@RestController
@RequestMapping("/ocr")
public class PenaltyOcrController {

    private static final Logger logger = LoggerFactory.getLogger(PenaltyOcrController.class);

    @Resource(name = "PenaltyOcrService")
    private PenaltyOcrService penaltyOcrService;

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processOcr(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> stringMap = penaltyOcrService.processOcrFile(file);
            Map<String, Object> objectMap = new HashMap<>(stringMap);
            return ResponseEntity.ok(objectMap);
        } catch (Exception e) {
            logger.error("OCR 처리 중 오류 발생", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "OCR 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveOcrResult(@RequestBody Map<String, String> confirmedData) {
        logger.debug("Received data: {}", confirmedData);
        try {
            if (confirmedData == null || confirmedData.isEmpty()) {
                logger.error("요청 데이터가 비어 있습니다.");
                throw new IllegalArgumentException("요청 데이터가 비어 있습니다.");
            }

            String message = penaltyOcrService.saveOcrResult(confirmedData);
            Map<String, Object> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("데이터 저장 중 오류 발생", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}