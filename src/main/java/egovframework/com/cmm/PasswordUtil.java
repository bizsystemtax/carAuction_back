package egovframework.com.cmm;

import java.security.SecureRandom;

import egovframework.com.cmm.service.ResultVO;

/**
 * 비밀번호 공통 유틸
 * 
 * https://baby9235.tistory.com/131#google_vignette 참고
 *
 * <pre>
 *
 *   수정일        수정자      수정내용
 *  -----------  --------  ---------------------------
 *   2025.10.10  이인하      최초 생성
 * </pre>
 */

public class PasswordUtil {

	public static ResultVO handleAuthError(int code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResultCode(code);
		resultVO.setResultMessage(msg);
		return resultVO;
	}
	
	public static String makeTempPassword() {
		// 영어 대문자 상수 선언
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // 영어 소문자 상수 선언
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        // 숫자 상수 선언
        final String NUMBERS = "0123456789";
        // 특수문자 상수 선언
        final String SPECIAL_CHAR = "!@#$%^&*()-_+=<>?";
        // 전체 문자열 상수 선언
        final String ALL_CHARS = UPPERCASE + LOWERCASE + NUMBERS + SPECIAL_CHAR;

        // 최소 길이 상수 선언
        final int MIN_LENGTH = 8;
        // 최대 길이 상수 선언
        final int MAX_LENGTH = 15;

        // 난수 생성기 객체
        SecureRandom random = new SecureRandom();
        // 문자열 생성 객체
        StringBuilder sb = new StringBuilder();

        // 영문 대/소문자 , 숫자, 특수문자 1자 랜덤 추가
        sb.append(getRandomChar(UPPERCASE, random));
        sb.append(getRandomChar(LOWERCASE, random));
        sb.append(getRandomChar(NUMBERS, random));
        sb.append(getRandomChar(SPECIAL_CHAR, random));

        // 8~ 15 사이의 랜덤 길이 생성
        int passwordLength = random.nextInt((MAX_LENGTH - MIN_LENGTH) + 1 ) + MIN_LENGTH;

        // 나머지 글자는 랜덤하게 채움
        // 총 길이 8~15자 중 적당한 길이로 생성
        for (int i = 4; i < passwordLength; i++) {
            sb.append(getRandomChar(ALL_CHARS, random));
        }

        // 비밀번호를 랜덤하게 섞음
        return shuffleString(sb.toString(), random);
	}
	
	// 랜던 문자 메서드
    private static String getRandomChar(String characters, SecureRandom random) {
        // 문자열에서 랜덤한 1개의 문자를 가져온다.
        return String.valueOf(characters.charAt(random.nextInt(characters.length())));
    }

    // 문자열 섞는 메서드
    private static String shuffleString(String input, SecureRandom random) {
        // 문자열을 char[] 배열로 변환
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        // char 배열을 다시 문자열로 변환
        return new String(characters);
    }
}