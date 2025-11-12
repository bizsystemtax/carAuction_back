package egovframework.carauction.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.UserLoginVO;
import egovframework.carauction.service.CommonFileService;
import egovframework.carauction.service.UserJoinService;
import egovframework.let.utl.sim.service.EgovFileScrty;


/**
 * 차량 경매 정보 서비스 구현 클래스 
 */
@Service("userJoinService")
public class UserJoinServiceImpl extends EgovAbstractServiceImpl implements UserJoinService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserJoinServiceImpl.class);

    @Resource(name = "userJoinDAO")
    private UserJoinDAO userJoinDAO;
    
    @Resource(name = "commonFileService")
	private CommonFileService commonFileService;

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
  	
  	//사용자id 중복조회
  	@Override
  	public UserLoginVO chkUserId(String userId) throws Exception {
		return userJoinDAO.chkUserId(userId);
	}

  	
  	//회원등록 (회원가입)
	@Override
	public void insertUser(Map<String, Object> param, List<MultipartFile> files) throws Exception {
		UserLoginVO userLoginVO = (UserLoginVO) param.get("userLoginVO");
		
		logger.info("param :::::::::::: {} ", param);
		logger.info("files ::::::::::::: {} ", files);
		
		String enpassword = EgovFileScrty.encryptPassword(userLoginVO.getUserPw(), userLoginVO.getUserId());
		userLoginVO.setUserPw(enpassword);
		
		logger.info("enpassword :::::::::::: {} ", enpassword);
		String targetId = userLoginVO.getUserId();
		String userId = userLoginVO.getUserId();
		
		
		logger.info("targetId :::::::::::: {} ", targetId );
		logger.info("userId :::::::::::: {} ", userId );
		userLoginVO.setUserId(userId); 
		
		int result = userJoinDAO.insertUser(userLoginVO);
		logger.info("result :::::::::::: {} ", result );
		
		if (result > 0 && files != null && !files.isEmpty()) {
			logger.info("일단 여기 들어와야 합니다.");
			
			param.put("userId", userId); 
			commonFileService.saveFiles("user", targetId, files, param, userId);
               
        }
		
	}

}