package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfVO implements Serializable {
	private static final long serialVersionUID = 5510782413086945776L;
	
	// IN
	private String inStartDate = "";	// 가입일자시작
	private String inEndDate = "";		// 가입일자종료
	private String inUserGbCd = "";		// 사용자구분코드
	private String inUserNm = "";		// 사용자명
	private String inUserId = "";		// 사용자ID
	private String inUserPw = "";		// 사용자PW

	// OUT
	private String num = "";			// 순번
	private String userGbNm = "";		// 사용자구분명
	private String userNm = "";			// 사용자명
	private String contNm = "";			// 담당자명
	private String contCellno = "";		// 담당자 휴대전화번호
	private String contEmailAddr = "";	// 담당자 휴대전화번호
	private String compAddr = "";		// 회사주소
	private String compTelno = "";		// 회사전화번호
	private String compFaxno = "";		// 회사팩스번호
	private String compHmpgAddr = "";	// 회사홈페이지주소
	private String refdBank = "";		// 환불은행
	private String refdAcc = "";		// 환불계좌번호
	private String refdAccAhdrNm = "";	// 환불계좌예금주명
	private String aucEntryCnt = "";	// 경매등록건수
	private String aucBidProgCnt = "";	// 경매입찰진행건수
	private String aucSbidCnt = "";		// 경매낙찰건수
	private String bidEntryCnt = "";	// 입찰등록건수
	private String bidSbidCnt = "";		// 입찰낙찰건수
	private String regDate = "";		// 가입일자
	private String userId = "";			// 사용자ID
	private String userPw = "";			// 사용자PW

	// 회원관리 회원건수 현황 조회조건
	private String inRegStartDate = "";	//등록일(시작)
	private String inRegEndDate = "";	//등록일(종료)
	private String inSearchCd = "";		//조회구분코드
	private String inSearchUserId = "";	//조회등록자ID
	
	// 회원관리 회원건수 현황 조회결과
	private String carListNo = "";		// 순번
	private String carNo = "";			// 차량번호
	private String carNm = "";			// 차량명
	private String carOption = "";		// 옵션내용
	private String carYear = "";		// 연식
	private String carOil = "";			// 연료
	private String mileage = "";		// 주행거리
	private String gear = "";			// 변속기
	private String estimatedCost = "";	// 예정가
	private String bidCnt = "";			// 입찰건수
	private String bidRegDate = "";		// 입찰등록일
	private String bidClosDate = "";	// 입찰마감일
	private String regMemNm = "";		// 경매(공매)등록 회원사명
	
	// 필수
	private String sessionId = "";	// 세션ID
	private String sessionIp = "";	// 세션IP
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
