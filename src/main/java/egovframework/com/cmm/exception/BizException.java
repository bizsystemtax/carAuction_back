package egovframework.com.cmm.exception;

import lombok.Getter;

/**
 * @Class Name : BizException
 * @Description : BizException 오류 처리
 * @ 수정일     수정자  수정내용
 * @ -------- ---- ---------------------------
 * @ 20240807 이인하  최초 생성
 */
@Getter
public class BizException extends RuntimeException {
	private static final long serialVersionUID = -1889385519113481691L;
	private final ErrorCode errorCode;
	private final String errorMessage;
	
	public BizException(ErrorCode errorCode, String errorMessage) {
        super(errorCode.getMessage() + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
