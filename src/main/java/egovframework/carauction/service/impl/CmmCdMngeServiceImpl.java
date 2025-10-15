package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.carauction.CmmCdMngeVO;
import egovframework.carauction.service.CmmCdMngeService;

@Service("CmmCdMngeService")
public class CmmCdMngeServiceImpl extends EgovAbstractServiceImpl implements CmmCdMngeService {

	@Resource(name = "CmmCdMngeDAO")
	private CmmCdMngeDAO cmmCdMngeDAO;

	private final Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);

	public Map<String, Object> findCmmCdByCodeFirstAndCodeSecond(CmmCdMngeVO CmmCdMngeVO) throws Exception {

		Map<String, Object> map = new HashMap<>();

		String codeFrist = CmmCdMngeVO.getCodeFirst();
		String codeSecond = CmmCdMngeVO.getCodeSecond();

		map.put("cmmCdList", cmmCdMngeDAO.findCmmCdByCodeFirstAndCodeSecond(codeFrist, codeSecond));

		return map;
	}

	public CmmCdMngeVO findCmmCdByCodeNo() throws Exception {
		return null;
	}

}
