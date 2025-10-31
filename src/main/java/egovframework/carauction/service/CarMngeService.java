package egovframework.carauction.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import egovframework.carauction.CarMngeVO;

public interface CarMngeService {

	// 차량관리 검색조건(제조사) 조회
	public Map<String, Object> selMkrList(@Param("locImpGb") String locImpGb) throws Exception;

	// 차량관리 검색조건(모델, 등급명, 세부등급명) 조회
	public Map<String, Object> selSrchCondList(CarMngeVO carMngeVO) throws Exception;

	// 차량관리 검색조건(연료) 조회
	public Map<String, Object> selFuelList() throws Exception;

	// 차량관리 리스트 조회
	public Map<String, Object> getCarMngeList(CarMngeVO carMngeVO) throws Exception;

	// 차량관리 상세 조회
	public Map<String, Object> getCarMnge(@Param("carMainNo") String carMainNo) throws Exception;

	// 차량관리 등록
	public Map<String, Object> insCarMnge(CarMngeVO carMngeVO) throws Exception;

	// 차량관리 수정
	public Map<String, Object> updCarMnge(CarMngeVO carMngeVO) throws Exception;
}