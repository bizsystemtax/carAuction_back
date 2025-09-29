package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.NoticeVO;

@Repository("noticeDAO")
public class NoticeDAO extends EgovAbstractMapper {
	
	// 공지사항 목록
	public List<NoticeVO> noticeList(NoticeVO noticeVO) {
		return selectList("noticeDAO.noticeList", noticeVO);
	}
	
	// 공지사항 조회수 + 1
	public int updateViewCnt(NoticeVO noticeVO) {
		return update("noticeDAO.updateViewCnt", noticeVO);
	}
	
	// 공지사항 상세조회
	public NoticeVO getNoticeDetail(NoticeVO noticeVO) {
		return (NoticeVO) selectOne("noticeDAO.getNoticeDetail", noticeVO);
	}
	
	// 공지사항 등록
	public int insNotice(Map<String, Object> paramMap) {
		return insert("noticeDAO.insNotice", paramMap);
	}
	
	// 공지사항 수정
	public int updNotice(Map<String, Object> paramMap) {
		return update("noticeDAO.updNotice", paramMap);
	}

	// 공지사항 삭제
	public int delNotice(NoticeVO noticeVO) {
		return update("noticeDAO.delNotice", noticeVO);
	}
	
	// 첨부파일 목록 조회
	public List<AttachFileVO> getNotiFileList(AttachFileVO attachFileVO) throws Exception {
		return selectList("noticeDAO.getNotiFileList", attachFileVO);
	}
	
	// 첨부파일 등록
	
	
}