package egovframework.carauction;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachFileVO implements Serializable {
	
    public int attFileId = 0;			// 첨부파일ID
	public String targetType = "";		// 대상타입
	public int targetId = 0;			// 대상ID
	public String attFileNm = "";		// 첨부파일명
	public int attFileSize = 0;			// 첨부파일크기
	public String attFilePath = "";		// 첨부파일경로
	public String attFileType = "";		// 첨부파일타입
	private String entryDate = "";		// 등록일시
	private String entryIdno = "";		// 등록자ID
	private String updatDate = "";		// 수정일시
	private String updatIdno = "";		// 수정자ID
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
