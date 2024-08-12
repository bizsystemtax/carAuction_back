package egovframework.penalty.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.PenaltyStdManageVO;

@Repository("PenaltyStdManageDAO")
public class PenaltyStdManageDAO extends EgovAbstractMapper {
	public List<ComnCdVO> selectComboBoxList() {
		// TODO Auto-generated method stub
		return selectList("PenaltyStdManageDAO.selectComboBoxList");
	}

	public List<PenaltyStdManageVO> selectPenaltyStdManageList(PenaltyStdManageVO penaltyStdManageVO) {
		// TODO Auto-generated method stub
		return selectList("PenaltyStdManageDAO.selectPenaltyStdManageList", penaltyStdManageVO);
	}
	
	public List<PenaltyStdManageVO> selectNtcdocSendPlcList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectList("PenaltyStdManageDAO.selectNtcdocSendPlcNmList", paramMap);
	}
	
	public Map<String, Object> selectMaxSendPlcCd() {
		// TODO Auto-generated method stub
		return selectOne("PenaltyStdManageDAO.selectMaxSendPlcCd");
	}
	
	public Map<String, Object> selectMaxSendPlcSeq(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectOne("PenaltyStdManageDAO.selectMaxSendPlcSeq", paramMap);
	}
	
	public int insertSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return insert("PenaltyStdManageDAO.insertSendPlcData", paramMap);
	}
	
	public int updateSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return update("PenaltyStdManageDAO.updateSendPlcData", paramMap);
	}
	
	public int deleteSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return delete("PenaltyStdManageDAO.deleteSendPlcData", paramMap);
	}
}