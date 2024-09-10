package egovframework.penalty.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineStdManageVO;

@Repository("FineStdManageDAO")
public class FineStdManageDAO extends EgovAbstractMapper {
	public List<ComnCdVO> selectComboBoxList() {
		// TODO Auto-generated method stub
		return selectList("FineStdManageDAO.selectComboBoxList");
	}

	public List<FineStdManageVO> selectFineStdManageList(FineStdManageVO FineStdManageVO) {
		// TODO Auto-generated method stub
		return selectList("FineStdManageDAO.selectFineStdManageList", FineStdManageVO);
	}
	
	public List<FineStdManageVO> selectNtcdocSendPlcList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectList("FineStdManageDAO.selectNtcdocSendPlcNmList", paramMap);
	}
	
	public Map<String, Object> selectMaxSendPlcCd() {
		// TODO Auto-generated method stub
		return selectOne("FineStdManageDAO.selectMaxSendPlcCd");
	}
	
	public Map<String, Object> selectMaxSendPlcSeq(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectOne("FineStdManageDAO.selectMaxSendPlcSeq", paramMap);
	}
	
	public int insertSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return insert("FineStdManageDAO.insertSendPlcData", paramMap);
	}
	
	public int updateSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return update("FineStdManageDAO.updateSendPlcData", paramMap);
	}
	
	public int deleteSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return delete("FineStdManageDAO.deleteSendPlcData", paramMap);
	}
}