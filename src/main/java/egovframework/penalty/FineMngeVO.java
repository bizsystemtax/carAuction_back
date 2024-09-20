package egovframework.penalty;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FineMngeVO implements Serializable {
	//조회조건
	private String inVltDtStrt = "";	// 위반일자시작
	private String inVltDtEnd = "";		// 위반일자종료
	private String inVltKindCd = "";	// 위반종류코드
	private String inSendPlcCd = "";	// 발송처코드
	private String inFineUploadCd = "";	// 업로드구분
	private String inCfmtYn = "";		// 확정여부
	private String inGdCd = "";			// 상품코드
	private String inCsNm = "";			// 고객명
	private String inVhclNo = "";		// 차량번호

	//BIZ_범칙금기본
	private String vltDt = "";			// 위반일자
	private String vltAtime = "";		// 위반시각
	private String vhclNo = "";			// 차량번호
	private String fineSeq = "";		// 범칙금일련번호
	private String vltKindCd = "";		// 위반종류코드
	private String sendPlcCd = "";		// 발송처코드
	private String sendPlcSeq = "";		// 발송처일련번호
	private String vltCts = "";			// 위반내용
	private String vltPnt = "";			// 위반장소
	private String rcptDt = "";			// 접수일자
	private String pymtDdayDt = "";		// 납부기한일자
	private String pymtDt = "";			// 납입일자
	private String cfmtDt = "";			// 확정일자
	private String sendDt = "";			// 발송일자
	private String sendEmpNo = "";		// 발송사원번호
	private String fineAmt = "";		// 범칙금금액
	private String ntcdocKindCd = "";	// 고지서종류코드
	private String ntcdocDocNo = "";	// 고지서문서번호
	private String ntcdocImgUrl = "";	// 고지서이미지URL
	private String actBankNm1 = "";		// 계좌은행명1
	private String actNo1 = "";			// 계좌번호1
	private String actBankNm2 = "";		// 계좌은행명2
	private String actNo2 = "";			// 계좌번호2
	private String actBankNm3 = "";		// 계좌은행명3
	private String actNo3 = "";			// 계좌번호3
	private String actBankNm4 = "";		// 계좌은행명4
	private String actNo4 = "";			// 계좌번호4
	private String drvrChgeDt = "";		// 운전자변경일자
	private String fineUploadCd = "";	// 범칙금업로드코드
	private String docFineNo1 = "";		// 문서범칙금번호1
	private String docFineNo2 = "";		// 문서범칙금번호2
	private String docFineNo3 = "";		// 문서범칙금번호3
	private String docFineNo4 = "";		// 문서범칙금번호4
	private String pnltStatNm = "";		// 과태료상태명

	//범칙금관리 조회 추가 데이터
	private String id = "";				// 순번
	private String gdNm = "";			// 상품명
	private String csNm = "";			// 고객명
	private String fineUploadNm = "";	// 범칙금업로드명
	private String cfmtStatNm = "";		// 확정여부명
	private String sendPlcNm = "";		// 발송처명
	private String sendPlcDeptNm = "";	// 발송처부서명
	private String vltKindNm = "";		// 위반종류명
	private String drvrChgeYn = "";		// 운전자변경여부
	private String loanStatNm = "";		// 대출상태명
	
	//대출정보
	private String loanNo = "";			// 대출번호
	private String loanSeq = "";		// 대출일련번호
	
	//위반종류 매핑 KEY
	private String vltKindCdKey1 = "";	// 위반종류 매핑 키워드1
	private String vltKindCdKey2 = "";	// 위반종류 매핑 키워드2
	
	//발송처코드 매핑 KEY
	private String sendPlcCdKey = "";	// 발송처 매핑 키워드
	
	//서비스 처리 후 재조회용 반환 값
	private String svcNm = "";			// 서비스명
	private String submitYn = "";		// 결과여부
	
	//파일 다운로드 공통
	private String fileName = "";		//파일명
	
	//파일 다운로드 이파인
	private String col1 = "";
	private String col2 = "";
	private String col3 = "";
	private String col4 = "";
	private String col5 = "";
	private String col6 = "";
	private String col7 = "";
	private String col8 = "";
	private String col9 = "";
	private String col10 = "";
	private String col11 = "";
	private String col12 = "";
	private String col13 = "";
	private String col14 = "";
	private String col15 = "";
	private String col16 = "";
	private String col17 = "";
	private String col18 = "";
	private String col19 = "";
	private String col20 = "";
	private String col21 = "";
	private String col22 = "";

	//필수
	private String sessionId = "";	// 세션ID
	private String sessionIp = "";	// 세션IP

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
