package egovframework.carauction.service;

import java.util.Map;
import java.util.Optional;

import egovframework.carauction.CmmCdMngeVO;

public interface CmmCdMngeService {
	
	public abstract Map<String, Object> findCmmCdByCodeFirstAndCodeSecond(CmmCdMngeVO CmmCdMngeVO) throws Exception;
	public abstract boolean findCmmCdByCodeNo(String codeNo) throws Exception;

}
