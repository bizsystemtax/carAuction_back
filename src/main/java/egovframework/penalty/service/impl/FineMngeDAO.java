package egovframework.penalty.service.impl;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;

@Repository("FineMngeDAO")
public class FineMngeDAO extends EgovAbstractMapper {
	//범칙금관리 검색조건 콤보박스 조회
	public List<ComnCdVO> retrieveComboBoxList(ComnCdVO comnCdVO) {
		// TODO Auto-generated method stub
		return (List<ComnCdVO>) list("FineMngeDAO.retrieveComboBoxList", comnCdVO);
	}
	
	//범칙금관리 목록 조회
	public List<FineMngeVO> retrieveFineMnge(FineMngeVO fineMngeVO) {
		//범칙금관리 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveFineMnge", fineMngeVO);
	}

	//범칙금관리 확정 상태 업데이트
	public List<FineMngeVO> updateCfmtStat(FineMngeVO fineMngeVO) {
		//범칙금관리 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.updateCfmtStat", fineMngeVO);
	}
}
