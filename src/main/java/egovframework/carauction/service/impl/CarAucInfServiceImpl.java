package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;
import egovframework.carauction.service.CarAucInfService;

/**
 * 차량 경매 정보 서비스 구현 클래스 
 */
@Service("carAucInfService")
public class CarAucInfServiceImpl implements CarAucInfService {

    @Resource(name = "carAucInfDAO")
    private CarAucInfDAO carAucInfDAO;

    @Override
    public List<CarInfoVO> findCarsWithConditions(CarSearchCriteriaVO criteria) throws Exception {
        // 검색 조건 전처리
        processSearchCriteria(criteria);
        return carAucInfDAO.findCarsWithConditions(criteria);
    }

    @Override
    public List<Map<String, Object>> findAllManufacturers() throws Exception {
        return carAucInfDAO.findAllManufacturers();
    }

    @Override
    public List<Map<String, Object>> findAllModels() throws Exception {
        return carAucInfDAO.findAllModels();
    }

    @Override
    public List<Map<String, Object>> findModelsByManufacturer(String manufacturer) throws Exception {
        if (manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer)) {
            return carAucInfDAO.findModelsByManufacturer(manufacturer);
        } else {
            return carAucInfDAO.findAllModels();
        }
    }

    @Override
    public List<Map<String, Object>> findAllSubModels() throws Exception {
        return carAucInfDAO.findAllSubModels();
    }

    @Override
    public List<Map<String, Object>> findSubModelsByManufacturerAndModel(String manufacturer, String model) throws Exception {
        if (manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer) &&
            model != null && !model.trim().isEmpty() && !"전체".equals(model)) {
            return carAucInfDAO.findSubModelsByManufacturerAndModel(manufacturer, model);
        } else {
            return carAucInfDAO.findAllSubModels();
        }
    }

    @Override
    public List<Map<String, Object>> findAllFuelTypes() throws Exception {
        return carAucInfDAO.findAllFuelTypes();
    }

    @Override
    public List<Map<String, Object>> findAllRegistrationCompanies() throws Exception {
        return carAucInfDAO.findAllRegistrationCompanies();
    }

    /**
     * 검색 조건 전처리
     * @param criteria 검색 조건
     */
    private void processSearchCriteria(CarSearchCriteriaVO criteria) {
        // "전체" 값을 null로 변환
        if ("전체".equals(criteria.getManufacturer())) {
            criteria.setManufacturer(null);
        }
        if ("전체".equals(criteria.getModel())) {
            criteria.setModel(null);
        }
        if ("전체".equals(criteria.getSubModel())) {
            criteria.setSubModel(null);
        }
        if ("전체".equals(criteria.getFuelType())) {
            criteria.setFuelType(null);
        }
        if ("전체".equals(criteria.getRegistrationCompany())) {
            criteria.setRegistrationCompany(null);
        }

        // 빈 문자열을 null로 변환
        if ("".equals(criteria.getManufacturer())) {
            criteria.setManufacturer(null);
        }
        if ("".equals(criteria.getModel())) {
            criteria.setModel(null);
        }
        if ("".equals(criteria.getSubModel())) {
            criteria.setSubModel(null);
        }
        if ("".equals(criteria.getFuelType())) {
            criteria.setFuelType(null);
        }
        if ("".equals(criteria.getRegistrationCompany())) {
            criteria.setRegistrationCompany(null);
        }
    }
}