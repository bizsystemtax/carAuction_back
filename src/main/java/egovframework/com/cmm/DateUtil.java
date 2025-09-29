package egovframework.com.cmm;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import egovframework.com.cmm.service.ResultVO;

/**
 * 날짜 공통  유틸
 *
 * <pre>
 *
 *   수정일        수정자      수정내용
 *  -----------  --------  ---------------------------
 *   2025.09.29  이인하      최초 생성
 * </pre>
 */

public class DateUtil {

	public static ResultVO handleAuthError(int code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResultCode(code);
		resultVO.setResultMessage(msg);
		return resultVO;
	}
	
	public static String dateFormatYyyyMmDd(String date) {
		if(date == null || "".equals(date)) {
			return "";
		}
		if(date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return date.replaceAll("-", "");
		}
		if(date.matches(".*(Z|[+-](?:2[0-3]|[01][0-9]):?[0-5][0-9]?)$")) {
			// ex) "20250629T07:12:18.795Z" 타임존 형태의 datepicker 값인 경우
			// 1. Instant로 파싱
			Instant instant = Instant.parse(date);
			
			// 2. Instant를 한국 시간대(LocalDateTime)으로 변환
			ZoneId koreaZone = ZoneId.of("Asia/Seoul");
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, koreaZone);
			
			// 3. LocalDate로 변환 (시간 부분 제거)
			LocalDate localDate = localDateTime.toLocalDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String formattedDate = localDate.format(formatter);

			return formattedDate;
		}
		return "";
	}

}