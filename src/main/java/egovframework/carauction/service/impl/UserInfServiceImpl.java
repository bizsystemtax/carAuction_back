package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.carauction.UserInfVO;
import egovframework.carauction.service.UserInfService;
          
@Service("UserInfService")
public class UserInfServiceImpl extends EgovAbstractServiceImpl implements UserInfService {
	@Resource(name = "UserInfDAO")
	private UserInfDAO UserInfDAO;
	
	// 회원관리 목록 조회
	@Override
	public Map<String, Object> userInfListSelect(UserInfVO userInfVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", UserInfDAO.userInfListSelect(userInfVO));
		
		return map;
	}
}