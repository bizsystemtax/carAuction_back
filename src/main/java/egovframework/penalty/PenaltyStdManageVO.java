package egovframework.penalty;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenaltyStdManageVO implements Serializable {
	private String sendPlcCd = "";				// 발송처 코드
	private String sendPlcSeq = "";				// 발송처 일련번호
	private String sendPlcCdSeq = "";			// 발송처 코드 + 일련번호
	private String docTypCd = "";				// 문서유형
	private String sendPlcKindCd = "";			// 발송처 종류 코드
	private String sendPlcKindNm = "";			// 발송처 종류 코드명
	private String rcptTypCd = "";				// 접수유형
	private String hdlgTypCd = "";				// 처리유형
	private String ntcdocSendPlcNm = "";		// 고지서 발송처명
	private String ntcdocSendPlcDeptNm = "";	// 고지서 발송처 부서명
	private String comnSendPlcNm = "";			// 공통 발송처명
	private String st1SendPlcOficNm = "";		// 1차 발송처 담당자명
	private String scndSendPlcOficNm = "";		// 2차 발송처 담당자명
	private String st1SendPlcTelno = "";		// 1차 발송처 전화번호
	private String scndSendPlcTelno = "";		// 2차 발송처 전화번호
	private String thrdSendPlcTelno = "";		// 3차 발송처 전화번호
	private String faxno = "";					// 팩스번호
	private String oficEmailAddr = "";			// 담당자 이메일 주소
	private String hpageAddr = "";				// 홈페이지 주소
	private String zipCd = "";					// 우편번호
	private String sendPlcAddr = "";			// 발송처 주소
	private String sendPlcSpcfcAddr = "";		// 발송처 상세 주소
	private String plclMtr = "";				// 특이사항
	private String sendPlcRem = "";				// 발송처 비고
	private String st1DeptKeyWordCts = "";		// 1차 부서 키워드 내용
	private String scndDeptKeyWordCts = "";		// 2차 부서 키워드 내용
	private String thrdDeptKeyWordCts = "";		// 3차 부서 키워드 내용
	private String useYn = "";					// 사용여부
	private String firstRegrId = "";			// 최종 등록자id
	private String firstRegTstmp = "";			// 최종 등록일자
	private String firstRegIpAddr = "";			// 최종 등록 ip주소
	private String lastChngmnId = "";			// 최종 변경자id
	private String lastChgeTstmp = "";			// 최종 변경일자
	private String lastChgeIpAddr = "";			// 최종 변경 ip주소
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
