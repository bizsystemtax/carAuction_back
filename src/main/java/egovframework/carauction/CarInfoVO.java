package egovframework.carauction;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

// 차량 경매 정보 VO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoVO {

    // 순번
    private Integer rowNum;

    // 차량번호 (경매등록번호)
    private String aucRegNo;

    // 차량명 (제조사명 + 모델명)
    private String carModelNm;

    // 옵션내용 (세부사양)
    private String carLevelNm;

    // 연식
    private String aucCarMakeYr;

    // 연료 (휘발유, 디젤 등)
    private String fuelType;

    // 주행거리 (km)
    private Long aucCarDrvDis;

    // 변속기 (A:자동, M:수동)
    private String carTransGb;

    // 예정가 (원)
    private Long bidPlnPrice;

    // 입찰건수
    private Integer aucCarCc;

    // 보관장소 구분코드
    private String locImpGb;

    // 입찰등록일
    private LocalDateTime entryDate;

    // 입찰마감일
    private String bidExpDt;

    // 등록 회원사명
    private String entryIdno;
    
    // 브랜드명
    private String brHname;
    
    // 모델명
    private String modelName;
    
    // 세부모델명
    private String subModelName;
    
    // 연료타입명
    private String fuelTypeName;
    
    // 등록회원사명
    private String registrationCompanyName;
}