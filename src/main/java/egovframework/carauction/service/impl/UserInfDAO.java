package egovframework.carauction.service.impl;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.UserInfVO;

@Repository("UserInfDAO")
public class UserInfDAO extends EgovAbstractMapper {
	// 회원관리 목록 조회
	public List<UserInfVO> userInfListSelect(UserInfVO userInfVO) {
		// 회원관리 목록 조회 XML 호출
		return selectList("UserInfDAO.userInfListSelect", userInfVO);
	}

	// 회원 수정 모달 정보 조회
	public List<UserInfVO> userInfUpdateModalDataSelect(UserInfVO userInfVO) {
		// 회원 수정 모달 정보 조회 XML 호출
		return selectList("UserInfDAO.userInfUpdateModalDataSelect", userInfVO);
	}

	// 비밀번호 초기화
	public int userInfPwInitUpdate(UserInfVO userInfVO) {
		// 비밀번호 초기화 XML 호출
		return update("UserInfDAO.userInfPwInitUpdate", userInfVO);
	}
}