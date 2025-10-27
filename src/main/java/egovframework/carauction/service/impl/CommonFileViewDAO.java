package egovframework.carauction.service.impl;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.AttachFileVO;

@Repository("commonFileViewDAO")
public class CommonFileViewDAO extends EgovAbstractMapper {
	
	//첨부파일 PDF, 이미지 -> 웹브라우저 미리보기, 엑셀, 워드, 한글 직접 다운로드
	public AttachFileVO getFileById(String fileId) throws Exception {
		return selectOne("commonFileViewDAO.getFileById", fileId);
	}
}
