package egovframework.carauction.service;

import java.util.List;
import java.util.Map;

import egovframework.carauction.BidInfoVO;
import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.CarSaleVO;
import egovframework.carauction.CarSearchCriteriaVO;

/**
 * 차량 경매 정보 / 차량 판매 정보 서비스 인터페이스
 */
public interface CarAucInfService {

	/************************************************************************************************************************
	 차량 경매 정보
	************************************************************************************************************************/

    List<CarInfoVO> findCarsWithConditions(CarSearchCriteriaVO criteria) throws Exception;

    /**
     * 제조사 목록 조회
     * @return 제조사 목록
     * @throws Exception
     */
    List<Map<String, Object>> findAllManufacturers() throws Exception;

    /**
     * 전체 모델 목록 조회
     * @return 모델 목록
     * @throws Exception
     */
    List<Map<String, Object>> findAllModels() throws Exception;

    /**
     * 제조사별 모델 목록 조회
     * @param manufacturer 제조사명
     * @return 모델 목록
     * @throws Exception
     */
    List<Map<String, Object>> findModelsByManufacturer(String manufacturer) throws Exception;

    /**
     * 전체 세부모델 목록 조회
     */
    List<Map<String, Object>> findAllSubModels() throws Exception;

    /**
     * 제조사 및 모델별 세부모델 목록 조회
     */
    List<Map<String, Object>> findSubModelsByManufacturerAndModel(String manufacturer, String model) throws Exception;

    /**
     * 연료타입 목록 조회
     */
    List<Map<String, Object>> findAllFuelTypes() throws Exception;

    /**
     * 등록회원사 목록 조회
     */
    List<Map<String, Object>> findAllRegistrationCompanies() throws Exception;
    
    /**
     * 차량 상세 정보 조회
     */
    CarInfoVO getCarDetail(String aucRegNo) throws Exception;

    /**
     * 은행 목록 조회
     */
    List<String> getBanks() throws Exception;

    /**
     * 계좌번호 목록 조회
     */
    List<String> getAccountNumbers(String bankName) throws Exception;

    /************************************************************************************************************************
	 차량 판매 정보
	************************************************************************************************************************/
    
    // 차량정보_제조사코드, 제조사 조회
 	public Map<String, Object> manufacturList(CarSaleVO carSaleVO) throws Exception;
 	
 	// 차량정보_모델코드, 모델명 조회
 	public Map<String, Object> modNmList(CarSaleVO carSaleVO) throws Exception;
 	
 	// 차량정보_등급코드, 등급명 조회
 	public Map<String, Object> grdNmList(CarSaleVO carSaleVO) throws Exception;
 	
 	// 차량정보_세부등급코드, 등급명 조회
 	public Map<String, Object> dtlGrdNmList(CarSaleVO carSaleVO) throws Exception;

 	// 차량정보_기타 조회
 	public Map<String, Object> getCarAucInfo(CarSaleVO carSaleVO) throws Exception;
 	
 	// 차량정보_연료코드 조회
 	public Map<String, Object> getCarEngCd(CarSaleVO carSaleVO) throws Exception;
 		
 	// 경매 판매차량 등록
 	public void insertCarSale(CarSaleDetailVO carSaleDetailVO);
 		
 	// 경매 판매차량 상세 조회
 	public Map<String, Object> getCarSaleDetail(CarSaleVO carSaleVO) throws Exception;
 	
}