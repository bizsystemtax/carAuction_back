package egovframework.carauction.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.BidInfoVO;
import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;

@Repository("carAucInfDAO")
public class CarAucInfDAO extends EgovAbstractMapper {

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

}