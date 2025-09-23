package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;


import egovframework.carauction.service.UserLoginService;

/**
 * 차량 경매 정보 서비스 구현 클래스 
 */
@Service("userLoginService")
public class UserLoginServiceImpl extends EgovAbstractServiceImpl implements UserLoginService {

    @Resource(name = "userLoginDAO")
    private UserLoginDAO userLoginDAO;

	/************************************************************************************************************************
	 차량 경매 정보
	************************************************************************************************************************/
    
    
    
	
}