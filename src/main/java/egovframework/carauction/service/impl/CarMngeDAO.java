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
	
	
	
	
	// 차량관리 등록
	public int inscarMnge(Map<String, Object> paramMap) {
		return insert("carMngeDAO.inscarMnge", paramMap);
	}
	
	// 차량관리 수정
	public int updcarMnge(Map<String, Object> paramMap) {
		return update("carMngeDAO.updcarMnge", paramMap);
	}

	// 차량관리 삭제
	public int delcarMnge(CarMngeVO CarMngeVO) {
		return update("carMngeDAO.delcarMnge", CarMngeVO);
	}
}