package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractDAO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;

@Repository("carAucInfDAO")
public class CarAucInfDAO extends EgovAbstractMapper {

	/************************************************************************************************************************
	 차량 경매 정보
	************************************************************************************************************************/
	
    @SuppressWarnings("unchecked")
    public List<CarInfoVO> findCarsWithConditions(CarSearchCriteriaVO criteria) throws Exception {
        return (List<CarInfoVO>) list("CarAucInfDAO.findCarsWithConditions", criteria);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllManufacturers() throws Exception {
        return (List<Map<String, Object>>) list("CarAucInfDAO.findAllManufacturers", null);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllModels() throws Exception {
        return (List<Map<String, Object>>) list("CarAucInfDAO.findAllModels", null);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findModelsByManufacturer(String manufacturer) throws Exception {
        return (List<Map<String, Object>>) list("CarAucInfDAO.findModelsByManufacturer", manufacturer);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllSubModels() throws Exception {
        return (List<Map<String, Object>>) list("CarAucInfDAO.findAllSubModels", null);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findSubModelsByManufacturerAndModel(String manufacturer, String model) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("manufacturer", manufacturer);
        paramMap.put("model", model);
        return (List<Map<String, Object>>) list("CarAucInfDAO.findSubModelsByManufacturerAndModel", paramMap);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllFuelTypes() throws Exception {
        return (List<Map<String, Object>>) list("CarAucInfDAO.findAllFuelTypes", null);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllRegistrationCompanies() throws Exception {
        return (List<Map<String, Object>>) list("CarAucInfDAO.findAllRegistrationCompanies", null);
    }
    
    /************************************************************************************************************************
	 차량 판매 정보
	************************************************************************************************************************/
    
//    public List<CarSaleComboVO> manufacturList(CarSaleVO carSaleVO) {
//		return selectList("CarSaleDAO.manufacturList",carSaleVO);
//	}
	
}