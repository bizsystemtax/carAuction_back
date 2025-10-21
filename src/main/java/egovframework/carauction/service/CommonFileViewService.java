package egovframework.carauction.service;

import egovframework.carauction.AttachFileVO;

public interface CommonFileViewService {
	
	//첨부파일 PDF, 이미지 -> 웹브라우저 미리보기, 엑셀, 워드, 한글 직접 다운로드
	public AttachFileVO getFileById(String fileId) throws Exception;

}
