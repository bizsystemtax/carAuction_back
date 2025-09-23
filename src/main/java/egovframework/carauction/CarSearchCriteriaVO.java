package egovframework.carauction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString, equals, hashCode 자동 생성
@Builder // 빌더 패턴
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 생성자
public class CarSearchCriteriaVO {
    
    // 제조사
    private String manufacturer;
    
    // 모델
    private String model;
    
    // 세부모델
    private String subModel;
    
    // 연료타입
    private String fuelType;
    
    // 시작년도
    private String startYear;
    
    // 종료년도
    private String endYear;
    
    // 시작일
    private String startDate;
    
    // 종료일
    private String endDate;
    
    // 최소주행거리
    private String mileageStart;
    
    // 최대주행거리
    private String mileageEnd;
    
    // 등록회원사
    private String registrationCompany;
}