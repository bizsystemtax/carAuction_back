package egovframework.penalty.util.ocr;

import egovframework.penalty.PenaltyOcrVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class OcrUtil {
    private static final Logger logger = LoggerFactory.getLogger(OcrUtil.class);

    public static Map<String, String> extractDataFromOcrResult(String jsonResponse) {
        logger.debug("받은 JSON 응답: " + jsonResponse);

        Map<String, String> data = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray images = jsonObject.getJSONArray("images");
            if (images.length() > 0) {
                JSONObject firstImage = images.getJSONObject(0);
                JSONArray fields = firstImage.getJSONArray("fields");
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    String inferText = field.getString("inferText");
                    data.put("field" + i, inferText);
                }
            }
        } catch (JSONException e) {
            logger.error("JSON 파싱 중 오류 발생: " + e.getMessage());
        }
        
        // 추가적인 정보 추출
        extractAdditionalInfo(data);
        return data;
    }

    private static void extractAdditionalInfo(Map<String, String> data) {
        String fullText = String.join(" ", data.values());

        // 차량번호 패턴
        extractPattern(data, fullText, "차량번호", "\\d{3}[가-힣]\\d{4}");

        // 위반일자 패턴
        extractDatePattern(data, fullText, "위반일자", "(주차일시|단속일시|위반일시|통행일시)[^\\d]*(\\d{4})[-.](\\d{2})[-.](\\d{2})");

        // 위반 시각 패턴
        extractTimePattern(data, fullText, "위반시각");

        // 계좌번호 가상계좌번호 패턴
        extractPattern(data, fullText, "납부계좌", "(:?\\d{6}-\\d{2}-\\d{6}|:?\\d{3}-\\d{4}-\\d{4}-\\d{2}|:\\d{14})");

        // 고지서번호 패턴
        extractPattern(data, fullText, "고지서번호", "\\d{8}");

        // 은행 패턴
        extractBankPattern(data, fullText);

        // 주소 패턴
        extractAddressPattern(data, fullText);

        // 구청 패턴
        extractPattern(data, fullText, "발급관청", "\\b(?:\\S*구청|\\S*시장|\\S*개발공사|\\S*관리공단|\\S*대교|\\S*군수)\\b");

        // 위반내용 패턴
        extractViolationPattern(data, fullText);

        // 위반 장소 패턴
        extractLocationPattern(data, fullText);

        // 범칙금 패턴
        extractFinePattern(data, fullText);

        // 납부기한 패턴
        extractPaymentDeadlinePattern(data, fullText);
    }

    private static void extractPattern(Map<String, String> data, String text, String key, String regex) {
        if (!data.containsKey(key) || data.get(key).isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                data.put(key, matcher.group().replaceAll("^:", ""));
            }
        }
    }

    private static void extractDatePattern(Map<String, String> data, String text, String key, String regex) {
        if (!data.containsKey(key) || data.get(key).isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String year = matcher.group(2);
                String month = matcher.group(3);
                String day = matcher.group(4);
                data.put(key, String.format("%s-%s-%s", year, month, day));
            }
        }
    }

    private static void extractTimePattern(Map<String, String> data, String text, String key) {
        if (!data.containsKey(key) || data.get(key).isEmpty()) {
            Pattern patternWithSeconds = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
            Pattern patternWithoutSeconds = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9])");
            Matcher matcherWithSeconds = patternWithSeconds.matcher(text);
            if (matcherWithSeconds.find()) {
                data.put(key, matcherWithSeconds.group());
            } else {
                Matcher matcherWithoutSeconds = patternWithoutSeconds.matcher(text);
                if (matcherWithoutSeconds.find()) {
                    data.put(key, matcherWithoutSeconds.group());
                }
            }
        }
    }

    private static void extractBankPattern(Map<String, String> data, String text) {
        if (!data.containsKey("납부은행") || data.get("납부은행").isEmpty()) {
            Pattern bankPattern = Pattern.compile("\\b(국민은행|우리은행|부산은행|SC제일은행|한국씨티은행|신한은행|하나은행|수협은행|NH농협은행|국민|부산|우리|제일|씨티|신한|하나|수협|농협)\\b");
            Matcher bankMatcher = bankPattern.matcher(text);
            if (bankMatcher.find()) {
                String match = bankMatcher.group();
                String fullName = getBankFullName(match);
                data.put("납부은행", fullName);
            }
        }
    }

    private static String getBankFullName(String shortName) {
        switch (shortName) {
            case "국민": case "국민은행": return "국민은행";
            case "우리": case "우리은행": return "우리은행";
            case "제일": case "SC제일은행": return "SC제일은행";
            case "씨티": case "한국씨티은행": return "한국씨티은행";
            case "신한": case "신한은행": return "신한은행";
            case "하나": case "하나은행": return "하나은행";
            case "수협": case "수협은행": return "수협은행";
            case "농협": case "NH농협은행": return "NH농협은행";
            default: return "";
        }
    }

    private static void extractAddressPattern(Map<String, String> data, String text) {
        if ((!data.containsKey("발송처주소") || data.get("발송처주소").isEmpty()) &&
            (!data.containsKey("발송처상세주소") || data.get("발송처상세주소").isEmpty())) {
            String baseAddressRegex = "([가-힣]{2,}(시|도))\\s*([가-힣]{2,}(구|군))\\s*([가-힣\\d]+(로|길)\\s*\\d+)";
            Pattern baseAddressPattern = Pattern.compile(baseAddressRegex);
            Matcher baseAddressMatcher = baseAddressPattern.matcher(text);
            if (baseAddressMatcher.find()) {
                String baseAddress = baseAddressMatcher.group().trim();
                data.put("발송처주소", baseAddress);
                String detailAddressRegex = "\\s+(\\d+[동|호|층])?\\s*([가-힣\\d]*[아파트|빌라|빌딩|타워|센터|마을])?";
                Pattern detailAddressPattern = Pattern.compile(Pattern.quote(baseAddress) + detailAddressRegex);
                Matcher detailAddressMatcher = detailAddressPattern.matcher(text);
                if (detailAddressMatcher.find()) {
                    String fullAddress = detailAddressMatcher.group().trim();
                    String detailAddress = fullAddress.substring(baseAddress.length()).trim();
                    data.put("발송처상세주소", detailAddress);
                }
            }
        }
    }

    private static void extractViolationPattern(Map<String, String> data, String text) {
        if (!data.containsKey("위반내용") || data.get("위반내용").isEmpty()) {
            List<Pattern> violationPatterns = new ArrayList<>();
            violationPatterns.add(Pattern.compile("\\b\\S*주차\\S*\\b"));
            violationPatterns.add(Pattern.compile("\\b\\S*주정차\\S*\\b"));
            violationPatterns.add(Pattern.compile("\\b\\S*통행\\S*\\b"));
            for (Pattern pattern : violationPatterns) {
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    data.put("위반내용", matcher.group());
                    return;
                }
            }
            data.put("위반내용", "위반내용을 찾을 수 없습니다.");
        }
    }

//    private static void extractLocationPattern(Map<String, String> data, String text) {
//        if (!data.containsKey("위반장소") || data.get("위반장소").isEmpty()) {
//            String locationRegex = "(단속|위반|적발)장소\\s*[：:](\\s*[^\\n\\r]+)";
//            Pattern locationPattern = Pattern.compile(locationRegex);
//            Matcher locationMatcher = locationPattern.matcher(text);
//            if (locationMatcher.find()) {
//                String location = locationMatcher.group(2).trim();
//                data.put("위반장소", location);
//            }
//        }
//    }

    private static void extractLocationPattern(Map<String, String> data, String text) {
        if (!data.containsKey("위반장소") || data.get("위반장소").isEmpty()) {
            // 위반장소를 추출하기 위한 보다 포괄적인 정규 표현식
            String locationRegex = "(?:위반|단속|적발)[\\s:]*([\\d가-힣\\w\\s\\-.,]+)";
            Pattern locationPattern = Pattern.compile(locationRegex, Pattern.MULTILINE);
            Matcher locationMatcher = locationPattern.matcher(text);

            if (locationMatcher.find()) {
                String location = locationMatcher.group(1).trim();

                // 위치 정보를 정리하여 최종 결과에 넣기
                // 예: 불필요한 텍스트나 불필요한 공백을 제거
                location = cleanLocationText(location);
                data.put("위반장소", location);
            }
        }
    }
    private static String cleanLocationText(String text) {
        // 일반적으로 위반장소의 형식에 맞지 않는 부분을 제거
        // 예: "부과대상", "영수증", 등 불필요한 부분을 제거
        return text.replaceAll("(부과대상|영수증|납부자보관용|주소:|소재지:|\\d{3}-\\d{2}-\\d{5})", "").trim();
    }
    
    private static void extractFinePattern(Map<String, String> data, String text) {
        if (!data.containsKey("범칙금") || data.get("범칙금").isEmpty()) {
            String amountRegex = "(주정차과태료|합계금액|납기내금액|납부금액|과태료금액)\\s*:?\\s*(\\d{1,3}(,\\d{3})*)(\\s*원)?";
            Pattern amountPattern = Pattern.compile(amountRegex);
            Matcher amountMatcher = amountPattern.matcher(text);
            if (amountMatcher.find()) {
                String amountWithCommas = amountMatcher.group(2);
                String amountWithoutCommas = amountWithCommas.replaceAll(",", "");
                int amount = Integer.parseInt(amountWithoutCommas);
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
                String formattedAmount = numberFormat.format(amount);
                data.put("범칙금", formattedAmount);
            }
        }
    }

    private static void extractPaymentDeadlinePattern(Map<String, String> data, String text) {
        if (!data.containsKey("납부기한일자") || data.get("납부기한일자").isEmpty()) {
            Pattern dateAfterKeywordPattern = Pattern.compile("(납기내|납기일자|납부기한|납기)[^\\d]*(\\d{4})[-.](\\d{2})[-.](\\d{2})");
            Matcher dateOfReceiptMatcher = dateAfterKeywordPattern.matcher(text);
            if (dateOfReceiptMatcher.find()) {
                String year = dateOfReceiptMatcher.group(2);
                String month = dateOfReceiptMatcher.group(3);
                String day = dateOfReceiptMatcher.group(4);
                data.put("납부기한일자", String.format("%s-%s-%s", year, month, day));
            }
        }
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