package egovframework.penalty;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonCodeVO implements Serializable {
	
	private String cmmCd;					//공통코드
	private String cmmCdNm;					//공통코드명
//	private String fld1Nm;					//필드명1 
//	private String fld1Cd;					//필드명1 코드
//	private String fld2Nm;					//필드명2
//	private String fld2Cd;					//필드명2 코드
//	private String fld3Nm;					//필드명3
//	private String fld3Cd;					//필드명3 코드
//	private String fld4Nm;					//필드명4
//	private String fld4Cd;					//필드명4 코드
//	private String fld5Nm;					//필드명5
//	private String fld5Cd;					//필드명5 코드
//	private String fld6Nm;					//필드명6
//	private String fld6Cd;					//필드명6 코드
//	private String fld7Nm;					//필드명7
//	private String fld7Cd;					//필드명7 코드
//	private String cdCtnt;					//코드 설명
//	private String useYn;					//사용여부
//	private String firstRegrId;				//최종 등록자id
//	private String firstRegTstmp;			//최종 등록일자
//	private String firstRegIpAddr;			//최종 등록 ip주소
//	private String lastChngmnId;			//최종 변경자id
//	private String lastChgeTstmp;			//최종 변경일자
//	private String lastChgeIpAddr;			//최종 변경 ip주소
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
