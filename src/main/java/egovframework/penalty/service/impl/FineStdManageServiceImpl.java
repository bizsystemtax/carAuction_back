package egovframework.penalty.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.penalty.FineStdManageVO;
import egovframework.penalty.service.FineStdManageService;

@Service("FineStdManageService")
public class FineStdManageServiceImpl extends EgovAbstractServiceImpl implements FineStdManageService {
	@Resource(name = "FineStdManageDAO")
	private FineStdManageDAO FineStdManageDAO;
	
	// 범칙금 발송처 기준관리 콤보박스 값 조회
	@Override
	public Map<String, Object> retrieveComboBoxList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", FineStdManageDAO.retrieveComboBoxList());
		
		return map;
	}
	
	// 범칙금 발송처 기준관리 조회
	@Override
	public Map<String, Object> retrieveFineStdManageList(FineStdManageVO FineStdManageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", FineStdManageDAO.retrieveFineStdManageList(FineStdManageVO));
		
		return map;
	}
	
	// 고지서 발송처명 조회
	@Override
	public Map<String, Object> retrieveNtcdocSendPlcList(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", FineStdManageDAO.retrieveNtcdocSendPlcList(paramMap));
		
		return map;
	}
	
	// 발송처 저장
	@Override
	public int insertSendPlcData(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> sendPlcCdMap;
		String sendPlcCd = (String) paramMap.get("sendPlcCd");
		
		if (StringUtils.defaultString(sendPlcCd).isEmpty()) {
			// 발송처코드가 없을 경우 신규 채번
			sendPlcCdMap = FineStdManageDAO.retrieveMaxSendPlcCd();
			
			paramMap.put("sendPlcCd", String.format("%03d", ((Long) sendPlcCdMap.get("SEND_PLC_CD")).intValue()));
			paramMap.put("sendPlcSeq", "1");
		} else {
			sendPlcCdMap = new HashMap<String, Object>();
			sendPlcCdMap.put("sendPlcCd", sendPlcCd);

			// 해당 발송처코드의 발송처일련번호 채번
			Map<String, Object> sendPlcSeqMap = FineStdManageDAO.retrieveMaxSendPlcSeq(sendPlcCdMap);
			
			paramMap.put("sendPlcSeq", sendPlcSeqMap.get("SEND_PLC_SEQ"));
		}

		int cnt = FineStdManageDAO.insertSendPlcData(paramMap);
		
		return cnt;
	}
	
	// 발송처 수정
	@Override
	public int updateSendPlcData(Map<String, Object> paramMap) throws Exception {
		int cnt = FineStdManageDAO.updateSendPlcData(paramMap);
		
		return cnt;
	}
	
	// 발송처 삭제
	@Override
	public int deleteSendPlcData(FineStdManageVO fineStdManageVO) throws Exception {
		//BIZ_범칙금발송처기준관리 삭제
		int cnt = FineStdManageDAO.deleteSendPlcData(fineStdManageVO);
		
		return cnt;
	}
}