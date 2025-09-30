package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarSaleVO implements Serializable {
	
	private String codeNo;	
	private String codeHname;
	private String carMainNo;
	private int carCc;
	private String carTransGb;
	private String codeFirst;		// 코드대분류
	private String codeSecond;		// 코드중분류
	private String codeKita5;		// 기타5
	private String mkrCd;			// 메이커코드
	private String carModelCd;		// 차량모델코드
	private String carLevelCd;		// 등급코드
	private String carDtLevelCd;	// 등급코드
	private String carEngCd;		// 연료
	private int carPrice;			// 차량갸격
	private String aucRegNo;		// 경매번호
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
