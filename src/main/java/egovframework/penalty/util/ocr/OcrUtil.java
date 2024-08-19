package egovframework.penalty.util.ocr;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import egovframework.penalty.PenaltyOcrVO;
import net.sourceforge.tess4j.Tesseract;

public class OcrUtil {

    public static String performOcr(File file) throws Exception {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata/");
        tesseract.setLanguage("kor4");
        return tesseract.doOCR(file);
    }

    public static Map<String, String> extractDataFromOcrResult(String ocrText) {
        Map<String, String> data = new HashMap<>();

        // 차량번호 패턴
        Pattern carNumberPattern = Pattern.compile("\\d{3}[가-힣]\\d{4}");
        Matcher carNumberMatcher = carNumberPattern.matcher(ocrText);
        if (carNumberMatcher.find()) {
            data.put("차량번호", carNumberMatcher.group());
        } else {
            data.put("차량번호", "");
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
                data.put("위반일자", "");
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
                data.put("위반시각", "");
            }
        }
        
        // 계좌번호 가상계좌번호 패턴
        Pattern accountNumberPattern = Pattern.compile("\\d{6}-\\d{2}-\\d{6}");
        Matcher accountNumberMatcher = accountNumberPattern.matcher(ocrText);
        if (accountNumberMatcher.find()) {
            data.put("납부계좌", accountNumberMatcher.group());
        } else {
            data.put("납부계좌", "");
        }
        
        // 고지서번호 패턴
        Pattern billPattern = Pattern.compile("\\d{8}");
        Matcher billMatcher = billPattern.matcher(ocrText);
        if (billMatcher.find()) {
            data.put("고지서번호", billMatcher.group());
        } else {
            data.put("고지서번호", "");
        }

        // 은행 패턴 (다양한 형식)
        Pattern bankPattern = Pattern.compile("\\b(국민은행|우리은행|SC제일은행|한국씨티은행|신한은행|하나은행|수협은행|NH농협은행|국민|우리|제일|씨티|신한|하나|수협|농협)\\b");
        Matcher bankMatcher = bankPattern.matcher(ocrText);

        String fullName = "";

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
        
        data.put("납부은행", fullName);
        
     // 주소 패턴 (기본 주소)
        String baseAddressRegex = "([가-힣]{2,}(시|도))\\s*([가-힣]{2,}(구|군))\\s*([가-힣\\d]+(로|길)\\s*\\d+)";
        Pattern baseAddressPattern = Pattern.compile(baseAddressRegex);
        Matcher baseAddressMatcher = baseAddressPattern.matcher(ocrText);

        if (baseAddressMatcher.find()) {
            String baseAddress = baseAddressMatcher.group().trim();
            data.put("발송처주소", baseAddress);

            // 상세 주소 패턴
            String detailAddressRegex = "\\s+(\\d+[동|호|층])?\\s*([가-힣\\d]*[아파트|빌라|빌딩|타워|센터|마을])?";
            Pattern detailAddressPattern = Pattern.compile(Pattern.quote(baseAddress) + detailAddressRegex);
            Matcher detailAddressMatcher = detailAddressPattern.matcher(ocrText);

            if (detailAddressMatcher.find()) {
                String fullAddress = detailAddressMatcher.group().trim();
                String detailAddress = fullAddress.substring(baseAddress.length()).trim();
                data.put("발송처상세주소", detailAddress);
            } else {
                data.put("발송처상세주소", "");
            }
        } else {
            data.put("발송처주소", "");
            data.put("발송처상세주소", "");
        }
        
        // 구청 패턴
        Pattern districtOfficePattern = Pattern.compile("\\b\\S*구청\\b");
        Matcher districtOfficeMatcher = districtOfficePattern.matcher(ocrText);
        if (districtOfficeMatcher.find()) {
            data.put("발급관청", districtOfficeMatcher.group());
        } else {
            data.put("발급관청", "");
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
        
        
        // 접수 일자 패턴
        Pattern dateOfReceiptPatternFull = Pattern.compile("(\\d{4})년\\s*(\\d{1,2})월\\s*(\\d{1,2})일");
        Pattern dateOfReceiptPatternShort = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})");

        Matcher dateOfReceiptMatcher = dateOfReceiptPatternFull.matcher(ocrText);
        if (dateOfReceiptMatcher.find()) {
            String year = dateOfReceiptMatcher.group(1);
            String month = dateOfReceiptMatcher.group(2);
            String day = dateOfReceiptMatcher.group(3);
            data.put("위반일자", String.format("%s년 %s월 %s일", year, month, day));
        } else {
        	dateOfReceiptMatcher = dateOfReceiptPatternShort.matcher(ocrText);
            if (dateOfReceiptMatcher.find()) {
                String year = "20" + dateOfReceiptMatcher.group(1);
                String month = dateOfReceiptMatcher.group(2);
                String day = dateOfReceiptMatcher.group(3);
                data.put("접수일자", String.format("%s년 %s월 %s일", year, month, day));
            } else {
                data.put("접수일자", "");
            }
        }
        
        data.put("위반장소", "");
        data.put("납부기한일자", "");
        data.put("범칙금", "");

        
        return data;
    }

    public static PenaltyOcrVO mapToPenaltyOcrVO(Map<String, String> extractedData) {
        PenaltyOcrVO vo = new PenaltyOcrVO();

        vo.setVhclNo(extractedData.getOrDefault("차량번호", ""));
        vo.setFineAmt(extractedData.getOrDefault("범칙금", ""));
        vo.setVltDt(extractedData.getOrDefault("위반일자", ""));
        vo.setVltAtime(extractedData.getOrDefault("위반시각", ""));
        vo.setVltCts(extractedData.getOrDefault("위반내용", ""));
        vo.setVltPnt(extractedData.getOrDefault("위반장소", ""));
        vo.setNtcdocDocNo(extractedData.getOrDefault("고지서번호", ""));
        vo.setSendEmpNo(extractedData.getOrDefault("발급관청", ""));
        vo.setPymtDdayDt(extractedData.getOrDefault("납부기한일자", ""));
        vo.setActBankNm1(extractedData.getOrDefault("납부은행", ""));
        vo.setActNo1(extractedData.getOrDefault("납부계좌", ""));
//        vo.setNtcdocImgUrl(extractedData.getOrDefault("이미지주소", ""));
//        vo.setSendPlcCd(extractedData.getOrDefault("발송처주소", ""));
//        vo.setSendPlcDtlCd(extractedData.getOrDefault("발송처상세주소", ""));
//        vo.setRcptDt(extractedData.getOrDefault("접수일자", ""));
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        vo.setFirstRegTstmp(formattedDateTime);
        vo.setLastChgeTstmp(formattedDateTime);
        vo.setFirstRegrId("SYSTEM");
        vo.setLastChngmnId("SYSTEM");
        
        return vo;
    }
}