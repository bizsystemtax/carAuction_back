package egovframework.carauction.service;

import java.util.Map;

import egovframework.carauction.CmmCdMngeVO;

public interface CmmCdMngeService {
	public abstract Map<String, Object> findCmmCdByCodeFirstAndCodeSecond(CmmCdMngeVO cmmCdMngeVO) throws Exception;
	public abstract CmmCdMngeVO findByCodeAndCodeNo(CmmCdMngeVO cmmCdMngeVO) throws Exception;
	public abstract boolean existCode(CmmCdMngeVO cmmCdMngeVO) throws Exception;
	public abstract void insertCmmCd(CmmCdMngeVO cmmCdMngeVO) throws Exception;
	public abstract void updateCmmCd(CmmCdMngeVO cmmCdMngeVO) throws Exception;
}
