package egovframework.penalty.service.impl;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import egovframework.penalty.PenaltyOcrVO;
import egovframework.penalty.service.PenaltyOcrService;
import egovframework.penalty.util.ocr.OcrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("PenaltyOcrService")
public class PenaltyOcrServiceImpl implements PenaltyOcrService {
	 private static final Logger logger = LoggerFactory.getLogger(OcrUtil.class);

	 
	@Resource(name = "PenaltyOcrDAO")
	private PenaltyOcrDAO penaltyOcrDAO;

	    private static final String CLOVA_OCR_URL = "https://v9r0bzitin.apigw.ntruss.com/custom/v1/34008/eb7e7f22c9211742deeaf8e9c23e402c5528bdca9fa2eafb5a9a0d7992b39e76/general";
	    private static final String SECRET_KEY = "ampzS0xEUHBWV2lBWHNaVlVNS2RPenhDTWhoR0xvemM=";
	    
	    @Override
	    public Map<String, String> processOcrFile(MultipartFile file) throws Exception {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        headers.set("X-OCR-SECRET", SECRET_KEY);

	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("file", file.getResource());

	        JSONObject message = new JSONObject();
	        message.put("version", "V2");
	        message.put("requestId", UUID.randomUUID().toString());
	        message.put("timestamp", System.currentTimeMillis());
	        
	        JSONArray images = new JSONArray();
	        JSONObject image = new JSONObject();
	        image.put("format", file.getContentType().split("/")[1]);
	        image.put("name", "demo");
	        images.put(image);
	        
	        message.put("images", images);
	        
	        body.add("message", message.toString());

	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.postForEntity(CLOVA_OCR_URL, requestEntity, String.class);

	        String ocrText = response.getBody();
	        logger.debug("OCR API 응답: " + ocrText);
	        return OcrUtil.extractDataFromOcrResult(ocrText);
	    }
	

	    @Override
	    public String saveOcrResult(@RequestBody Map<String, String> confirmedData) throws Exception {
	        logger.debug("Received data: " + confirmedData);
	        
	        if (confirmedData == null) {
	            logger.error("요청 데이터가 null입니다.");
	            throw new IllegalArgumentException("요청 데이터가 null입니다.");
	        }
	        
	        if (confirmedData.isEmpty()) {
	            logger.error("요청 데이터가 비어 있습니다.");
	            throw new IllegalArgumentException("요청 데이터가 비어 있습니다.");
	        }
	        
	        // 각 필드 로깅
	        for (Map.Entry<String, String> entry : confirmedData.entrySet()) {
	            logger.debug("Field: " + entry.getKey() + ", Value: " + entry.getValue());
	        }
	        
	        try {
	            PenaltyOcrVO penaltyOcrVO = OcrUtil.mapToPenaltyOcrVO(confirmedData);
	            penaltyOcrVO.setFirstRegrId("admin");
	            penaltyOcrVO.setFirstRegIpAddr("116.124.144.140");
	            penaltyOcrVO.setLastChngmnId("admin");
	            penaltyOcrVO.setLastChgeIpAddr("116.124.144.140");
	            penaltyOcrDAO.insertOcr(penaltyOcrVO);
	            logger.info("OCR 결과가 성공적으로 저장되었습니다.");
	            return "OCR 결과가 성공적으로 저장되었습니다.";
	        } catch (Exception e) {
	            logger.error("OCR 결과 저장 중 오류 발생", e);
	            throw new RuntimeException("OCR 결과 저장 중 오류가 발생했습니다: " + e.getMessage());
	        }
	    }
}