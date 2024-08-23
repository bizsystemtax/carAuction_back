package egovframework.penalty.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.penalty.PenaltyOcrVO;
import egovframework.penalty.service.PenaltyOcrService;
import egovframework.penalty.util.ocr.OcrUtil;



@Service("PenaltyOcrService")
public class PenaltyOcrServiceImpl implements PenaltyOcrService {

    @Resource(name = "PenaltyOcrDAO")
    private PenaltyOcrDAO penaltyOcrDAO;
	
	 @Override
	    public Map<String, Object> processOcrFile(MultipartFile file) throws Exception {
	        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
	        file.transferTo(tempFile);

	        String rawText = OcrUtil.performOcr(tempFile);
	        Map<String, String> extractedData = OcrUtil.extractDataFromOcrResult(rawText);

	        tempFile.delete();

	        Map<String, Object> response = new HashMap<>();
	        response.put("rawText", rawText);
	        response.put("extractedData", extractedData);
	        response.put("message", "OCR 처리가 완료되었습니다. 데이터를 확인해 주세요.");

	        System.out.println("데이터확인::::::::::::::::::::::::::::::::"+rawText);
	        return response;
	    }

	    @Override
	    public String saveOcrResult(Map<String, String> confirmedData) throws Exception {
	        if (confirmedData == null || confirmedData.isEmpty()) {
	            throw new IllegalArgumentException("요청 데이터가 비어 있습니다.");
	        }

	        PenaltyOcrVO penaltyOcrVO = OcrUtil.mapToPenaltyOcrVO(confirmedData);
	        
	        penaltyOcrVO.setFirstRegrId("admin");
	        penaltyOcrVO.setFirstRegIpAddr("116.124.144.140");
	        penaltyOcrVO.setLastChngmnId("admin");
	        penaltyOcrVO.setLastChgeIpAddr("116.124.144.140");
	        
	        penaltyOcrDAO.insertOcr(penaltyOcrVO);

	        return "OCR 결과가 성공적으로 저장되었습니다.";
	    }
}
