package egovframework.penalty.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.penalty.PenaltyStdManageVO;
import egovframework.penalty.service.PenaltyStdManageService;

@Service("PenaltyStdManageService")
public class PenaltyStdManageServiceImpl extends EgovAbstractServiceImpl implements PenaltyStdManageService {
	@Resource(name = "PenaltyStdManageDAO")
	private PenaltyStdManageDAO penaltyStdManageDAO;
	
	// 범칙금 발송처 기준관리 콤보박스 값 조회
	@Override
	public Map<String, Object> selectComboBoxList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", penaltyStdManageDAO.selectComboBoxList());
		
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
	
	// 발송처 저장
	@Override
	public Map<String, Object> insertSendPlcData(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> sendPlcCdMap;
		String sendPlcCd = paramMap.containsKey("sendPlcCd") ? (String) paramMap.get("sendPlcCd") : "";
		if (sendPlcCd.equals("")) {
			sendPlcCdMap = penaltyStdManageDAO.selectMaxSendPlcCd();
			
			paramMap.put("sendPlcCd", String.format("%03d", ((Long) sendPlcCdMap.get("SEND_PLC_CD")).intValue()));
			paramMap.put("sendPlcSeq", "1");
		} else {
			sendPlcCdMap = new HashMap<String, Object>();
			sendPlcCdMap.put("sendPlcCd", sendPlcCd);
			
			Map<String, Object> sendPlcSeqMap = penaltyStdManageDAO.selectMaxSendPlcSeq(sendPlcCdMap);
			
			paramMap.put("sendPlcSeq", sendPlcSeqMap.get("SEND_PLC_SEQ"));
		}
		paramMap.put("firstRegrId", "admin");
		paramMap.put("firstRegIpAddr", "116.124.144.140");
		paramMap.put("lastChngmnId", "admin");
		paramMap.put("lastChgeIpAddr", "116.124.144.140");

		int cnt = penaltyStdManageDAO.insertSendPlcData(paramMap);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String resultCd = cnt > 0 ? "000" : "999";
		String resultMsg = cnt > 0 ? "성공적으로 저장되었습니다." : "저장에 실패했습니다.";
		
		map.put("resultCd", resultCd);
		map.put("resultMsg", resultMsg);
		
		return map;
	}
	
	// 발송처 수정
	@Override
	public Map<String, Object> updateSendPlcData(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!paramMap.containsKey("sendPlcCd") || paramMap.get("sendPlcCd").equals("") || !paramMap.containsKey("sendPlcSeq") || paramMap.get("sendPlcSeq").equals("")) {
			map.put("resultCd", "-001");
			map.put("resultMsg", "발송처 코드 또는 발송처 일련번호가 없습니다. 수정할 발송처를 선택한 후 다시 시도해주세요.");
			
			return map;
		}
		paramMap.put("lastChngmnId", "admin");
		paramMap.put("lastChgeIpAddr", "116.124.144.140");

		int cnt = penaltyStdManageDAO.updateSendPlcData(paramMap);
		
		String resultCd = cnt > 0 ? "000" : "999";
		String resultMsg = cnt > 0 ? "성공적으로 저장되었습니다." : "저장에 실패했습니다.";
		
		map.put("resultCd", resultCd);
		map.put("resultMsg", resultMsg);
		
		return map;
	}
	
	// 발송처 삭제
	@Override
	public Map<String, Object> deleteSendPlcData(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!paramMap.containsKey("sendPlcCd") || paramMap.get("sendPlcCd").equals("") || !paramMap.containsKey("sendPlcSeq") || paramMap.get("sendPlcSeq").equals("")) {
			map.put("resultCd", "-001");
			map.put("resultMsg", "발송처 코드 또는 발송처 일련번호가 없습니다. 삭제할 발송처를 선택한 후 다시 시도해주세요.");
			
			return map;
		}

		int cnt = penaltyStdManageDAO.deleteSendPlcData(paramMap);
		
		String resultCd = cnt > 0 ? "000" : "999";
		String resultMsg = cnt > 0 ? "성공적으로 삭제되었습니다." : "삭제에 실패했습니다.";
		
		map.put("resultCd", resultCd);
		map.put("resultMsg", resultMsg);
		
		return map;
	}
}