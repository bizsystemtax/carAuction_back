package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginVO implements Serializable {
	private static final long serialVersionUID = 5510782413086945776L;
	
	//공통코드
	private String     codeFirst = "";							//코드대분류
	private String     codeSecond = "";							//코드중분류
	private String     codeNo = "";								//코드번호
	private String     codeHname = "";							//코드명
	private String     codeKita1 = "";							//기타1
	private String     codeKita2 = "";							//기타2
	private String     codeKita3 = "";							//기타3
	private String     codeKita4 = "";							//기타4
	private String     codeKita5 = "";							//기타5
	private String     codeUseyn = "";							//사용여부
	private int        sortOrder = 0;							//정렬순서

	// 회원가입정보
	private String     userId = "";				//사용자ID
	private String     userPw = "";				//사용자PW
	private String     userGbCd = "";			//사용자구분코드
	private String     userNm = "";				//사용자명
	private String     contNm = "";				//담당자명
	private String     contCellno = "";			//담당자 휴대전화번호
	private String     contEmailAddr = "";		//담당자 이메일 주소
	private String     compAddr = "";			//회사주소
	private String     compTelno = "";			//회사전화번호
	private String     compFaxno = "";			//회사팩스번호
	private String     compHmpgAddr = "";		//회사홈페이지주소
	private String     refdBank = "";			//환불은행
	private String     refdAcc = "";			//환불계좌
	private String     refdAccAhdrNm = "";		//환불계좌예금주명
	private String     entryDate = "";			//등록일시
	private String     entryIdno = "";			//등록자ID
	private String     updatDate = "";			//수정일시
	private String     updatIdno = "";			//수정자ID
	
	private String     userIdCnt = "";			//수정자ID
	
	// 필수
	private String sessionId = "";	// 세션ID
	private String sessionIp = "";	// 세션IP
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
