package egovframework.penalty.service;

import java.util.Map;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;


public interface FineMngeService {
	//범칙금관리 검색조건 콤보박스 조회
	public Map<String, Object> retrieveComboBoxList(ComnCdVO comnCdVO) throws Exception;

	//범칙금관리 목록 조회
	public Map<String, Object> retrieveFineMnge(FineMngeVO fineMngeVO) throws Exception;

	//범칙금관리 확정 상태 업데이트
	public int updateCfmtStat(FineMngeVO fineMngeVO) throws Exception;

	//발송처부서명 목록 조회
	public Map<String, Object> retrieveSendPlcDeptList(FineMngeVO fineMngeVO) throws Exception;

	//범칙금관리 수정
	public int updateFine(FineMngeVO fineMngeVO) throws Exception;
}
