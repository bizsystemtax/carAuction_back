package egovframework.penalty.util.ocr;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
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

        // 주차일시/단속일시/위반일시/통행일시 뒤에 오는 yyyy-MM-dd 또는 yyyy.MM.dd 형식의 날짜를 찾는 패턴
        Pattern dateAfterEventPattern = Pattern.compile("(주차일시|단속일시|위반일시|통행일시)[^\\d]*(\\d{4})[-.](\\d{2})[-.](\\d{2})");

        Matcher eventDateMatcher = dateAfterEventPattern.matcher(ocrText);

        if (eventDateMatcher.find()) {
            String year = eventDateMatcher.group(2);
            String month = eventDateMatcher.group(3);
            String day = eventDateMatcher.group(4);

            // 월과 일을 유효 범위 내로 보정
            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);

            // 월 보정 (1월~12월)
            if (monthInt < 1) monthInt = 1;
            if (monthInt > 12) monthInt = 12;

            // 일 보정 (1일~31일)
            if (dayInt < 1) dayInt = 1;
            if (dayInt > 31) dayInt = 31;

            data.put("위반일자", String.format("%s-%02d-%02d", year, monthInt, dayInt));
        } else {
            // 기존 로직 유지 (yyyy년 mm월 dd일 및 yyMMdd 패턴)
            Pattern dateOfEventPatternFull = Pattern.compile("(\\d{4})년\\s*(\\d{1,2})월\\s*(\\d{1,2})일");
            Pattern dateOfEventPatternShort = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})");

            eventDateMatcher = dateOfEventPatternFull.matcher(ocrText);
            if (eventDateMatcher.find()) {
                String year = eventDateMatcher.group(1);
                String month = eventDateMatcher.group(2);
                String day = eventDateMatcher.group(3);

                // 월과 일을 유효 범위 내로 보정
                int monthInt = Integer.parseInt(month);
                int dayInt = Integer.parseInt(day);

                // 월 보정 (1월~12월)
                if (monthInt < 1) monthInt = 1;
                if (monthInt > 12) monthInt = 12;

                // 일 보정 (1일~31일)
                if (dayInt < 1) dayInt = 1;
                if (dayInt > 31) dayInt = 31;

                data.put("위반일자", String.format("%s-%02d-%02d", year, monthInt, dayInt));
            } else {
                eventDateMatcher = dateOfEventPatternShort.matcher(ocrText);
                if (eventDateMatcher.find()) {
                    String year = "20" + eventDateMatcher.group(1);
                    String month = eventDateMatcher.group(2);
                    String day = eventDateMatcher.group(3);

                    // 월과 일을 유효 범위 내로 보정
                    int monthInt = Integer.parseInt(month);
                    int dayInt = Integer.parseInt(day);

                    // 월 보정 (1월~12월)
                    if (monthInt < 1) monthInt = 1;
                    if (monthInt > 12) monthInt = 12;

                    // 일 보정 (1일~31일)
                    if (dayInt < 1) dayInt = 1;
                    if (dayInt > 31) dayInt = 31;

                    data.put("위반일자", String.format("%s-%02d-%02d", year, monthInt, dayInt));
                } else {
                    data.put("위반일자", "");
                }
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
        Pattern accountNumberPattern = Pattern.compile("(:?\\d{6}-\\d{2}-\\d{6}|:?\\d{3}-\\d{4}-\\d{4}-\\d{2}|:\\d{14})");
        Matcher accountNumberMatcher = accountNumberPattern.matcher(ocrText);
        if (accountNumberMatcher.find()) {
            String accountNumber = accountNumberMatcher.group();
            // ':' 문자가 있으면 제거
            accountNumber = accountNumber.startsWith(":") ? accountNumber.substring(1) : accountNumber;
            data.put("납부계좌", accountNumber);
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
        Pattern districtOfficePattern = Pattern.compile("\\b(?:\\S*구청|\\S*시장|\\S*개발공사|\\S*관리공단|\\S*대교|\\S*군수)\\b");
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
        
        // 위반 장소 패턴
        String locationRegex = "(단속|위반|적발)장소\\s*[：:](\\s*[^\\n\\r]+)";
        Pattern locationPattern = Pattern.compile(locationRegex);
        Matcher locationMatcher = locationPattern.matcher(ocrText);

        if (locationMatcher.find()) {
            String location = locationMatcher.group(2).trim(); // 그룹 2를 사용
            data.put("위반장소", location);
            System.out.println("추출된 위반장소: " + location); // 디버깅용 출력
        } else {
            data.put("위반장소", "");
            System.out.println("위반장소를 찾을 수 없습니다."); // 디버깅용 출력
        }
        
        // 범칙금 패턴 (여러 용어 포함)
        String amountRegex = "(주정차과태료|합계금액|납기내금액|납부금액|과태료금액)\\s*:?\\s*(\\d{1,3}(,\\d{3})*)(\\s*원)?";
        Pattern amountPattern = Pattern.compile(amountRegex);
        Matcher amountMatcher = amountPattern.matcher(ocrText);

        if (amountMatcher.find()) {
            String termUsed = amountMatcher.group(1); // 사용된 용어
            String amountWithCommas = amountMatcher.group(2);
            String amountWithoutCommas = amountWithCommas.replaceAll(",", "");
            
            // 숫자 포맷을 위해 숫자 변환 및 쉼표 추가
            int amount = Integer.parseInt(amountWithoutCommas); // 문자열을 정수로 변환
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA); // 한국 로케일
            String formattedAmount = numberFormat.format(amount); //
            
            data.put("범칙금", formattedAmount);
            System.out.println("추출된 " + termUsed + ": " + formattedAmount + "원");
        } else {
            data.put("범칙금", "");
            System.out.println("범칙금을 찾을 수 없습니다.");
        }
        
     // 납기내/납기일자/납부기한/납기 뒤에 오는 yyyy-MM-dd 또는 yyyy.MM.dd 형식의 날짜를 찾는 패턴
        Pattern dateAfterKeywordPattern = Pattern.compile("(납기내|납기일자|납부기한|납기)[^\\d]*(\\d{4})[-.](\\d{2})[-.](\\d{2})");

        Matcher dateOfReceiptMatcher = dateAfterKeywordPattern.matcher(ocrText);

        if (dateOfReceiptMatcher.find()) {
            String year = dateOfReceiptMatcher.group(2);
            String month = dateOfReceiptMatcher.group(3);
            String day = dateOfReceiptMatcher.group(4);

            // 월과 일을 유효 범위 내로 보정
            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);

            // 월 보정 (1월~12월)
            if (monthInt < 1) monthInt = 1;
            if (monthInt > 12) monthInt = 12;

            // 일 보정 (1일~31일)
            if (dayInt < 1) dayInt = 1;
            if (dayInt > 31) dayInt = 31;

            data.put("납부기한일자", String.format("%s-%02d-%02d", year, monthInt, dayInt));
        } else {
            // 기존 로직 유지 (yyyy년 mm월 dd일 및 yyMMdd 패턴)
            Pattern dateOfReceiptPatternFull = Pattern.compile("(\\d{4})년\\s*(\\d{1,2})월\\s*(\\d{1,2})일");
            Pattern dateOfReceiptPatternShort = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})");

            dateOfReceiptMatcher = dateOfReceiptPatternFull.matcher(ocrText);
            if (dateOfReceiptMatcher.find()) {
                String year = dateOfReceiptMatcher.group(1);
                String month = dateOfReceiptMatcher.group(2);
                String day = dateOfReceiptMatcher.group(3);

                // 월과 일을 유효 범위 내로 보정
                int monthInt = Integer.parseInt(month);
                int dayInt = Integer.parseInt(day);

                // 월 보정 (1월~12월)
                if (monthInt < 1) monthInt = 1;
                if (monthInt > 12) monthInt = 12;

                // 일 보정 (1일~31일)
                if (dayInt < 1) dayInt = 1;
                if (dayInt > 31) dayInt = 31;

                data.put("납부기한일자", String.format("%s-%02d-%02d", year, monthInt, dayInt));
            } else {
                dateOfReceiptMatcher = dateOfReceiptPatternShort.matcher(ocrText);
                if (dateOfReceiptMatcher.find()) {
                    String year = "20" + dateOfReceiptMatcher.group(1);
                    String month = dateOfReceiptMatcher.group(2);
                    String day = dateOfReceiptMatcher.group(3);

                    // 월과 일을 유효 범위 내로 보정
                    int monthInt = Integer.parseInt(month);
                    int dayInt = Integer.parseInt(day);

                    // 월 보정 (1월~12월)
                    if (monthInt < 1) monthInt = 1;
                    if (monthInt > 12) monthInt = 12;

                    // 일 보정 (1일~31일)
                    if (dayInt < 1) dayInt = 1;
                    if (dayInt > 31) dayInt = 31;

                    data.put("납부기한일자", String.format("%s-%02d-%02d", year, monthInt, dayInt));
                } else {
                    data.put("납부기한일자", "");
                }
            }
        }

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