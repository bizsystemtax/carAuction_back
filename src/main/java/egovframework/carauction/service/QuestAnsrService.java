package egovframework.carauction.service;

import java.util.List;
import java.util.Map;

import egovframework.carauction.QuestAnsrVO;

public interface QuestAnsrService {

	//Q&A - Q&A 목록 
	public Map<String, Object> questAnsrList(QuestAnsrVO questAnsrVO) throws Exception;
	
	// Q&A 상세조회
	public Map<String, Object> getQuestAnsrDetail(QuestAnsrVO questAnsrVO) throws Exception;
	
	// Q&A 등록
	public int insQuestAnsr(Map<String, Object> paramMap) throws Exception;
	
	// Q&A 수정
	public int updQuestAnsr(Map<String, Object> paramMap) throws Exception;
	
	// Q&A 삭제
	public int delQuestAnsr(QuestAnsrVO questAnsrVO) throws Exception;
	
	// Q&A 답변작성
	public int updAnsrWrite(Map<String, Object> paramMap) throws Exception;

	
}
