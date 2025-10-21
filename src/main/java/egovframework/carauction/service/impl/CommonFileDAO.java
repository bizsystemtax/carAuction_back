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

	//DB 테이터 삭제하기 전 데이터 조회
	public AttachFileVO selectFileInfo(AttachFileVO attachFileVO) {
		
		return selectOne("commonFileDAO.selectFileInfo", attachFileVO);
	}

	//실제 DB데이터 삭제
	public int deleteFile(AttachFileVO attachFileVO) {
		return delete("commonFileDAO.deleteFile", attachFileVO);
	}

}
