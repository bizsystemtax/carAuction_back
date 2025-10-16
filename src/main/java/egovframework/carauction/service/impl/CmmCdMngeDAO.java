package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Optional;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CmmCdMngeVO;

@Repository("CmmCdMngeDAO")
public class CmmCdMngeDAO extends EgovAbstractMapper {
	
	public List<CmmCdMngeVO> findCmmCdByCodeFirstAndCodeSecond(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		return selectList("CmmCdMngeDAO.findCmmCdByCodeFirstAndCodeSecond", cmmCdMngeVO);
	};
	
	public Optional<CmmCdMngeVO> findCmmCdByCodeNo(CmmCdMngeVO cmmCdMngeVO) throws Exception {
		CmmCdMngeVO vo = selectOne("CmmCdMngeDAO.findCmmCdByCodeNo", cmmCdMngeVO);
		return Optional.ofNullable(vo);
	};
	

}
