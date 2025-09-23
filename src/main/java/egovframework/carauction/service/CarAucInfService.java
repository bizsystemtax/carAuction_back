package egovframework.carauction.service;

import java.util.List;
import java.util.Map;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;

/**
 * 차량 경매 정보 서비스 인터페이스
 */
public interface CarAucInfService {

    /**
     * 검색 조건에 따른 차량 목록 조회
     * @param criteria 검색 조건
     * @return 차량 목록
     * @throws Exception
     */
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
     * @return 세부모델 목록
     * @throws Exception
     */
    List<Map<String, Object>> findAllSubModels() throws Exception;

    /**
     * 제조사 및 모델별 세부모델 목록 조회
     * @param manufacturer 제조사명
     * @param model 모델명
     * @return 세부모델 목록
     * @throws Exception
     */
    List<Map<String, Object>> findSubModelsByManufacturerAndModel(String manufacturer, String model) throws Exception;

    /**
     * 연료타입 목록 조회
     * @return 연료타입 목록
     * @throws Exception
     */
    List<Map<String, Object>> findAllFuelTypes() throws Exception;

    /**
     * 등록회원사 목록 조회
     * @return 등록회원사 목록
     * @throws Exception
     */
    List<Map<String, Object>> findAllRegistrationCompanies() throws Exception;
}