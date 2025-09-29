package egovframework.com.cmm;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
		
		// ex) "20250629T07:12:18.795Z" 형태의 datepicker 값인 경우
        // 1. Instant로 파싱
        Instant instant = Instant.parse(date);

        // 2. 원하는 타임존으로 변환 (예: 시스템 기본 타임존)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        // 3. yyyyMMdd 포맷으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return formatter.format(zonedDateTime);
	}

}