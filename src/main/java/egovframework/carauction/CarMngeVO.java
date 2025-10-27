package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class CarMngeVO implements Serializable {

	private String carMainNo = ""; // 차량관리번호
	private String mkrCd = ""; // 메이커코드
	private String mkrNm = ""; // 메이커명
	private String carModelCd = ""; // 차량모델코드
	private String carModelNm = ""; // 차량모델명
	private String repCarYn = ""; // 대표차종여부
	private String carLevelCd = ""; // 차량등급코드
	private String carLevelNm = ""; // 차량등급명
	private String carDtLevelCd = ""; // 차량세부등급코드
	private String carDtLevelNm = ""; // 차량세부등급명
	private String locImpGb = ""; // 내수/수입구분
	private String carEngCd = ""; // 차량연료코드
	private String carEngNm = ""; // 차량연료명
	private String carKindCd = ""; // 차량종류코드
	private int carPrice = 0; // 차량가격
	private String makeStrtYrMn = ""; // 차량출시년월
	private String makeEndYrMn = ""; // 차량단종년월
	private int carCc = 0; // 차량CC
	private String startCarCc = ""; // 시작차량CC
	private String endCarCc = ""; // 끝차량CC
	private String carTransGb = ""; // 차량변속기구분
	private String useYn = ""; // 사용유무
	private String entryDate = ""; // 등록일시
	private String entryIdno = ""; // 등록자ID
	private String updatDate = ""; // 수정일시
	private String updatIdno = ""; // 수정자ID

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}