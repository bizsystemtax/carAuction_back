package egovframework.carauction.service.impl;

import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.AttachFileVO;

@Repository("commonFileDAO")
public class CommonFileDAO extends EgovAbstractMapper {
	
	//첨부파일 등록
	public void insertFile(Map<String, Object> fileParam) {
		insert("commonFileDAO.commonInsertFile", fileParam);
	}

//	//첨부파일 수정
//	public void updateFile(AttachFileVO fileVO) {
//		update("commonFileDAO.commonUpdateFile", fileVO);
//	}
//
//	//DB데이터 삭제
//	public void deleteDataFile(AttachFileVO fileVO) {
//		delete("commonFileDAO.commonDeleteFile", fileVO);	
//	}
}
