package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.QuestAnsrVO;

@Repository("questAnsrDAO")
public class QuestAnsrDAO extends EgovAbstractMapper {
	
	// Q&A 목록
	public List<QuestAnsrVO> questAnsrList(QuestAnsrVO questAnsrVO) {
		return selectList("questAnsrDAO.questAnsrList", questAnsrVO);
	}
	
//	// Q&A 조회수 + 1
//	public int updateViewCnt(QuestAnsrVO questAnsrVO) {
//		return update("questAnsrDAO.updateViewCnt", questAnsrVO);
//	}
	
	// Q&A 상세조회
	public QuestAnsrVO getQuestAnsrDetail(QuestAnsrVO questAnsrVO) {
		return (QuestAnsrVO) selectOne("questAnsrDAO.getQuestAnsrDetail", questAnsrVO);
	}
	
	// Q&A 등록
	public int insQuestAnsr(Map<String, Object> paramMap) {
		return insert("questAnsrDAO.insQuestAnsr", paramMap);
	}
	
//	// Q&A 수정
//	public int updNotice(Map<String, Object> paramMap) {
//		return update("noticeDAO.updNotice", paramMap);
//	}
//
//	// Q&A 삭제
//	public int delNotice(NoticeVO noticeVO) {
//		return update("noticeDAO.delNotice", noticeVO);
//	}
	
}