package egovframework.penalty.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;
import egovframework.penalty.service.FineMngeService;
          
@Service("FineMngeService")
public class FineMngeServiceImpl extends EgovAbstractServiceImpl implements FineMngeService {
	@Resource(name = "FineMngeDAO")
	private FineMngeDAO FineMngeDAO;
	
	//범칙금관리 검색조건 콤보박스 조회
	@Override
	public Map<String, Object> retrieveComboBoxList(ComnCdVO comnCdVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveComboBoxList(comnCdVO));
		
		return map;
	}
	
	//범칙금관리 목록 조회
	@Override
	public Map<String, Object> retrieveFineMnge(FineMngeVO fineMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveFineMnge(fineMngeVO));
		
		return map;
	}

	//범칙금관리 확정 상태 업데이트
	@Override
	public int updateCfmtStat(FineMngeVO fineMngeVO) throws Exception {
		int cnt = FineMngeDAO.updateCfmtStat(fineMngeVO);
		
		return cnt;
	}

	//발송처부서명 목록 조회
	@Override
	public Map<String, Object> retrieveSendPlcDeptList(FineMngeVO fineMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveSendPlcDeptList(fineMngeVO));
		
		return map;
	}
}