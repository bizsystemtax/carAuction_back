package egovframework.penalty.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PenaltyOcrService {
    // 파일 처리 관련 메서드
    public Map<String, String> processOcrFile(MultipartFile file) throws Exception;

    // 결과 저장 관련 메서드
    public String saveOcrResult(Map<String, String> confirmedData) throws Exception;
}
