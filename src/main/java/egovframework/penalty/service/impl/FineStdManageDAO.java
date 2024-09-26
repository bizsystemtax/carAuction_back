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
		return selectList("FineStdManageDAO.retrieveComboBoxList");
	}

	public List<FineStdManageVO> retrieveFineStdManageList(FineStdManageVO FineStdManageVO) {
		return selectList("FineStdManageDAO.retrieveFineStdManageList", FineStdManageVO);
	}
	
	public List<FineStdManageVO> retrieveNtcdocSendPlcList(Map<String, Object> paramMap) {
		return selectList("FineStdManageDAO.retrieveNtcdocSendPlcNmList", paramMap);
	}
	
	public Map<String, Object> retrieveMaxSendPlcCd() {
		return selectOne("FineStdManageDAO.retrieveMaxSendPlcCd");
	}
	
	public Map<String, Object> retrieveMaxSendPlcSeq(Map<String, Object> paramMap) {
		return selectOne("FineStdManageDAO.retrieveMaxSendPlcSeq", paramMap);
	}
	
	public int insertSendPlcData(Map<String, Object> paramMap) {
		return insert("FineStdManageDAO.insertSendPlcData", paramMap);
	}
	
	public int updateSendPlcData(Map<String, Object> paramMap) {
		return update("FineStdManageDAO.updateSendPlcData", paramMap);
	}
	
	public int deleteSendPlcData(FineStdManageVO fineStdManageVO) {
		// 발송처 삭제 XML 호출
		return delete("FineStdManageDAO.deleteSendPlcData", fineStdManageVO);
	}
	
	//다운로드 조회
	public List<FineStdManageVO> downloadSendPlc(Map<String, Object> paramMap) {
		//다운로드 조회 XML 호출
		return selectList("FineStdManageDAO.retrieveDownloadSendPlc", paramMap);
	}
}