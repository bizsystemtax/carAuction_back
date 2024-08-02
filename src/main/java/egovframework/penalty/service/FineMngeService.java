package egovframework.penalty.service;

import java.util.Map;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;


public interface FineMngeService {
	//범칙금관리 검색조건 콤보박스 조회
	public Map<String, Object> retrieveComboBoxList(ComnCdVO comnCdVO) throws Exception;

	//범칙금관리 목록 조회
	public Map<String, Object> retrieveFineMnge(FineMngeVO fineMngeVO) throws Exception;
}
