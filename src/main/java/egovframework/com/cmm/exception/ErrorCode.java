package egovframework.com.cmm.exception;

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
	ERR000("ERR000", "오류가 발생했습니다."),
	ERR001("ERR001", "범칙금 유효성 확인 중 오류가 발생했습니다."),
	ERR002("ERR002", "이미 확정된 범칙금 내역입니다."),
	ERR003("ERR003", "범칙금 확정 처리 중 오류가 발생했습니다."),
	ERR004("ERR004", "존재하지 않는 범칙금 내역입니다. 재조회 후 다시 시도하세요."),
	ERR005("ERR005", "범칙금 수정 처리 중 오류가 발생했습니다."),
	
	//403
	ERR300("ERR300", "로그인 후 이용 가능합니다.");

	private final String code;
    private final String message;
}
