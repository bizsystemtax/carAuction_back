package egovframework.carauction.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.NoticeVO;

@Repository("NoticeDAO")
public class NoticeDAO extends EgovAbstractMapper {
	
	// 공지사항 목록
	public List<NoticeVO> noticeList(NoticeVO noticeVO) {
		return selectList("NoticeDAO.noticeList", noticeVO);
	}
	
	// 공지사항 조회수 + 1
	public int updateViewCnt(NoticeVO noticeVO) {
		return update("NoticeDAO.updateViewCnt", noticeVO);
	}
	
	// 공지사항 상세조회
	public NoticeVO getNoticeDetail(NoticeVO noticeVO) {
		return (NoticeVO) selectOne("NoticeDAO.getNoticeDetail", noticeVO);
	}

	// 공지사항 삭제
	public int delNotice(NoticeVO noticeVO) {
		return update("NoticeDAO.delNotice", noticeVO);
	}
	
	// 첨부파일 목록 조회
	public List<AttachFileVO> getNotiFileList(AttachFileVO attachFileVO) throws Exception {
		return selectList("NoticeDAO.getNotiFileList", attachFileVO);
	}
	
	
}