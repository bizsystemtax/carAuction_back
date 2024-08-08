package egovframework.penalty.service;

import java.util.Map;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.PenaltyStdManageVO;

public interface PenaltyStdManageService {
	// 범칙금 발송처 기준관리 콤보박스 값 조회
	public Map<String, Object> selectComboBoxList(ComnCdVO comnCdVO) throws Exception;

	// 범칙금 발송처 기준관리 조회
	public Map<String, Object> selectPenaltyStdManageList(PenaltyStdManageVO penaltyStdManageVO) throws Exception;
	
	// 고지서 발송처명 조회
	public Map<String, Object> selectNtcdocSendPlcList(Map<String, Object> paramMap) throws Exception;
}
