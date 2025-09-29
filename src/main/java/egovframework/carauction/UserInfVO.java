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
	private String num = "";			// 순번
	private String userGbNm = "";		// 사용자구분명
	private String userNm = "";			// 사용자명
	private String contNm = "";			// 담당자명
	private String contCellno = "";		// 담당자 휴대전화번호
	private String compAddr = "";		// 회사주소
	private String compTelno = "";		// 회사전화번호
	private String aucEntryCnt = "";	// 경매등록건수
	private String aucBidProgCnt = "";	// 경매입찰진행건수
	private String aucSbidCnt = "";		// 경매낙찰건수
	private String bidEntryCnt = "";	// 입찰등록건수
	private String bidSbidCnt = "";		// 입찰낙찰건수
	private String regDate = "";		// 가입일자

	// 필수
	private String sessionId = "";	// 세션ID
	private String sessionIp = "";	// 세션IP
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
