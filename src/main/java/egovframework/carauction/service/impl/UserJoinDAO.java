package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.UserLoginVO;


@Repository("userJoinDAO")
public class UserJoinDAO extends EgovAbstractMapper {
	
	//회원구분코드 조회
	public List<UserLoginVO> userGbCdList(UserLoginVO userLoginVO) throws Exception {
		return selectList("userJoinDAO.userGbCdList", userLoginVO);
	}
	
	//은행코드 조회
	public List<UserLoginVO> bankCdList(UserLoginVO userLoginVO) throws Exception {
		return selectList("userJoinDAO.bankCdList", userLoginVO);
	}
	
	//회원등록 (회원가입)
	public int insertUser(UserLoginVO userLoginVO) {
		return insert("userJoinDAO.insertUser", userLoginVO);
	}
	
}
