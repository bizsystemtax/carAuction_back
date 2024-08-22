package egovframework.penalty.service.impl;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;

@Repository("FineMngeDAO")
public class FineMngeDAO extends EgovAbstractMapper {
	//범칙금관리 검색조건 콤보박스 조회
	public List<ComnCdVO> retrieveComboBoxList(ComnCdVO comnCdVO) {
		// TODO Auto-generated method stub
		return (List<ComnCdVO>) list("FineMngeDAO.retrieveComboBoxList", comnCdVO);
	}
	
	//범칙금관리 목록 조회
	public List<FineMngeVO> retrieveFineMnge(FineMngeVO fineMngeVO) {
		//범칙금관리 목록 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveFineMnge", fineMngeVO);
	}

	//범칙금관리 중복 조회
	public List<FineMngeVO> retrieveFineDupeCheck(FineMngeVO fineMngeVO) {
		//범칙금관리 목록 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveFineDupeCheck", fineMngeVO);
	}

	//범칙금관리 확정 상태 업데이트
	public int updateCfmtStat(FineMngeVO fineMngeVO) {
		//범칙금관리 확정 상태 업데이트 XML 호출
		return update("FineMngeDAO.updateCfmtStat", fineMngeVO);
	}

	//발송처부서명 목록 조회
	public List<FineMngeVO> retrieveSendPlcDeptList(FineMngeVO fineMngeVO) {
		//발송처부서명 목록 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveSendPlcDeptList", fineMngeVO);
	}

	//범칙금관리 등록
	public int insertFine(FineMngeVO fineMngeVO) {
		//범칙금관리 수정 XML 호출
		return insert("FineMngeDAO.insertFine", fineMngeVO);
	}

	//범칙금관리 수정
	public int updateFine(FineMngeVO fineMngeVO) {
		//범칙금관리 수정 XML 호출
		return update("FineMngeDAO.updateFine", fineMngeVO);
	}

	//범칙금관리 삭제
	public int deleteFine(FineMngeVO fineMngeVO) {
		//범칙금관리 삭제 XML 호출
		return delete("FineMngeDAO.deleteFine", fineMngeVO);
	}

	//차량번호로 대출정보 조회
	public List<FineMngeVO> retrieveVhclNoLoanMas(FineMngeVO fineMngeVO) {
		//차량번호로 대출정보 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveVhclNoLoanMas", fineMngeVO);
	}

	//범칙금일련번호 채번
	public List<FineMngeVO> retrieveFineSeqSN(FineMngeVO fineMngeVO) {
		//차량번호로 대출정보 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveFineSeqSN", fineMngeVO);
	}

	//위반종류코드 조회
	public List<FineMngeVO> retrieveVltKindCd(FineMngeVO fineMngeVO) {
		//위반종류코드 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveVltKindCd", fineMngeVO);
	}

	//발송처코드 조회
	public List<FineMngeVO> retrieveSendPlcCd(FineMngeVO fineMngeVO) {
		//발송처코드 조회 XML 호출
		return (List<FineMngeVO>) list("FineMngeDAO.retrieveSendPlcCd", fineMngeVO);
	}
}
