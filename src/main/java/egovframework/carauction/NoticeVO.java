package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeVO implements Serializable {
	
	private int noticeId = 0;			// 공지사항ID
	private String noticeTit = "";		// 공지사항제목
	private String noticeCtnt = "";		// 공지사항내용
	private int viewCnt = 0;			// 조회수
	private String useYn = "";			// 사용여부
	private String attFileYn = "";		// 첨부파일여부
	private String entryDate = "";		// 등록일시
	private String entryIdno = "";		// 등록자ID
	private String updatDate = "";		// 수정일시
	private String updatIdno = "";		// 수정자ID	
	
	private int attFileId = 0;			// 첨부파일ID
	private String targetType = "";		// 대상타입
	private String attFileNm = "";		// 첨부파일명
	
	private int prevNoticeId = 0;
	private String prevNoticeTit = "";
	private int nextNoticeId = 0;
	private String nextNoticeTit = "";
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}