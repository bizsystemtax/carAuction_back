package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachFileVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String attFileId = "";		// 첨부파일ID
	private String targetType = "";		// 대상타입
	private int targetId = 0;			// 대상ID         
	private String attFileNm = "";		// 첨부파일명
	private int attFileSize = 0;		// 첨부파일크기
	private String attFilePath = "";	// 첨부파일경로       
	private String attFileType = "";	// 첨부파일타입
	private String entryDate = "";		// 등록일시
	private String entryIdno = "";		// 등록자ID
	private String updatDate = "";		// 수정일시
	private String updatIdno = "";		// 수정자ID
	private int fileSeq = 0;			//파일순번
	private String fileSvrName;			//서버이름
	
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
