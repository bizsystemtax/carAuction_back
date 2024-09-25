package egovframework.penalty.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineStdManageVO;

@Repository("FineStdManageDAO")
public class FineStdManageDAO extends EgovAbstractMapper {
	public List<ComnCdVO> retrieveComboBoxList() {
		// TODO Auto-generated method stub
		return selectList("FineStdManageDAO.retrieveComboBoxList");
	}

	public List<FineStdManageVO> retrieveFineStdManageList(FineStdManageVO FineStdManageVO) {
		// TODO Auto-generated method stub
		return selectList("FineStdManageDAO.retrieveFineStdManageList", FineStdManageVO);
	}
	
	public List<FineStdManageVO> retrieveNtcdocSendPlcList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectList("FineStdManageDAO.retrieveNtcdocSendPlcNmList", paramMap);
	}
	
	public Map<String, Object> retrieveMaxSendPlcCd() {
		// TODO Auto-generated method stub
		return selectOne("FineStdManageDAO.retrieveMaxSendPlcCd");
	}
	
	public Map<String, Object> retrieveMaxSendPlcSeq(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectOne("FineStdManageDAO.retrieveMaxSendPlcSeq", paramMap);
	}
	
	public int insertSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return insert("FineStdManageDAO.insertSendPlcData", paramMap);
	}
	
	public int updateSendPlcData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return update("FineStdManageDAO.updateSendPlcData", paramMap);
	}
	
	public int deleteSendPlcData(FineStdManageVO fineStdManageVO) {
		// 발송처 삭제 XML 호출
		return delete("FineStdManageDAO.deleteSendPlcData", fineStdManageVO);
	}
}