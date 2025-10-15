package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Optional;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CmmCdMngeVO;

@Repository("CmmCdMngeDAO")
public abstract class CmmCdMngeDAO extends EgovAbstractMapper {
	
	public abstract List<CmmCdMngeVO> findCmmCdByCodeFirstAndCodeSecond(String codeFirst, String codeSecond) throws Exception;
	public abstract Optional<CmmCdMngeVO> findCmmCdByCodeNo(String codeNo) throws Exception;
	

}
