package egovframework.carauction.service;

import java.util.List;
import java.util.Map;

import egovframework.carauction.QuestAnsrVO;

public interface QuestAnsrService {

	//Q&A - Q&A 목록 
	public Map<String, Object> questAnsrList(QuestAnsrVO questAnsrVO) throws Exception;
	
	// Q&A 조회수 + 1
//	public int updateViewCnt(QuestAnsrVO QuestAnsrVO) throws Exception;
	
	// Q&A 상세조회
	public Map<String, Object> getQuestAnsrDetail(QuestAnsrVO questAnsrVO) throws Exception;
	
	// Q&A 등록
	public int insQuestAnsr(Map<String, Object> paramMap) throws Exception;
//	
//	// Q&A 수정
//	public int updNotice(Map<String, Object> paramMap) throws Exception;
//	
//	// Q&A 삭제
//	public int delNotice(QuestAnsrVO QuestAnsrVO) throws Exception;

	
}
