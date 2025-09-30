
package egovframework.carauction.service;

import java.util.Map;

import egovframework.carauction.UserInfVO;

public interface UserInfService {
	// 회원관리 목록 조회
	public Map<String, Object> userInfListSelect(UserInfVO userInfVO) throws Exception;

	// 회원 수정 모달 정보 조회
	public Map<String, Object> userInfUpdateModalDataSelect(UserInfVO userInfVO) throws Exception;
}
