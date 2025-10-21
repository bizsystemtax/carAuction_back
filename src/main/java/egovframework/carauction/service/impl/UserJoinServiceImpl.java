package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.UserLoginVO;
import egovframework.carauction.service.UserJoinService;


/**
 * 차량 경매 정보 서비스 구현 클래스 
 */
@Service("userJoinService")
public class UserJoinServiceImpl extends EgovAbstractServiceImpl implements UserJoinService {

    @Resource(name = "userJoinDAO")
    private UserJoinDAO userJoinDAO;

	/************************************************************************************************************************
	 회원가입 
	************************************************************************************************************************/
    
    //회원구분코드 조회
  	@Override
  	public Map<String, Object> userGbCdList(UserLoginVO userLoginVO) throws Exception {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("resultList", userJoinDAO.userGbCdList(userLoginVO));
  		
  		return map;
  	}
  	
  	//은행코드 조회
  	@Override
  	public Map<String, Object> bankCdList(UserLoginVO userLoginVO) throws Exception {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("resultList", userJoinDAO.bankCdList(userLoginVO));
  		
  		return map;
  	}

  	//회원등록 (회원가입)
  	@Override
	public void insertUser(UserLoginVO userLoginVO) {

  		userJoinDAO.insertUser(userLoginVO);
	}
}