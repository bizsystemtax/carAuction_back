package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestAnsrVO implements Serializable {
	
	private int questId = 0;			// 질문ID
	private String questTit = "";		// 질문제목
	private String questCtnt = "";		// 질문내용
	private int viewCnt = 0;			// 조회수
	private String ansrYn = "";			// 답변여부
	private String ansrCtnt = "";		// 답변내용
	private String ansrEntryIdno  = ""; // 답변등록자ID
	private String ansrEntryDate  = ""; // 답변등록일시
	private String useYn = "";			// 사용여부
	
	private String entryDate = "";		// 등록일시
	private String entryIdno = "";		// 등록자ID
	private String updatDate = "";		// 수정일시
	private String updatIdno = "";		// 수정자ID	
	
	private String startDt = "";		// 시작일자
	private String endDt = "";			// 종료일자
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}