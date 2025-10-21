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

	private final Logger logger = LoggerFactory.getLogger(CmmCdMngeServiceImpl.class);

	/*
	 * 다건조회
	 */
	public Map<String, Object> findCmmCdByCodeFirstAndCodeSecond(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("cmmCdList", cmmCdMngeDAO.findCmmCdByCodeFirstAndCodeSecond(cmmCdMngeVO));
		return map;
	}

	/*
	 * 단건조회
	 */
	public CmmCdMngeVO findByCodeAndCodeNo(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		logger.info("findCmmCdByCodeNo 호출 ▶▶▶▶▶▶ {}", cmmCdMngeVO.getCodeNo());

		/*
		 * 값 없으면 에러 반환
		 */
		return cmmCdMngeDAO.findCmmCdByCodeNo(cmmCdMngeVO).orElseThrow(() -> {
			throw new BizException(ErrorCode.ERR016, ErrorCode.ERR016.getMessage());
		});

	}

	public boolean existCmmCdByCode(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		logger.info("코드 존재여부 체크 ▶▶▶▶▶▶ {}", cmmCdMngeVO.getCodeNo());

		return cmmCdMngeDAO.existByCode(cmmCdMngeVO).isPresent();
	}

	/*
	 * 코드 등록
	 */
	public void insertCmmCd(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		logger.info("insertCmmCd 호출 ▶▶▶▶▶▶ {}", cmmCdMngeVO.getCodeNo());

		// 중복 체크
		findCmmCdByCodeNo(cmmCdMngeVO);

		int result = cmmCdMngeDAO.insert(cmmCdMngeVO);
		if (result <= 0) {
			throw new BizException(ErrorCode.ERR016, ErrorCode.ERR016.getMessage());
		}

	}

	/*
	 * 코드 수정
	 */
	public void updateCmmCd(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		cmmCdMngeDAO.findCmmCdByCodeNo(cmmCdMngeVO).ifPresentOrElse(existCode -> {
			/* cmmCdMngeDAO.update(); */ }, () -> {
				throw new BizException(ErrorCode.ERR016, ErrorCode.ERR016.getMessage());
			});
	}
}
