package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Repository;

import egovframework.carauction.QuestAnsrVO;
import egovframework.carauction.service.QuestAnsrService;

@Repository("questAnsrService")
public class QuestAnsrServiceImpl  extends EgovAbstractServiceImpl implements QuestAnsrService {
	
	@Resource(name = "questAnsrDAO")
	private QuestAnsrDAO questAnsrDAO;
	
	// Q&A 목록
	@Override
	public Map<String, Object> questAnsrList(QuestAnsrVO questAnsrVO) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", questAnsrDAO.questAnsrList(questAnsrVO));
		
		return map;
	}
	
	// Q&A 상세조회
	@Override
	public Map<String, Object> getQuestAnsrDetail(QuestAnsrVO questAnsrVO) throws Exception{
		
		// 상세조회시 조회수 + 1
		int updCnt = questAnsrDAO.updateViewCnt(questAnsrVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultData", questAnsrDAO.getQuestAnsrDetail(questAnsrVO));
		
		return map;
		
	}
	
	// Q&A 등록
	@Override
	public int insQuestAnsr(Map<String, Object> paramMap) throws Exception{
		int result = questAnsrDAO.insQuestAnsr(paramMap);
		return result;
	}
	
	// Q&A 수정
	@Override
	public int updQuestAnsr(Map<String, Object> paramMap) throws Exception{
		int result = questAnsrDAO.updQuestAnsr(paramMap);
		return result;
	}
	
	// Q&A 삭제
	@Override
	public int delQuestAnsr(QuestAnsrVO questAnsrVO) throws Exception{
		int result = questAnsrDAO.delQuestAnsr(questAnsrVO);
		return result;
	}
	
}