package egovframework.penalty.util.ocr;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OcrUtil {
	private static final Logger logger = LoggerFactory.getLogger(OcrUtil.class);

	/**
	 * OCR 결과에서 데이터를 추출하는 메인 메서드
	 * 
	 * @param jsonResponse OCR 서비스로부터 받은 JSON 응답
	 * @return 추출된 데이터를 포함하는 Map
	 */
	public static Map<String, String> extractDataFromOcrResult(String jsonResponse) {
		logger.debug("받은 JSON 응답: " + jsonResponse);

		Map<String, String> rawData = parseJsonResponse(jsonResponse);
		String region = identifyRegion(rawData);
		Map<String, String> extractedData = extractDataForRegion(rawData, region);

		// 추가적인 정보 추출
		extractAdditionalInfo(extractedData, rawData);

		// 원본 field 데이터와 추출된 데이터 병합
		Map<String, String> result = new HashMap<>(rawData);
		result.putAll(extractedData);

		return result;
	}

	// JSON 응답을 파싱하여 Map으로 변환
	private static Map<String, String> parseJsonResponse(String jsonResponse) {
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
		return data;
	}

	// 데이터를 기반으로 지역을 식별
	private static String identifyRegion(Map<String, String> data) {
		String fullText = String.join(" ", data.values());
		if (fullText.contains("금산군"))
			return "금산군";
		if (fullText.contains("김해시"))
			return "경상남도 김해시";
		if (fullText.contains("부산시"))
			return "부산시 강서구";
		if (fullText.contains("남양주시"))
			return "경기도 남양주시";
		if (fullText.contains("동구"))
			return "대전광역시 동구";
		if (fullText.contains("동작구"))
			return "서울시 동작구";
		if (fullText.contains("성남시"))
			return "경기도 성남시";
		if (fullText.contains("수원시"))
			return "경기도 수원시";
		return "unknown";
	}

	// 식별된 지역에 따라 데이터 추출
	private static Map<String, String> extractDataForRegion(Map<String, String> responseData, String region) {
		Map<String, String> extractedData = new HashMap<>();
		Map<String, String> accountInfo = parseAccountInfoFromFullResponse(responseData);
		extractedData.put("납부은행", accountInfo.get("은행명"));
		extractedData.put("납부계좌", accountInfo.get("계좌번호"));

		switch (region) {
		case "금산군":
			extractDataForGeumsan(responseData, extractedData);
			break;
		case "경상남도 김해시":
			extractDataForGyeongnamGimhae(responseData, extractedData);
			break;
		case "부산시 강서구":
			extractDataForBusanGangseogu(responseData, extractedData);
			break;
		case "경기도 남양주시":
			extractDataForGyeonggiNamyangju(responseData, extractedData);
			break;
		case "대전광역시 동구":
			extractDataForDaejeonDonggu(responseData, extractedData);
			break;
		case "서울시 동작구":
			extractDataForSeoulDongjak(responseData, extractedData);
			break;
		case "경기도 성남시":
			extractDataForGyeonggiSeongnam(responseData, extractedData);
			break;
		case "경기도 수원시":
			extractDataForGyeonggiSuwon(responseData, extractedData);
			break;
		default:
			logger.warn("알 수 없는 지역: " + region);
			break;
		}

		logger.debug("1차 추출 결과: " + extractedData);

		return extractedData;
	}

	// 각 지역별 데이터 추출 메서드들
	private static void extractDataForGeumsan(Map<String, String> responseData, Map<String, String> extractedData) {
		extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field65", "")));
		extractedData.put("위반장소",
				(responseData.getOrDefault("field70", "") + " " + responseData.getOrDefault("field71", "")).trim());
		extractedData.put("위반시각", standardizeTime(responseData.getOrDefault("field155", "")));
		extractedData.put("차량번호", responseData.getOrDefault("field151", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field174", ""));
		extractedData.put("위반내용",
				(responseData.getOrDefault("field76", "") + " " + responseData.getOrDefault("field77", "")).trim());
		extractedData.put("고지서번호", responseData.getOrDefault("field128", ""));
		extractedData.put("발급관청", responseData.getOrDefault("field11", ""));
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field79", "")));
	}

	private static void extractDataForGyeongnamGimhae(Map<String, String> responseData,
			Map<String, String> extractedData) {
		extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field156", "")));
		extractedData.put("위반장소", responseData.getOrDefault("field162", ""));
		extractedData.put("위반시각", standardizeTime(responseData.getOrDefault("field157", "")));
		extractedData.put("차량번호", responseData.getOrDefault("field75", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field169", ""));
		extractedData.put("위반내용", responseData.getOrDefault("field84", ""));
		extractedData.put("고지서번호", responseData.getOrDefault("field130", ""));
		extractedData.put("발급관청", (responseData.getOrDefault("field12", "") + " "
				+ responseData.getOrDefault("field13", "") + " " + responseData.getOrDefault("field14", "")).trim());
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field159", "")));
	}

	private static void extractDataForBusanGangseogu(Map<String, String> responseData,
			Map<String, String> extractedData) {
		extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field181", "")));
		extractedData.put("위반장소", responseData.getOrDefault("field30", ""));
		extractedData.put("위반시각", standardizeTime(responseData.getOrDefault("field66", "")));
		extractedData.put("차량번호", responseData.getOrDefault("field158", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field60", ""));
		extractedData.put("위반내용", responseData.getOrDefault("field2", ""));
		extractedData.put("고지서번호", responseData.getOrDefault("field138", ""));
		extractedData.put("발급관청", responseData.getOrDefault("field6", ""));
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field111", "")));
	}

	private static void extractDataForGyeonggiNamyangju(Map<String, String> responseData,
			Map<String, String> extractedData) {
		extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field79", "")));
		extractedData.put("위반장소",
				(responseData.getOrDefault("field214", "") + " " + responseData.getOrDefault("field215", "")).trim());
		extractedData.put("위반시각", standardizeTime(responseData.getOrDefault("field80", "")));
		extractedData.put("차량번호", responseData.getOrDefault("field124", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field91", ""));
		extractedData.put("위반내용", responseData.getOrDefault("field165", ""));
		extractedData.put("고지서번호", "");
		extractedData.put("발급관청", responseData.getOrDefault("field18", ""));
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field217", "")));
	}

	private static void extractDataForDaejeonDonggu(Map<String, String> responseData,
			Map<String, String> extractedData) {
		extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field74", "")));
		extractedData.put("위반장소", responseData.getOrDefault("field80", ""));
		extractedData.put("위반시각", standardizeTime(responseData.getOrDefault("위반시각", "")));
		extractedData.put("차량번호", responseData.getOrDefault("field167", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field56", ""));
		extractedData.put("위반내용", responseData.getOrDefault("field75", ""));
		extractedData.put("고지서번호", responseData.getOrDefault("field151", ""));
		extractedData.put("발급관청", responseData.getOrDefault("field1", ""));
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field172", "")));
	}

	private static void extractDataForSeoulDongjak(Map<String, String> responseData,
			Map<String, String> extractedData) {
		extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field183", "")));
		extractedData.put("위반장소", "");
		extractedData.put("위반시각", standardizeTime(responseData.getOrDefault("field184", "")));
		extractedData.put("차량번호", responseData.getOrDefault("field178", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field186", ""));
		extractedData.put("위반내용", responseData.getOrDefault("field50", ""));
		extractedData.put("고지서번호", responseData.getOrDefault("field128", ""));
		extractedData.put("발급관청", responseData.getOrDefault("field6", ""));
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field136", "")));
	}

	private static void extractDataForGyeonggiSeongnam(Map<String, String> responseData,
			Map<String, String> extractedData) {
		Map<String, String> dateTime = extractDateAndTime(responseData.getOrDefault("field254", ""));
		extractedData.put("위반일자", standardizeDate(dateTime.get("date")));
		extractedData.put("위반장소", removePattern(responseData.getOrDefault("field130", "")));
		extractedData.put("위반시각", standardizeTime(dateTime.get("time")));
		extractedData.put("차량번호", responseData.getOrDefault("field128", ""));
		extractedData.put("범칙금", responseData.getOrDefault("field197", ""));
		extractedData.put("위반내용", responseData.getOrDefault("field59", ""));
		extractedData.put("고지서번호", "");
		extractedData.put("발급관청", responseData.getOrDefault("field3", ""));
		extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field217", "")));
	}

	private static void extractDataForGyeonggiSuwon(Map<String, String> responseData,
			Map<String, String> extractedData) {
		boolean isAfterPaymentDeadline = checkAfterPaymentDeadline(responseData);

		if (isAfterPaymentDeadline) {
			extractedData.put("차량번호", responseData.getOrDefault("field122", ""));
			extractedData.put("범칙금", responseData.getOrDefault("field284", ""));
			extractedData.put("위반일자", standardizeDate(responseData.getOrDefault("field137", "") + " "
					+ responseData.getOrDefault("field138", "") + " " + responseData.getOrDefault("field139", "")));
			extractedData.put("위반시각", standardizeTime(
					responseData.getOrDefault("field140", "") + " " + responseData.getOrDefault("field141", "")));
			extractedData.put("위반내용",
					(responseData.getOrDefault("field169", "") + " " + responseData.getOrDefault("field170", "") + " "
							+ responseData.getOrDefault("field171", "")).trim());
			extractedData.put("위반장소", (responseData.getOrDefault("field151", "") + " "
					+ responseData.getOrDefault("field152", "") + " " + responseData.getOrDefault("field154", "") + " "
					+ responseData.getOrDefault("field155", "") + " " + responseData.getOrDefault("field162", "")));
			extractedData.put("고지서번호", responseData.getOrDefault("field251", ""));
			extractedData.put("발급관청", responseData.getOrDefault("field247", ""));
			extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field279", "") + " "
					+ responseData.getOrDefault("field280", "") + " " + responseData.getOrDefault("field281", "")));
		} else {
			String rawViolationDate = (responseData.getOrDefault("field136", "")
					+ responseData.getOrDefault("field137", "") + responseData.getOrDefault("field138", "")).trim();
			extractedData.put("위반일자", standardizeDate(rawViolationDate));
			extractedData.put("위반장소", (responseData.getOrDefault("field149", "") + " "
					+ responseData.getOrDefault("field150", "") + " " + responseData.getOrDefault("field151", "") + " "
					+ responseData.getOrDefault("field152", "") + " " + responseData.getOrDefault("field153", "") + " "
					+ responseData.getOrDefault("field154", "") + " " + responseData.getOrDefault("field161", ""))
					.trim());
			extractedData.put("위반시각",
					standardizeTime(
							responseData.getOrDefault("field139", "") + " " + responseData.getOrDefault("field140", ""))
							.trim());
			extractedData.put("차량번호", responseData.getOrDefault("field127", ""));
			extractedData.put("범칙금", responseData.getOrDefault("field134", ""));
			extractedData.put("위반내용", responseData.getOrDefault("field167", ""));
			extractedData.put("고지서번호", responseData.getOrDefault("field244", ""));
			extractedData.put("발급관청", responseData.getOrDefault("field240", ""));
			extractedData.put("납부기한일자", standardizeDate(responseData.getOrDefault("field117", "")));
		}
	}

	// 문자열에서 특정 패턴 제거
	private static String removePattern(String str) {
		return str.replaceAll("\\([-\\)]+\\)", "").trim();
	}

	// 날짜와 시간을 동시에 추출
	private static Map<String, String> extractDateAndTime(String dateTimeString) {
		Map<String, String> result = new HashMap<>();
		Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*?(\\d{2}:\\d{2})");
		Matcher matcher = pattern.matcher(dateTimeString);
		if (matcher.find()) {
			result.put("date", matcher.group(1));
			result.put("time", matcher.group(2));
		} else {
			result.put("date", "");
			result.put("time", "");
		}
		return result;
	}

	// 날짜 문자열을 표준 형식으로 변환
	private static String standardizeDate(String dateString) {
		Pattern pattern = Pattern.compile("(\\d{4})[.](\\d{2})[.](\\d{2})");
		Matcher matcher = pattern.matcher(dateString);
		if (matcher.find()) {
			return String.format("%s-%s-%s", matcher.group(1), matcher.group(2), matcher.group(3));
		}

		pattern = Pattern.compile("(\\d{4})년\\s*(\\d{2})월\\s*(\\d{2})일");
		matcher = pattern.matcher(dateString);
		if (matcher.find()) {
			return String.format("%s-%s-%s", matcher.group(1), matcher.group(2), matcher.group(3));
		}

		pattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})");
		matcher = pattern.matcher(dateString);
		if (matcher.find()) {
			return String.format("%s-%s-%s", matcher.group(1), matcher.group(2), matcher.group(3));
		}

		return dateString;
	}

	// 시간 문자열을 표준 형식으로 변환
	private static String standardizeTime(String timeString) {
		String cleanedTimeString = timeString.replaceAll("[^0-9시분:]", "");

		Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
		Matcher matcher = pattern.matcher(cleanedTimeString);
		if (matcher.find()) {
			return cleanedTimeString;
		}

		pattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})");
		matcher = pattern.matcher(cleanedTimeString);
		if (matcher.find()) {
			return String.format("%s:%s:%s", matcher.group(1), matcher.group(2), matcher.group(3));
		}

		pattern = Pattern.compile("(\\d{2}):(\\d{2})");
		matcher = pattern.matcher(cleanedTimeString);
		if (matcher.find()) {
			return String.format("%s:%s:00", matcher.group(1), matcher.group(2));
		}

		pattern = Pattern.compile("(\\d{2})(\\d{2})");
		matcher = pattern.matcher(cleanedTimeString);
		if (matcher.find()) {
			return String.format("%s:%s:00", matcher.group(1), matcher.group(2));
		}

		pattern = Pattern.compile("(\\d{1,2})시(\\d{1,2})분");
		matcher = pattern.matcher(cleanedTimeString);
		if (matcher.find()) {
			String hour = String.format("%02d", Integer.parseInt(matcher.group(1)));
			String minute = String.format("%02d", Integer.parseInt(matcher.group(2)));
			return String.format("%s:%s:00", hour, minute);
		}

		return timeString;
	}

	// 추가적인 정보 추출
	private static void extractAdditionalInfo(Map<String, String> extractedData, Map<String, String> rawData) {
		String fullText = String.join(" ", rawData.values());

		secondaryExtract(extractedData, fullText, "차량번호", "\\d{2,3}[가-힣]\\d{4}");
		secondaryExtractDate(extractedData, fullText, "위반일자",
				"(주차일시|단속일시|위반일시|통행일시)[^\\d]*(\\d{4})[-.](\\d{2})[-.](\\d{2})");
		secondaryExtractTime(extractedData, fullText, "위반시각");
		secondaryExtract(extractedData, fullText, "납부계좌",
				"(:?\\d{6}-\\d{2}-\\d{6}|:?\\d{3}-\\d{4}-\\d{4}-\\d{2}|:\\d{14})");
		secondaryExtract(extractedData, fullText, "고지서번호", "\\d{8}");
		secondaryExtractBank(extractedData, fullText);
		secondaryExtractAddress(extractedData, fullText);
		secondaryExtract(extractedData, fullText, "발급관청", "\\b(?:\\S*구청|\\S*시장|\\S*개발공사|\\S*관리공단|\\S*대교|\\S*군수)\\b");
		secondaryExtractViolation(extractedData, fullText);
		secondaryExtractLocation(extractedData, fullText);
		secondaryExtractFine(extractedData, fullText);
		secondaryExtractPaymentDeadline(extractedData, fullText);
	}

	private static void secondaryExtract(Map<String, String> data, String text, String key, String regex) {
		if (!data.containsKey(key) || data.get(key).isEmpty()) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				data.put(key, matcher.group().replaceAll("^:", ""));
			}
		}
	}

	private static void secondaryExtractDate(Map<String, String> data, String text, String key, String regex) {
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

	private static void secondaryExtractTime(Map<String, String> data, String text, String key) {
		if (!data.containsKey(key) || data.get(key).isEmpty()) {
			Pattern patternWithSeconds = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
			Pattern patternWithoutSeconds = Pattern.compile("([0-1][0-9]|2[0-3]):([0-5][0-9])");
			Matcher matcherWithSeconds = patternWithSeconds.matcher(text);
			if (matcherWithSeconds.find()) {
				data.put(key, matcherWithSeconds.group());
			} else {
				Matcher matcherWithoutSeconds = patternWithoutSeconds.matcher(text);
				if (matcherWithoutSeconds.find()) {
					data.put(key, matcherWithoutSeconds.group() + ":00");
				}
			}
		}
	}

	private static void secondaryExtractBank(Map<String, String> data, String text) {
		if (!data.containsKey("납부은행") || data.get("납부은행").isEmpty()) {
			Pattern bankPattern = Pattern
					.compile("\\b(국민은행|우리은행|부산은행|SC제일은행|한국씨티은행|신한은행|하나은행|수협은행|NH농협은행|국민|부산|우리|제일|씨티|신한|하나|수협|농협)\\b");
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
		case "국민":
		case "국민은행":
			return "국민은행";
		case "우리":
		case "우리은행":
			return "우리은행";
		case "제일":
		case "SC제일은행":
			return "SC제일은행";
		case "씨티":
		case "한국씨티은행":
			return "한국씨티은행";
		case "신한":
		case "신한은행":
			return "신한은행";
		case "하나":
		case "하나은행":
			return "하나은행";
		case "수협":
		case "수협은행":
			return "수협은행";
		case "농협":
		case "NH농협은행":
			return "NH농협은행";
		default:
			return shortName;
		}
	}

	private static void secondaryExtractAddress(Map<String, String> data, String text) {
		if ((!data.containsKey("발송처주소") || data.get("발송처주소").isEmpty())
				&& (!data.containsKey("발송처상세주소") || data.get("발송처상세주소").isEmpty())) {
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

	private static void secondaryExtractViolation(Map<String, String> data, String text) {
		if (!data.containsKey("위반내용") || data.get("위반내용").isEmpty()) {
			List<Pattern> violationPatterns = Arrays.asList(Pattern.compile("\\b\\S*주차\\S*\\b"),
					Pattern.compile("\\b\\S*주정차\\S*\\b"), Pattern.compile("\\b\\S*통행\\S*\\b"));

			for (Pattern pattern : violationPatterns) {
				Matcher matcher = pattern.matcher(text);
				if (matcher.find()) {
					data.put("위반내용", matcher.group());
					return;
				}
			}
		}
	}

	private static void secondaryExtractLocation(Map<String, String> data, String text) {
		if (!data.containsKey("위반장소") || data.get("위반장소").isEmpty()) {
			String locationRegex = "(?:위반|단속|적발)[\\s:]*([\\d가-힣\\w\\s\\-.,]+)";
			Pattern locationPattern = Pattern.compile(locationRegex, Pattern.MULTILINE);
			Matcher locationMatcher = locationPattern.matcher(text);

			if (locationMatcher.find()) {
				String location = locationMatcher.group(1).trim();
				location = cleanLocationText(location);
				data.put("위반장소", location);
			}
		}
	}

	private static String cleanLocationText(String text) {
		return text.replaceAll("(부과대상|영수증|납부자보관용|주소:|소재지:|\\d{3}-\\d{2}-\\d{5})", "").trim();
	}

	private static void secondaryExtractFine(Map<String, String> data, String text) {
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

	private static void secondaryExtractPaymentDeadline(Map<String, String> data, String text) {
		if (!data.containsKey("납부기한일자") || data.get("납부기한일자").isEmpty()) {
			Pattern dateAfterKeywordPattern = Pattern
					.compile("(납기내|납기일자|납부기한|납기)[^\\d]*(\\d{4})[-.](\\d{2})[-.](\\d{2})");
			Matcher dateOfReceiptMatcher = dateAfterKeywordPattern.matcher(text);
			if (dateOfReceiptMatcher.find()) {
				String year = dateOfReceiptMatcher.group(2);
				String month = dateOfReceiptMatcher.group(3);
				String day = dateOfReceiptMatcher.group(4);
				data.put("납부기한일자", String.format("%s-%s-%s", year, month, day));
			}
		}
	}

	// 납부기한 이후인지 확인 ex.수원 고지서에서 2가지 경우가 있는데 임시로 이것으로 구분
	private static boolean checkAfterPaymentDeadline(Map<String, String> responseData) {
		for (String value : responseData.values()) {
			if (value.contains("사전납부기한")) {
				return true;
			}
		}
		return false;
	}

	// 계좌 정보 파싱
	private static Map<String, String> parseAccountInfo(String accountString) {
		Map<String, String> result = new HashMap<>();
		String 계좌번호 = "";
		String 은행명 = "";

		Pattern bankAccountPattern = Pattern.compile(
				"(\\p{IsHangul}+)(\\d{3}-\\d{3}-\\d{8}|\\d{6}-\\d{2}-\\d{6}|\\d{3}-\\d{4}-\\d{4}-\\d{2}|\\d{14})");
		Matcher bankAccountMatcher = bankAccountPattern.matcher(accountString);
		if (bankAccountMatcher.find()) {
			은행명 = bankAccountMatcher.group(1);
			계좌번호 = bankAccountMatcher.group(2);
		} else {
			Pattern accountPattern = Pattern.compile("(:?\\d{6}-\\d{2}-\\d{6}|:?\\d{3}-\\d{4}-\\d{4}-\\d{2}|:\\d{14})");
			Matcher matcher = accountPattern.matcher(accountString);

			if (matcher.find()) {
				계좌번호 = matcher.group().replaceAll("^:", "");
			} else {
				Pattern pattern = Pattern.compile("계좌번호:([\\d-]+)");
				matcher = pattern.matcher(accountString);
				if (matcher.find()) {
					계좌번호 = matcher.group(1);
				} else {
					pattern = Pattern.compile("([^:]+):([\\d-]+)");
					matcher = pattern.matcher(accountString);
					if (matcher.find()) {
						은행명 = matcher.group(1).trim();
						계좌번호 = matcher.group(2);
					} else {
						pattern = Pattern.compile("가상계좌:\\s*\\(([^)]+)\\)([\\d-]+)");
						matcher = pattern.matcher(accountString);
						if (matcher.find()) {
							은행명 = matcher.group(1);
							계좌번호 = matcher.group(2);
						} else {
							pattern = Pattern.compile("^([\\d-]+)\\s*\\(([^)]+)\\)$");
							matcher = pattern.matcher(accountString);
							if (matcher.find()) {
								계좌번호 = matcher.group(1);
								은행명 = matcher.group(2);
							} else {
								계좌번호 = accountString.replaceAll("[^\\d-]", "");
							}
						}
					}
				}
			}

			if (은행명.isEmpty()) {
				Pattern bankPattern = Pattern.compile("\\(([^)]+은행)\\)");
				Matcher bankMatcher = bankPattern.matcher(accountString);
				if (bankMatcher.find()) {
					은행명 = bankMatcher.group(1);
				}
			}
		}
		result.put("계좌번호", 계좌번호);
		result.put("은행명", 은행명);
		return result;
	}

	// 전체 응답에서 계좌 정보 추출
	private static Map<String, String> parseAccountInfoFromFullResponse(Map<String, String> responseData) {
		Map<String, String> result = new HashMap<>();
		String 계좌번호 = "";
		String 은행명 = "";

		String fullText = String.join(" ", responseData.values());

		Map<String, String> initialResult = parseAccountInfo(fullText);
		계좌번호 = initialResult.get("계좌번호");
		은행명 = initialResult.get("은행명");

		if (은행명.isEmpty()) {
			Pattern bankPattern = Pattern
					.compile("(국민은행|우리은행|신한은행|하나은행|농협은행|기업은행|SC제일은행|씨티은행|대구은행|부산은행|경남은행|광주은행|전북은행|제주은행|수협은행|산업은행|우체국)");
			Matcher bankMatcher = bankPattern.matcher(fullText);
			if (bankMatcher.find()) {
				은행명 = bankMatcher.group();
			} else {
				bankPattern = Pattern.compile("(국민|우리|신한|하나|농협|기업|SC제일|씨티|대구|부산|경남|광주|전북|제주|수협|산업|우체국)");
				bankMatcher = bankPattern.matcher(fullText);
				if (bankMatcher.find()) {
					은행명 = bankMatcher.group() + "은행";
				}
			}
		}

		result.put("계좌번호", 계좌번호);
		result.put("은행명", 은행명);
		return result;
	}
}