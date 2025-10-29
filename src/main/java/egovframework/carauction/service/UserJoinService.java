package egovframework.carauction.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.UserLoginVO;



/**
 * 차량 경매 정보 / 차량 판매 정보 서비스 인터페이스
 */
public interface UserJoinService {

	//회원구분코드 조회
	public Map<String, Object> userGbCdList(UserLoginVO userLoginVO) throws Exception;
	
	//은행코드 조회
	public Map<String, Object> bankCdList(UserLoginVO userLoginVO) throws Exception;
	
	//사용자id 중복조회
	UserLoginVO chkUserId(String userId) throws Exception;
		
	
	//회원등록 (회원가입)
	public void insertUser(Map<String, Object> param, List<MultipartFile> files) throws Exception;
    
}