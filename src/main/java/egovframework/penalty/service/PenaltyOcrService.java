package egovframework.penalty.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PenaltyOcrService {
    Map<String, Object> processOcrFile(MultipartFile file) throws Exception;
    String saveOcrResult(Map<String, String> confirmedData) throws Exception;
}