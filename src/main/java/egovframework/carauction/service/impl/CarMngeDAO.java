package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CarMngeVO;

@Repository("carMngeDAO")
public class CarMngeDAO extends EgovAbstractMapper {

	// 차량관리 검색조건(제조사) 조회
	public List<Map<String, Object>> selMkrList(@Param("locImpGb") String locImpGb) {
		return selectList("carMngeDAO.selMkrList", locImpGb);
	}

	// 차량관리 검색조건(모델, 등급명, 세부등급명) 조회
	public List<CarMngeVO> selSrchCondList(CarMngeVO carMngeVO) {
		return selectList("carMngeDAO.selSrchCondList", carMngeVO);
	}

	// 차량관리 검색조건(연료) 조회
	public List<Map<String, Object>> selFuelList() {
		return selectList("carMngeDAO.selFuelList");
	}

	// 차량관리 리스트 조회
	public List<CarMngeVO> selCarMngeList(CarMngeVO carMngeVO) {
		return selectList("carMngeDAO.selCarMngeList", carMngeVO);
	}

	// 차량관리 상세 조회
	public CarMngeVO selCarMnge(@Param("carMainNo") String carMainNo) {
		return selectOne("carMngeDAO.selCarMnge", carMainNo);
	}

	// 차량모델명 중복 조회
	public int selChkCarModelNm(CarMngeVO carMngeVO) {
		return selectOne("carMngeDAO.selChkCarModelNm", carMngeVO);
	}

	// 차량모델코드 MAX값 조회
	public String selMaxCarModelCd(CarMngeVO carMngeVO) {
		return selectOne("carMngeDAO.selMaxCarModelCd", carMngeVO);
	}

	// 차량등급명 중복 조회
	public int selChkCarLevelNm(CarMngeVO carMngeVO) {
		return selectOne("carMngeDAO.selChkCarLevelNm", carMngeVO);
	}

	// 차량등급코드 MAX값 조회
	public String selMaxCarLevelCd(CarMngeVO carMngeVO) {
		return selectOne("carMngeDAO.selMaxCarLevelCd", carMngeVO);
	}

	// 차량세부등급명 중복 조회
	public int selChkCarDtLevelNm(CarMngeVO carMngeVO) {
		return selectOne("carMngeDAO.selChkCarDtLevelNm", carMngeVO);
	}

	// 차량세부등급코드 MAX값 조회
	public String selMaxCarDtLevelCd(CarMngeVO carMngeVO) {
		return selectOne("carMngeDAO.selMaxCarDtLevelCd", carMngeVO);
	}

	// 차량관리 등록
	public int insCarMnge(CarMngeVO carMngeVO) {
		return insert("carMngeDAO.insCarMnge", carMngeVO);
	}

	// 차량관리 수정
	public int updCarMnge(CarMngeVO carMngeVO) {
		return update("carMngeDAO.updCarMnge", carMngeVO);
	}
}