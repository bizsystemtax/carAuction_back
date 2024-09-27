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
	ERR002("ERR002", "확정된 내역은 처리할 수 없습니다."),
	ERR003("ERR003", "확정 처리 중 오류가 발생했습니다."),
	ERR004("ERR004", "존재하지 않는 범칙금 내역입니다. 재조회 후 다시 시도하세요."),
	ERR005("ERR005", "수정 처리 중 오류가 발생했습니다."),
	ERR006("ERR006", "삭제 처리 중 오류가 발생했습니다."),
	ERR007("ERR007", "대출 정보가 없습니다."),
	ERR008("ERR008", "위반종류코드 조회 중 오류가 발생했습니다."),
	ERR009("ERR009", "등록 처리 중 오류가 발생했습니다."),
	ERR010("ERR010", "중복된 범칙금 내역이 존재합니다."),
	ERR011("ERR011", "범칙금 일련번호 채번 중 오류가 발생했습니다."),
	ERR012("ERR012", "범칙금 내역 또는 고객, 대출 정보가 존재하지 않습니다."),
	ERR013("ERR013", "발송처 코드 또는 발송처 일련번호가 없습니다. 삭제할 발송처를 선택한 후 다시 시도해주세요."),
	ERR014("ERR014", "발송처 정보가 존재하지 않습니다."),
	ERR015("ERR015", "문서번호 채번 중 오류가 발생했습니다."),
	
	//403
	ERR300("ERR300", "로그인 후 이용 가능합니다.");

	private final String code;
    private final String message;
}
