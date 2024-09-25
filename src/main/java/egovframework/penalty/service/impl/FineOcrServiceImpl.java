package egovframework.penalty.service.impl;

import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import egovframework.penalty.service.FineOcrService;
import egovframework.penalty.util.ocr.OcrUtil;

@Service("FineOcrService")
public class FineOcrServiceImpl implements FineOcrService {
	 private static final Logger logger = LoggerFactory.getLogger(OcrUtil.class);

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
}