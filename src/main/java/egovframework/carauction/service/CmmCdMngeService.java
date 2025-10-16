package egovframework.carauction.service;

import java.util.Map;

import egovframework.carauction.CmmCdMngeVO;

public interface CmmCdMngeService {
	
	public abstract Map<String, Object> findCmmCdByCodeFirstAndCodeSecond(CmmCdMngeVO cmmCdMngeVO) throws Exception;
	public abstract boolean findCmmCdByCodeNo(CmmCdMngeVO cmmCdMngeVO) throws Exception;

}
