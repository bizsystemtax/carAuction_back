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

    private String manufacturer;//제조사
    private String model;//모델
    private String subModel;//세부모델
    private String fuelType;// 연료타입
    private String startYear;// 시작년도
    private String endYear;// 종료년도
    private String startDate;// 시작일
    private String endDate;// 종료일
    private String mileageStart;// 최소주행거리
    private String mileageEnd; // 최대주행거리
    private String registrationCompany;// 등록회원사
    private String loginUserId;//로그인 사용자ID
}