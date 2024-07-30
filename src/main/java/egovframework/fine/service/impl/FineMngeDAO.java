package egovframework.fine.service.impl;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.fine.FineMngeVO;

@Repository("FineMngeDAO")
public class FineMngeDAO extends EgovAbstractMapper {

	/**
	 * @author 범칙금관리 조회 DAO
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<FineMngeVO> getRetrieveFineMnge(FineMngeVO fineMngeVO) throws Exception {
		//범칙금관리 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.getRetrieveFineMnge", fineMngeVO);
	}

}
