package egovframework.penalty.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.PenaltyStdManageVO;

@Repository("PenaltyStdManageDAO")
public class PenaltyStdManageDAO extends EgovAbstractMapper {
	public List<ComnCdVO> selectComboBoxList(ComnCdVO comnCdVO) {
		// TODO Auto-generated method stub
		return (List<ComnCdVO>) list("PenaltyStdManageDAO.selectComboBoxList", comnCdVO);
	}

	public List<PenaltyStdManageVO> selectPenaltyStdManageList(PenaltyStdManageVO penaltyStdManageVO) {
		// TODO Auto-generated method stub
		return (List<PenaltyStdManageVO>) list("PenaltyStdManageDAO.selectPenaltyStdManageList", penaltyStdManageVO);
	}
	
	public List<PenaltyStdManageVO> selectNtcdocSendPlcList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return (List<PenaltyStdManageVO>) list("PenaltyStdManageDAO.selectNtcdocSendPlcNmList", paramMap);
	}
}