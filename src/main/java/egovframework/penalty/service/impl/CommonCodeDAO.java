package egovframework.penalty.service.impl;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;
import egovframework.penalty.CommonCodeVO;

@Repository("CommonCodeDAO")
public class CommonCodeDAO extends EgovAbstractMapper {

	 /**
     * 목록을 조회 한다.
     *
     * @param CommonCodeVO
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<CommonCodeVO> getSelectCode(CommonCodeVO commoncodeVO) throws Exception {
		
		return (List<CommonCodeVO>) list("CommonCodeDAO.getSelectCode", commoncodeVO);
	}

}
