package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.carauction.CmmCdMngeVO;
import egovframework.carauction.service.CmmCdMngeService;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;

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

	public boolean findCmmCdByCodeNo(String codeNo) throws Exception {

		logger.info("findCmmCdByCodeNo 호출 ▶▶▶▶▶▶ {}", codeNo);
		
		/*
		 * 중복시 에러 반환
		 */
		cmmCdMngeDAO.findCmmCdByCodeNo(codeNo).ifPresent((m) -> {
			throw new BizException(ErrorCode.ERR016, ErrorCode.ERR016.getMessage());
		});

		return true;
	}

}
