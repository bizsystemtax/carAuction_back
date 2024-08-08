package egovframework.penalty.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.PenaltyStdManageVO;
import egovframework.penalty.service.PenaltyStdManageService;

@Service("PenaltyStdManageService")
public class PenaltyStdManageServiceImpl extends EgovAbstractServiceImpl implements PenaltyStdManageService {
	@Resource(name = "PenaltyStdManageDAO")
	private PenaltyStdManageDAO penaltyStdManageDAO;
	
	// 범칙금 발송처 기준관리 콤보박스 값 조회
	@Override
	public Map<String, Object> selectComboBoxList(ComnCdVO comnCdVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", penaltyStdManageDAO.selectComboBoxList(comnCdVO));
		
		return map;
	}
	
	// 범칙금 발송처 기준관리 조회
	@Override
	public Map<String, Object> selectPenaltyStdManageList(PenaltyStdManageVO penaltyStdManageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", penaltyStdManageDAO.selectPenaltyStdManageList(penaltyStdManageVO));
		
		return map;
	}
	
	// 고지서 발송처명 조회
	@Override
	public Map<String, Object> selectNtcdocSendPlcList(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", penaltyStdManageDAO.selectNtcdocSendPlcList(paramMap));
		
		return map;
	}
}