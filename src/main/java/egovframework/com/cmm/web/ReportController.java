package egovframework.com.cmm.web;
import egovframework.com.cmm.service.ReportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/report-server")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Resource(name = "reportService")
    private ReportService reportService;

    @PostMapping("/test")
    public ResponseEntity<String> testConnection() {
        logger.info("8080 서버: /api/report-server/test 요청 수신");
        try {
            String result = reportService.testConnection();
            logger.info("8080 서버: 톰캣 서버 연결 성공 - 결과: {}", result);
            return ResponseEntity.ok("8080 서버: 전자정부 프레임워크 → 톰캣 서버 연결 성공: " + result);
        } catch (Exception e) {
            logger.error("8080 서버: 톰캣 서버 연결 테스트 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("8080 서버: 톰캣 서버 연결 테스트 실패 - " + e.getMessage());
        }
    }
    
}