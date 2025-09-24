package egovframework.carauction.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;
import egovframework.carauction.service.CarAucInfService;

/**
 * 차량 경매 정보 컨트롤러
 */
@RestController
@RequestMapping("/carAucInf")
public class CarAucInfController {

    @Resource(name = "carAucInfService")
    private CarAucInfService carAucInfService;

    /**
     * 차량 목록 조회
     */
    @GetMapping("/cars")
    public List<CarInfoVO> getCars(CarSearchCriteriaVO criteria) throws Exception {
        return carAucInfService.findCarsWithConditions(criteria);
    }

    /**
     * 제조사 목록 조회
     */
    @GetMapping("/manufacturers")
    public List<Map<String, Object>> getManufacturers() throws Exception {
        return carAucInfService.findAllManufacturers();
    }

    /**
     * 모델 목록 조회
     */
    @GetMapping("/models")
    public List<Map<String, Object>> getModels(@RequestParam(required = false) String manufacturer) throws Exception {
        if (manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer)) {
            return carAucInfService.findModelsByManufacturer(manufacturer);
        } else {
            return carAucInfService.findAllModels();
        }
    }

    /**
     * 세부모델 목록 조회
     */
    @GetMapping("/submodels")
    public List<Map<String, Object>> getSubModels(
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String model) throws Exception {
        return carAucInfService.findSubModelsByManufacturerAndModel(manufacturer, model);
    }

    /**
     * 연료타입 목록 조회
     */
    @GetMapping("/fuelType")
    public List<Map<String, Object>> getFuelTypes() throws Exception {
        return carAucInfService.findAllFuelTypes();
    }

    /**
     * 등록회원사 목록 조회
     */
    @GetMapping("/registrationCompanies")
    public List<Map<String, Object>> getRegistrationCompanies() throws Exception {
        return carAucInfService.findAllRegistrationCompanies();
    }
}