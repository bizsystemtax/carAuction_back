package egovframework.penalty.service;

import java.util.Map;

import egovframework.penalty.FineStdManageVO;

public interface FineStdManageService {
	// 범칙금 발송처 기준관리 콤보박스 값 조회
	public Map<String, Object> retrieveComboBoxList() throws Exception;

	// 범칙금 발송처 기준관리 조회
	public Map<String, Object> retrieveFineStdManageList(FineStdManageVO FineStdManageVO) throws Exception;
	
	// 고지서 발송처명 조회
	public Map<String, Object> retrieveNtcdocSendPlcList(Map<String, Object> paramMap) throws Exception;
	
	// 발송처 저장
	public int insertSendPlcData(Map<String, Object> paramMap) throws Exception;
	
	// 발송처 수정
	public int updateSendPlcData(Map<String, Object> paramMap) throws Exception;
	
	// 발송처 삭제
	public int deleteSendPlcData(FineStdManageVO fineStdManageVO) throws Exception;
}
