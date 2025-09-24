package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;

@Repository("carAucInfDAO")
public class CarAucInfDAO extends EgovAbstractMapper {

    public List<CarInfoVO> findCarsWithConditions(CarSearchCriteriaVO criteria) {
        return selectList("CarAucInfDAO.findCarsWithConditions", criteria);
    }

    public List<Map<String, Object>> findAllManufacturers() {
        return selectList("CarAucInfDAO.findAllManufacturers");
    }

    public List<Map<String, Object>> findAllModels() {
        return selectList("CarAucInfDAO.findAllModels");
    }

    public List<Map<String, Object>> findModelsByManufacturer(String manufacturer) {
        return selectList("CarAucInfDAO.findModelsByManufacturer", manufacturer);
    }

    public List<Map<String, Object>> findAllSubModels() {
        return selectList("CarAucInfDAO.findAllSubModels");
    }

    public List<Map<String, Object>> findSubModelsByManufacturerAndModel(CarSearchCriteriaVO criteria) {
        return selectList("CarAucInfDAO.findSubModelsByManufacturerAndModel", criteria);
    }

    public List<Map<String, Object>> findAllFuelTypes() {
        return selectList("CarAucInfDAO.findAllFuelTypes");
    }

    public List<Map<String, Object>> findAllRegistrationCompanies() {
        return selectList("CarAucInfDAO.findAllRegistrationCompanies");
    }
}