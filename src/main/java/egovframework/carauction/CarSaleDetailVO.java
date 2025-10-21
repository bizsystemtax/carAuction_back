package egovframework.carauction;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarSaleDetailVO implements Serializable {
	
	private String aucRegNo;		// 경매등록번호
	private String carMainNo;		// 차량관리번호
	private String locImpGb;		// 내수/수입구분
	private String carEngCd;		// 차량연료코드
	private int aucCarCc;			// 경매차량CC
	private String carTransGb;		// 차량변속기구분
	//private int aucCarSeat;			// 경매차량인승
	
	private Integer aucCarSeat;			// 경매차량인승
	
	
	private int carPrice;			// 차량가격
	private String carNo;			// 차량번호
	private String aucCarCmnt;		// 경매차량비고
	//private int aucCarMakeYr;		// 경매차량생산년
	private Integer aucCarMakeYr;		// 경매차량생산년
	private int aucCarDrvDis;		// 경매차량주행거리
	private int bidPlnPrice;		// 입찰예정금액
	private int bidSdepPrice;		// 입찰보증금액	
	private double bidSdepRt;		// 입찰보증금액
	private String bidExpDt;		// 입찰유효일
	private String eomYn;			// 근저당설정여부
	private int eomPrice;			// 근저당설정금액
	private int bidCnt;				// 입찰 견수
	private String aucProgStatCd;	// 경매진행상태코드
	private String aucProgYn;		// 경매진행여부
	private int sbidPrice;			// 낙찰금액
	private String sbidDate;		// 낙찰일시
	private String sbidmnNm;		// 낙찰자명
	private String entryDate;		// 등록일시
	private String entryIdno;		// 등록자
	private String updatDate;		// 수정일시
	private String updatIdno;		// 수정자
	private String mkrCd;			// 메이커코드
	private String carModelCd;		// 차량모델코드
	private String carLevelCd;		// 등급코드
	private String carDtLevelCd;	// 등급코드
	
	
	//필수
	private String sessionId = "";	// 세션ID
	private String sessionIp = "";	// 세션IP
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
