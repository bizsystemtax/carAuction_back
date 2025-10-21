package egovframework.carauction;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmmCdMngeVO implements Serializable {
	//공통코드
	private String     codeFirst = "";							//코드대분류
	private String     codeSecond = "";							//코드중분류
	private String     codeNo = "";								//코드번호
	private String     codeHname = "";							//코드명
	private String     codeKita1 = "";							//기타1
	private String     codeKita2 = "";							//기타2
	private String     codeKita3 = "";							//기타3
	private String     codeKita4 = "";							//기타4
	private String     codeKita5 = "";							//기타5
	private String     codeUseyn = "";							//사용여부
	private int        sortOrder = 0;							//정렬순서
	
}










