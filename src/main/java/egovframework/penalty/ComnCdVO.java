package egovframework.penalty;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComnCdVO implements Serializable {
	private String comnCdEngNm = "";	// 공통코드영문명
	private String cdNm = "";			// 코드명
	private String cdVal = "";			// 코드값
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
