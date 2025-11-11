package egovframework.carauction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageVO implements Serializable {

	private static final long serialVersionUID = 1L;
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
	
	//차량정보관리		
	private String     mkrCd = "";								//메이커코드
	private String     carModelCd = "";							//차량모델코드
	private String     carModelNm = "";							//차량모델명
	private String     repCarYn = "";							//대표차종여부
	private String     carLevelCd = "";							//차량등급코드
	private String     carLevelNm = "";							//차량등급명
	private String     carDtLevelCd = "";						//차량세부등급코드
	private String     carDtLevelName = "";						//차량세부등급명
	private String     carKindCd = "";							//차량종류코드
	private String     makeStrtYrMn = "";						//생산시작년월
	private String     makeEndYrMn = "";						//생산종료년월
	private int        carCc = 0;								//차량CC
	private String     useYn = "";								//사용여부
	
	//차량경매정보		
	private BigDecimal aucCarCc = new BigDecimal(0);			//경매차량CC
	private int 	   aucCarSeat = 0;							//경매차량인승
	private String     carNo = "";								//차량번호
	private String     aucCarCmnt = "";							//경매차량비고
	private String     aucCarMakeYr = "";						//경매차량생산년
	private String     aucCarDrvDis = "";						//경매차량주행거리
	private String     bidPlnPrice = "";						//입찰예정금액
	private String     bidExpDt = "";							//입찰유효일
	private String     eomYn = "";								//근저당설정여부
	private BigDecimal eomPrice = new BigDecimal(0);			//근저당설정금액
	private String     bidCnt = "";								//입찰건수
	private String     aucProgYn = "";							//경매진행여부
	private String     sbidmnNm = "";							//낙찰자명
	
	//차량경매상세입찰정보		
	private String     aucRegSeq = "";							//경매등록순서
	private String     bidmnNm = "";							//입찰자명
	private String     bidmnCellno = "";						//입찰자휴대전화번호
	private String     bidmnEmailAddr = "";						//입찰자이메일주소
	private String     bidPrice = "";							//입찰금액
	private String     bidEntryDate = "";						//입찰등록일시
	private String     depBankNm = "";							//입금은행명
	private String     depAcc = "";								//입금계좌
	private String     depmnNm = "";							//입금자명
	private String     aucProgStatCd = "";						//경매진행상태코드
	private String     sbidYn = "";								//낙찰여부
	private String     pomPayYn = "";							//대금납부완납여부
	
	//공통필드
	private BigDecimal carPrice = new BigDecimal(0);			//차량가격
	private String     carMainNo = "";							//차량관리번호
	private String     locImpGb = "";							//내수/수입구분
	private String     carEngCd = "";							//차량연료코드
	private String     carTransGb = "";							//차량변속기구분
	private String     aucRegNo = "";							//경매등록번호
	private String	   bidSdepPrice = "";						//입찰보증금액
	private String 	   sbidPrice = "";							//낙찰금액
	private String     sbidDate = "";							//낙찰일시
	private String     entryDate = "";							//등록일시
	private String     entryIdno = "";							//등록자ID
	private String     updatDate = "";							//수정일시
	private String     updatIdno = "";							//수정자ID
	private String     regStrDt = "";							//등록시작일자 추가
	private String     regEndDt = "";							//등록종료일자 추가
	private String     proState = "";							//진행상태 추가
	
	private String 	   fromDt;                                  //시작일자
	private String     toDt;									//종료일자
	private String     RNUM;									//순번
	  
	private String	   fuelType = "";                           //연료
	private String     carTransNm = "";                         //변속기명                          
	private String     aucProgStatNm = "";                      //진행상태명     
	private String     realDepPrice = "";						//실제입금예정액
	private String     entryIdnoNm = "";						//사용자명
	private String 	   flag = "";
	private String 	   sdepPayYn = "";							//보증금완납여부
	private String 	   sdepPayNm = "";							//보증금완납여부명
	private String userId;
	//private String 	   sbidAucSeq = "";							//낙찰경매순서
	
	private List<MyPageSelectedVO> selected;
    private List<MyPageUnSeledctedVO> unselected;
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}










