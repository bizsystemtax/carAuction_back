package egovframework.penalty.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.penalty.CommonCodeVO;
import egovframework.penalty.service.CommonCodeService;

@Service("CommonCodeService")
public class CommonCodeServiceImpl extends EgovAbstractServiceImpl implements CommonCodeService {

	@Resource(name = "CommonCodeDAO")
	private CommonCodeDAO cmmCodeDAO;
	
	//공통코드 조회
	@Override
	public CommonCodeVO selectCommonCode(CommonCodeVO commoncodevo) throws Exception {
		// TODO Auto-generated method stub
		return cmmCodeDAO.selectCommonCode(commoncodevo);
	}
}