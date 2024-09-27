package egovframework.penalty.service;

import java.util.List;
import java.util.Map;

import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;


public interface FineMngeService {
	//범칙금관리 검색조건 콤보박스 조회
	public Map<String, Object> retrieveComboBoxList(ComnCdVO comnCdVO) throws Exception;

	//범칙금관리 목록 조회
	public Map<String, Object> retrieveFineMnge(FineMngeVO fineMngeVO) throws Exception;

	//범칙금관리 확정 상태 업데이트
	public int updateCfmtStat(FineMngeVO fineMngeVO) throws Exception;

	//발송처부서명 목록 조회
	public Map<String, Object> retrieveSendPlcDeptList(FineMngeVO fineMngeVO) throws Exception;

	//범칙금 상태 유효성 검사
	public void checkFineStat(FineMngeVO fineMngeVO, String errKey) throws Exception;
	
	//범칙금관리 등록
	public int insertFine(FineMngeVO fineMngeVO, String errKey) throws Exception;

	//범칙금관리 수정
	public int updateFine(FineMngeVO fineMngeVO) throws Exception;

	//범칙금관리 삭제
	public int deleteFine(FineMngeVO fineMngeVO) throws Exception;

	//차량번호로 대충정보 조회
	public Map<String, Object> retrieveVhclNoLoanMas(FineMngeVO fineMngeVO) throws Exception;
	
	//차량번호로 대출정보 유효성 검사
	public void checkVhclNoLoanInf(FineMngeVO fineMngeVO, String errKey) throws Exception;

	//위반종류코드 매핑
	public String retrieveVltKindCd(FineMngeVO fineMngeVO) throws Exception;
	
	//발송처코드 조회
	public List<FineMngeVO> retrieveSendPlcCd(FineMngeVO fineMngeVO) throws Exception;

	//다운로드(이파인) 조회
	public List<FineMngeVO> downloadEfine(FineMngeVO fineMngeVO) throws Exception;

	//다운로드(한국도로공사) 조회
	public List<FineMngeVO> downloadEx(FineMngeVO fineMngeVO) throws Exception;
	
	//다운로드(OCR) 조회
	public List<FineMngeVO> downloadOCR(FineMngeVO fineMngeVO) throws Exception;
	
	//다운로드(카택스) 조회
	public List<FineMngeVO> downloadCartax(FineMngeVO fineMngeVO) throws Exception;

	//다운로드(PDF) 조회
	public List<FineMngeVO> downloadPdf(FineMngeVO fineMngeVO) throws Exception;
	
	//PDF 공문 문서번호 채번
	public String retrieveNtcdocDocNo(FineMngeVO fineMngeVO) throws Exception;
	
	//PDF 공문 문서번호 업데이트
	public int updateNtcdocDocNo(FineMngeVO fineMngeVO) throws Exception;
}
