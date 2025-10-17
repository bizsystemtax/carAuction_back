package egovframework.carauction.service.impl;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.AttachFileVO;

@Repository("commonFileViewDAO")
public class CommonFileViewDAO extends EgovAbstractMapper {
	
	//첨부파일 등록
	public AttachFileVO getFileById(String fileId) throws Exception {
		return selectOne("commonFileViewDAO.getFileById", fileId);
	}
}
