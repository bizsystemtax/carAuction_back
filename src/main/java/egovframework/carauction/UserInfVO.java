package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfVO implements Serializable {
	private static final long serialVersionUID = 5510782413086945776L;
	
	// 회원관리 목록 조회조건
	private String inStartDate = "";	// 가입일자시작
	private String inEndDate = "";		// 가입일자종료
	private String inUserGbCd = "";		// 회원구분코드
	private String inUserNm = "";		// 회원명

	// 회원관리 목록 조회결과
	private String vltDt = "";			// 위반일자
	
	private String num = "";
	private String userGbNm = "";
	private String userNm = "";
	private String contNm = "";
	private String contCellno = "";
	private String compAddr = "";
	private String compTelno = "";
	private String aucEntryCnt = "";
	private String aucBidProgCnt = "";
	private String aucSbidCnt = "";
	private String bidEntryCnt = "";
	private String bidSbidCnt = "";
	private String regDate = "";

	// 필수
	private String sessionId = "";	// 세션ID
	private String sessionIp = "";	// 세션IP
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
