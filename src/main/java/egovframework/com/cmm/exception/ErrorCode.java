package egovframework.com.cmm.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Class Name : ErrorCode
 * @Description : BizException 오류 코드 관리
 * @ 수정일      수정자  수정내용
 * @ -------- ---- ---------------------------
 * @ 20240807 이인하  최초 생성
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	//400
	ERREPT("ERREPT", ""),
	ERR000("ERR000", "오류가 발생했습니다. 다시 시도해 주세요."),
	ERR001("ERR001", "사용자 정보를 확인할 수 없습니다. 페이지를 새로고침해 주세요."),
	ERR002("ERR002", "필수 입력 항목을 모두 작성해 주세요."),
	ERR003("ERR003", "유효하지 않은 형식의 입력입니다."),
	ERR004("ERR004", "아이디 또는 비밀번호가 일치하지 않습니다."),
	ERR005("ERR005", "접근 권한이 없습니다. 로그인 후 다시 시도해 주세요."),
	ERR006("ERR006", "이미 사용 중인 아이디입니다. 다른 아이디를 입력해 주세요."),
	ERR007("ERR007", "비밀번호가 일치하지 않습니다. 다시 확인해 주세요."),
	ERR008("ERR008", "조회된 내용이 존재하지 않습니다."),
	ERR009("ERR009", "파일 업로드에 실패했습니다. 다시 시도해 주세요."),
	ERR010("ERR010", "지원하지 않는 파일 형식입니다."),
	ERR011("ERR011", "요청하신 작업을 수행할 수 없습니다. 관리자에게 문의해 주세요."),
	ERR012("ERR012", "입력한 값이 너무 깁니다. 제한된 문자 수를 초과했습니다."),
	ERR013("ERR013", "동일한 요청이 이미 처리 중입니다. 잠시 기다려 주세요."),
	ERR014("ERR014", "이미 만료된 링크입니다."),
	ERR015("ERR015", "입력한 정보에 오류가 있습니다. 다시 확인해 주세요."),
	
	//403
	ERR300("ERR300", "로그인 후 이용 가능합니다."),
	ERR301("ERR301", "세션이 만료되었습니다. 다시 로그인해 주세요."),
	
	// 코드관리
	ERR016(String.valueOf(HttpStatus.CONFLICT.value()), "중복된 코드입니다. 다시 확인해주세요"),

	//입찰등록
	ERR017("ERR017", "이미 해당 차량에 입찰하셨습니다.");
	
	private final String code;
    private final String message;
}
