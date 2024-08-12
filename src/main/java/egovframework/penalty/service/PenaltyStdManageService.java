package egovframework.penalty.service;

import java.util.Map;

import egovframework.penalty.PenaltyStdManageVO;

public interface PenaltyStdManageService {
	// 범칙금 발송처 기준관리 콤보박스 값 조회
	public Map<String, Object> selectComboBoxList() throws Exception;

	// 범칙금 발송처 기준관리 조회
	public Map<String, Object> selectPenaltyStdManageList(PenaltyStdManageVO penaltyStdManageVO) throws Exception;
	
	// 고지서 발송처명 조회
	public Map<String, Object> selectNtcdocSendPlcList(Map<String, Object> paramMap) throws Exception;
	
	// 발송처 저장
	public Map<String, Object> insertSendPlcData(Map<String, Object> paramMap) throws Exception;
	
	// 발송처 수정
	public Map<String, Object> updateSendPlcData(Map<String, Object> paramMap) throws Exception;
	
	// 발송처 삭제
	public Map<String, Object> deleteSendPlcData(Map<String, Object> paramMap) throws Exception;
}
