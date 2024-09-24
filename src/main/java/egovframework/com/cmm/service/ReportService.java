package egovframework.com.cmm.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.ResponseEntity;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Value("${report.server.url}")
    private String reportServerUrl;

    private final RestTemplate restTemplate;

    public ReportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String testConnection() {
        String url = reportServerUrl + "/api/report-server/test";
        logger.info("8080 서버: 톰캣 서버 연결 시도 - URL: {}", url);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String response = responseEntity.getBody();
                logger.info("8080 서버: 톰캣 서버로부터 성공 응답 수신 - {}", response);
                return response;
            } else {
                logger.error("8080 서버: 톰캣 서버로부터 오류 응답 수신 - 상태 코드: {}", responseEntity.getStatusCodeValue());
                throw new RuntimeException("톰캣 서버 오류 응답: " + responseEntity.getStatusCode());
            }
        } catch (ResourceAccessException e) {
            logger.error("8080 서버: 톰캣 서버 연결 실패 - 서버가 응답하지 않음", e);
            throw new RuntimeException("톰캣 서버 연결 실패: 서버가 응답하지 않습니다.", e);
        } catch (Exception e) {
            logger.error("8080 서버: 톰캣 서버 연결 중 예외 발생", e);
            throw new RuntimeException("톰캣 서버 연결 실패: " + e.getMessage(), e);
        }
    }

    // 실제 리포팅 기능을 위한 메소드 추가
    public String generateReport(/* 필요한 매개변수 */) {
        // 리포팅 서버에 실제 보고서 생성 요청
        // 구현 필요
        return "보고서 생성 완료";
    }
}