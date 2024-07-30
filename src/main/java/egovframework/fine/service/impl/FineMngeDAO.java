package egovframework.fine.service.impl;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.fine.FineMngeVO;

@Repository("FineMngeDAO")
public class FineMngeDAO extends EgovAbstractMapper {

	 /**
     * 목록을 조회 한다.
     *
     * @param FineMngeVO
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<FineMngeVO> getRetrieveFineMnge(FineMngeVO finemngeVO) throws Exception {
		
		return (List<FineMngeVO>) list("FineMngeDAO.getRetrieveFineMnge", finemngeVO);
	}

}
