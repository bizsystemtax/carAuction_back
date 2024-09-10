package egovframework.penalty.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FineOcrService {
    // 파일 처리 관련 메서드
    public Map<String, String> processOcrFile(MultipartFile file) throws Exception;

}
