package egovframework.penalty.service.impl;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.penalty.CommonCodeVO;

@Repository("CommonCodeDAO")
public class CommonCodeDAO extends EgovAbstractMapper {

	public CommonCodeVO selectCommonCode(CommonCodeVO commoncodevo) {
		// TODO Auto-generated method stub
		return (CommonCodeVO)selectOne("CommonCodeDAO.selectCommonCode", commoncodevo);
	}

}