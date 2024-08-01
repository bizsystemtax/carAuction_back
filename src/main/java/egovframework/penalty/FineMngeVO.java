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
	private String no = "";				// 순번
	private String gdNm = "";			// 상품명
	private String csNm = "";			// 고객명
	private String sendPlcNm = "";		// 발송처명
	private String sendPlcDeptNm = "";	// 발송처부서명
	private String vltKindNm = "";		// 위반종류명
	private String drvrChgeYn = "";		// 운전자변경여부
	private String loanStatNm = "";		// 대출상태명

	//필수
	private String firstRegrId = "";	// 최초등록자ID
	private String firstRegTstmp = "";	// 최초등록타임스탬프
	private String firstRegIpAddr = "";	// 최초등록IP주소
	private String lastChngmnId = "";	// 최종변경자ID
	private String lastChgeTstmp = "";	// 최종변경타임스탬프
	private String lastChgeIpAddr = "";	// 최종변경IP주소

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
