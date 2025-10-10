package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageUnSeledctedVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String aucRegNo;
    private String aucRegSeq;
    private String aucProgStatCd;
    private String pomPayYn;
    private String depmnNm;
    private String flag;
	
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}










