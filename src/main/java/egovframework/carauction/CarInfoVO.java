package egovframework.carauction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 차량 경매 정보 VO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoVO {

    
	private Integer rowNum;                 // 순번
	private String aucRegNo;                // 차량번호 (경매등록번호)
	private String carNo;                   // 차량번호
	private String carModelNm;              // 차량명 (제조사명 + 모델명)
	private String carLevelNm;              // 옵션내용 (세부사양)
	private String aucCarMakeYr;            // 연식
	private String fuelType;                // 연료 (휘발유, 디젤 등)
	private Long aucCarDrvDis;              // 주행거리 (km)
	private String carTransGb;              // 변속기 (A:자동, M:수동)
	private Long bidPlnPrice;               // 예정가 (원)
	private Integer bidCnt;                 // 입찰건수
	private String locImpGb;                // 보관장소 구분코드
	private String entryDate;               // 입찰등록일
	private String bidExpDt;                // 입찰마감일
	private String entryIdno;               // 등록 회원사명
	private String brHname;                 // 브랜드명
	private String modelName;               // 모델명
	private String subModelName;            // 세부모델명
	private String fuelTypeName;            // 연료타입명
	private String registrationCompanyName; // 등록회원사명
	private BigDecimal bidSdepRt;           // 입찰보증금율
	private String isMyVehicle;             // 본인 등록 차량 여부
	private String contCellNo;              // 담당자 번호
	private String contEmailAddr;           // 담당자 이메일

    
    
}