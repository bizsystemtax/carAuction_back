package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.CarSaleVO;
import egovframework.carauction.CarSearchCriteriaVO;

@Repository("carAucInfDAO")
public class CarAucInfDAO extends EgovAbstractMapper {

	/************************************************************************************************************************
	 * 차량 경매 정보
	 ************************************************************************************************************************/

	public List<CarInfoVO> findCarsWithConditions(CarSearchCriteriaVO criteria) {
		return selectList("carAucInfDAO.findCarsWithConditions", criteria);
	}

	public List<Map<String, Object>> findAllManufacturers() {
		return selectList("carAucInfDAO.findAllManufacturers");
	}

	public List<Map<String, Object>> findAllModels() {
		return selectList("carAucInfDAO.findAllModels");
	}

	public List<Map<String, Object>> findModelsByManufacturer(String manufacturer) {
		return selectList("carAucInfDAO.findModelsByManufacturer", manufacturer);
	}

	public List<Map<String, Object>> findAllSubModels() {
		return selectList("carAucInfDAO.findAllSubModels");
	}

	public List<Map<String, Object>> findSubModelsByManufacturerAndModel(CarSearchCriteriaVO criteria) {
		return selectList("carAucInfDAO.findSubModelsByManufacturerAndModel", criteria);
	}

	public List<Map<String, Object>> findAllFuelTypes() {
		return selectList("carAucInfDAO.findAllFuelTypes");
	}

	public List<Map<String, Object>> findAllRegistrationCompanies() {
		return selectList("carAucInfDAO.findAllRegistrationCompanies");
	}

	public List<String> findAllBanks() {
		return selectList("carAucInfDAO.findAllBanks");
	}

	public List<String> findAccountsByBank(String bankName) {
		return selectList("carAucInfDAO.findAccountsByBank", bankName);
	}

	public CarInfoVO findCarByAucRegNo(String aucRegNo) {
		return selectOne("carAucInfDAO.findCarByAucRegNo", aucRegNo);
	}

	public int checkUserBidHistory(Map<String, String> params) {
	    return selectOne("carAucInfDAO.checkUserBidHistory", params);
	}
	
	public String getNextAucRegSeq(String aucRegNo) {
		return selectOne("carAucInfDAO.getNextAucRegSeq", aucRegNo);
	}

	public int insertBid(Map<String, Object> bidData) {
		return insert("carAucInfDAO.insertBid", bidData);
	}
	
	public int incrementBidCount(Map<String, Object> bidData) {
	    return update("carAucInfDAO.incrementBidCount", bidData);
	}

	/************************************************************************************************************************
	 * 차량 판매 정보
	 ************************************************************************************************************************/

	// 차량정보_제조사코드, 제조사 조회
	public List<CarSaleVO> manufacturList(CarSaleVO carSaleVO) {
		return selectList("carAucInfDAO.manufacturList", carSaleVO);
	}

	// 차량정보_모델코드, 모델명 조회
	public List<CarSaleVO> modNmList(CarSaleVO carSaleVO) {
		return selectList("carAucInfDAO.modNmList", carSaleVO);
	}

	// 차량정보_등급코드, 등급명 조회
	public List<CarSaleVO> grdNmList(CarSaleVO carSaleVO) {
		return selectList("carAucInfDAO.grdNmList", carSaleVO);
	}

	// 차량정보_세부등급코드, 등급명 조회
	public List<CarSaleVO> dtlGrdNmList(CarSaleVO carSaleVO) {
		return selectList("carAucInfDAO.dtlGrdNmList", carSaleVO);
	}

	// 차량정보_기타 조회
	public CarSaleVO getCarAucInfo(CarSaleVO carSaleVO) {
		return selectOne("carAucInfDAO.getCarAucInfo", carSaleVO);
	}

	// 차량정보_연료코드 조회
	public List<CarSaleVO> getCarEngCd(CarSaleVO carSaleVO) {
		return selectList("carAucInfDAO.getCarEngCd", carSaleVO);
	}

	// 오늘 날짜 기준으로 다음 ID 조회
	public String getNextId() {
		return selectOne("carAucInfDAO.getNextId");
	}

	// 경매 판매차량 등록
//	public void insertCarSale(CarSaleDetailVO carSaleDetailVO) {
//		insert("carAucInfDAO.insertCarSale", carSaleDetailVO);
//	}
	
	public int insertCarSale(CarSaleDetailVO carSaleDetailVO) {
		// TODO Auto-generated method stub
		return insert("carAucInfDAO.insertCarSale", carSaleDetailVO);
	}

	// 경매 판매차량 상세 조회
	public CarSaleDetailVO getCarSaleDetail(CarSaleVO carSaleVO) {
		return selectOne("carAucInfDAO.getCarSaleDetail", carSaleVO);
	}

	// 경매 판매차량 이미지 상세 조회
	public List<CarSaleDetailVO> getCarSaleImgDetail(CarSaleVO carSaleVO) {
		return selectList("carAucInfDAO.getCarSaleImgDetail", carSaleVO);
	}
	
	// 경매 판매차량 수정
	public void updateCarSale(CarSaleDetailVO carSaleDetailVO) {
		insert("carAucInfDAO.updateCarSale", carSaleDetailVO);
	}
	
	// 경매 판매차량 삭제
	public void deleteCarSale(CarSaleDetailVO carSaleDetailVO) {
		insert("carAucInfDAO.deleteCarSale", carSaleDetailVO);
	}

	

}