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
		return selectList("FineMngeDAO.retrieveComboBoxList", comnCdVO);
	}
	
	//범칙금관리 목록 조회
	public List<FineMngeVO> retrieveFineMnge(FineMngeVO fineMngeVO) {
		//범칙금관리 목록 조회 XML 호출
		return selectList("FineMngeDAO.retrieveFineMnge", fineMngeVO);
	}

	//범칙금관리 중복 조회
	public List<FineMngeVO> retrieveFineDupeCheck(FineMngeVO fineMngeVO) {
		//범칙금관리 목록 조회 XML 호출
		return selectList("FineMngeDAO.retrieveFineDupeCheck", fineMngeVO);
	}

	//범칙금관리 확정 상태 업데이트
	public int updateCfmtStat(FineMngeVO fineMngeVO) {
		//범칙금관리 확정 상태 업데이트 XML 호출
		return update("FineMngeDAO.updateCfmtStat", fineMngeVO);
	}

	//발송처부서명 목록 조회
	public List<FineMngeVO> retrieveSendPlcDeptList(FineMngeVO fineMngeVO) {
		//발송처부서명 목록 조회 XML 호출
		return selectList("FineMngeDAO.retrieveSendPlcDeptList", fineMngeVO);
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
		return selectList("FineMngeDAO.retrieveVhclNoLoanMas", fineMngeVO);
	}

	//범칙금일련번호 채번
	public List<FineMngeVO> retrieveFineSeqSN(FineMngeVO fineMngeVO) {
		//차량번호로 대출정보 조회 XML 호출
		return selectList("FineMngeDAO.retrieveFineSeqSN", fineMngeVO);
	}

	//위반종류코드 조회
	public List<FineMngeVO> retrieveVltKindCd(FineMngeVO fineMngeVO) {
		//위반종류코드 조회 XML 호출
		return selectList("FineMngeDAO.retrieveVltKindCd", fineMngeVO);
	}

	//발송처코드 조회
	public List<FineMngeVO> retrieveSendPlcCd(FineMngeVO fineMngeVO) {
		//발송처코드 조회 XML 호출
		return selectList("FineMngeDAO.retrieveSendPlcCd", fineMngeVO);
	}
	
	//다운로드(이파인) 조회
	public List<FineMngeVO> downloadEfine(FineMngeVO fineMngeVO) {
		//다운로드(이파인) 조회 XML 호출
		return selectList("FineMngeDAO.retrieveDownloadEfine", fineMngeVO);
	}

	//다운로드(한국도로공사) 조회
	public List<FineMngeVO> downloadEx(FineMngeVO fineMngeVO) {
		//다운로드(한국도로공사) 조회 XML 호출
		return selectList("FineMngeDAO.retrieveDownloadEx", fineMngeVO);
	}
	
	//다운로드(OCR) 조회
	public List<FineMngeVO> downloadOCR(FineMngeVO fineMngeVO) {
		//다운로드(OCR) 조회 XML 호출
		return selectList("FineMngeDAO.retrieveDownloadOCR", fineMngeVO);
	}
	
	//다운로드(카택스) 조회
	public List<FineMngeVO> downloadCartax(FineMngeVO fineMngeVO) {
		//다운로드(카택스) 조회 XML 호출
		return selectList("FineMngeDAO.retrieveDownloadCartax", fineMngeVO);
	}

	//다운로드(PDF) 조회
	public List<FineMngeVO> downloadPdf(FineMngeVO fineMngeVO) {
		//다운로드(카택스) 조회 XML 호출
		return selectList("FineMngeDAO.retrieveDownloadPdf", fineMngeVO);
	}

	//PDF 공문 문서번호 채번
	public List<FineMngeVO> retrieveNtcdocDocNo(FineMngeVO fineMngeVO) {
		//다운로드(카택스) 조회 XML 호출
		return selectList("FineMngeDAO.retrieveNtcdocDocNo", fineMngeVO);
	}
	
	//PDF 공문 문서번호 업데이트
	public int updateNtcdocDocNo(FineMngeVO fineMngeVO) {
		//PDF 공문 문서번호 업데이트 XML 호출
		return update("FineMngeDAO.updateNtcdocDocNo", fineMngeVO);
	}
}
