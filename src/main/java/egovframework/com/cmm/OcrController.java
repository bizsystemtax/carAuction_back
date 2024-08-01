package egovframework.com.cmm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    @PostMapping
    public ResponseEntity<Map<String, Object>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata/tdata/");
            tesseract.setLanguage("kor4");

            String rawText = tesseract.doOCR(tempFile);
            Map<String, String> extractedData = extractDataFromOcrResult(rawText);

            tempFile.delete();

            Map<String, Object> response = new HashMap<>();
            response.put("rawText", rawText);
            response.put("extractedData", extractedData);
            System.out.println(extractedData);
            System.out.println(rawText);

            return ResponseEntity.ok(response);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    private Map<String, String> extractDataFromOcrResult(String ocrText) {
        Map<String, String> data = new HashMap<>();

        // 차량번호 패턴
        Pattern carNumberPattern = Pattern.compile("\\d{3}[가-힣]\\d{4}");
        Matcher carNumberMatcher = carNumberPattern.matcher(ocrText);
        if (carNumberMatcher.find()) {
            data.put("차량번호", carNumberMatcher.group());
        } else {
//            data.put("차량번호", "차량번호를 찾을 수 없습니다.");
            data.put("차량번호", " ");
        }

        // 위반 일자 패턴
        Pattern violationDatePatternFull = Pattern.compile("(\\d{4})년\\s*(\\d{1,2})월\\s*(\\d{1,2})일");
        Pattern violationDatePatternShort = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})");

        Matcher violationDateMatcher = violationDatePatternFull.matcher(ocrText);
        if (violationDateMatcher.find()) {
            String year = violationDateMatcher.group(1);
            String month = violationDateMatcher.group(2);
            String day = violationDateMatcher.group(3);
            data.put("위반일자", String.format("%s년 %s월 %s일", year, month, day));
        } else {
            violationDateMatcher = violationDatePatternShort.matcher(ocrText);
            if (violationDateMatcher.find()) {
                String year = "20" + violationDateMatcher.group(1);
                String month = violationDateMatcher.group(2);
                String day = violationDateMatcher.group(3);
                data.put("위반일자", String.format("%s년 %s월 %s일", year, month, day));
            } else {
                // data.put("위반일자", "위반일자를 찾을 수 없습니다.");
                data.put("위반일자", " ");
            }
        }
        
        // 위반 시각 패턴
        Pattern violationTimePatternWithSeconds = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
        Pattern violationTimePatternWithoutSeconds = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9])");

        Matcher violationTimeMatcher = violationTimePatternWithSeconds.matcher(ocrText);
        if (violationTimeMatcher.find()) {
            data.put("위반시각", violationTimeMatcher.group());
        } else {
            violationTimeMatcher = violationTimePatternWithoutSeconds.matcher(ocrText);
            if (violationTimeMatcher.find()) {
                data.put("위반시각", violationTimeMatcher.group());
            } else {
//                data.put("위반시각", "위반시각을 찾을 수 없습니다.");
                data.put("위반시각", " ");
            }
        }
        
        // 계좌번호 패턴
        Pattern accountNumberPattern = Pattern.compile("\\d{6}-\\d{2}-\\d{6}");
        Matcher accountNumberMatcher = accountNumberPattern.matcher(ocrText);
        if (accountNumberMatcher.find()) {
            data.put("가상계좌번호", accountNumberMatcher.group());
        } else {
//            data.put("가상계좌번호", "가상계좌번호를 찾을 수 없습니다.");
            data.put("가상계좌번호", " ");
        }

        // 은행 패턴 (다양한 형식)
        Pattern bankPattern = Pattern.compile("\\b(국민은행|우리은행|SC제일은행|한국씨티은행|신한은행|하나은행|수협은행|NH농협은행|국민|우리|제일|씨티|신한|하나|수협|농협)\\b");
        Matcher bankMatcher = bankPattern.matcher(ocrText);

//        String fullName = "가상계좌은행명을 찾을 수 없습니다.";
        String fullName = " ";

        if (bankMatcher.find()) {
            String match = bankMatcher.group();
            switch (match) {
                case "국민":
                case "국민은행":
                    fullName = "국민은행";
                    break;
                case "우리":
                case "우리은행":
                    fullName = "우리은행";
                    break;
                case "제일":
                case "SC제일은행":
                    fullName = "SC제일은행";
                    break;
                case "씨티":
                case "한국씨티은행":
                    fullName = "한국씨티은행";
                    break;
                case "신한":
                case "신한은행":
                    fullName = "신한은행";
                    break;
                case "하나":
                case "하나은행":
                    fullName = "하나은행";
                    break;
                case "수협":
                case "수협은행":
                    fullName = "수협은행";
                    break;
                case "농협":
                case "NH농협은행":
                    fullName = "NH농협은행";
                    break;
            }
        }
        
        data.put("가상계좌은행명", fullName);
        
        // 주소 패턴
        String addressRegex = "([가-힣]{2,}(시|도))\\s*([가-힣]{2,}(구|군))\\s*([가-힣\\d]+(로|길)\\s*\\d+)" +
                "(\\s+\\d+[동|호|층])?" +
                "(\\s+[가-힣\\d]*[아파트|빌라|빌딩|타워|센터|마을])?";

        Pattern addressPattern = Pattern.compile(addressRegex);
        Matcher addressMatcher = addressPattern.matcher(ocrText);

        if (addressMatcher.find()) {
            data.put("주소", addressMatcher.group());
        } else {
//            data.put("주소", "주소를 찾을 수 없습니다.");
            data.put("주소", " ");
        }

        // 지로번호(고지서)  <와 + 사이의 숫자 패턴
        Pattern numberPattern = Pattern.compile("<(\\d+)\\+");
        Matcher numberMatcher = numberPattern.matcher(ocrText);
        if (numberMatcher.find()) {
            data.put("고지서번호", numberMatcher.group(1));
        } else {
//            data.put("고지서번호", "고지서번호를 찾을 수 없습니다.");
            data.put("고지서번호", " ");
        }
        
        // 구청 패턴
        Pattern districtOfficePattern = Pattern.compile("\\b\\S*구청\\b");
        Matcher districtOfficeMatcher = districtOfficePattern.matcher(ocrText);
        if (districtOfficeMatcher.find()) {
            data.put("발급관청", districtOfficeMatcher.group());
        } else {
//            data.put("발급관청", "발급관청을 찾을 수 없습니다.");
            data.put("발급관청", " ");
        }

        // 위반내용 패턴 리스트
        List<Pattern> violationPatterns = new ArrayList<>();
        violationPatterns.add(Pattern.compile("\\b\\S*주차\\S*\\b")); // ex) **주차***
        violationPatterns.add(Pattern.compile("\\b\\S*주정차\\S*\\b")); // ex) **주정차***
        violationPatterns.add(Pattern.compile("\\b\\S*통행\\S*\\b")); // ex) **통행**

        boolean found = false;
        for (Pattern pattern : violationPatterns) {
            Matcher matcher = pattern.matcher(ocrText);
            if (matcher.find()) {
                data.put("위반내용", matcher.group());
                found = true;
                break;
            }
        }

        if (!found) {
            data.put("위반내용", "위반내용을 찾을 수 없습니다.");
        }

        return data;
    }
}
