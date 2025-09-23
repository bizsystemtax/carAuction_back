package egovframework.carauction.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSearchCriteriaVO;
import egovframework.carauction.service.CarAucInfService;

/**
 * 차량 경매 정보 컨트롤러
 */
@Controller
@RequestMapping("/api")
public class CarAucInfController {

    @Resource(name = "carAucInfService")
    private CarAucInfService carAucInfService;

    /**
     * 차량 목록 조회
     * @param criteria 검색 조건
     * @return 차량 목록
     * @throws Exception
     */
    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    @ResponseBody
    public List<CarInfoVO> getCars(CarSearchCriteriaVO criteria) throws Exception {
        return carAucInfService.findCarsWithConditions(criteria);
    }

    /**
     * 제조사 목록 조회
     * @return 제조사 목록
     * @throws Exception
     */
    @RequestMapping(value = "/manufacturers", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getManufacturers() throws Exception {
        return carAucInfService.findAllManufacturers();
    }

    /**
     * 모델 목록 조회
     * @param manufacturer 제조사명
     * @return 모델 목록
     * @throws Exception
     */
    @RequestMapping(value = "/models", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getModels(@RequestParam(required = false) String manufacturer) throws Exception {
        if (manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer)) {
            return carAucInfService.findModelsByManufacturer(manufacturer);
        } else {
            return carAucInfService.findAllModels();
        }
    }

    /**
     * 세부모델 목록 조회
     * @param manufacturer 제조사명
     * @param model 모델명
     * @return 세부모델 목록
     * @throws Exception
     */
    @RequestMapping(value = "/submodels", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getSubModels(@RequestParam(required = false) String manufacturer,
                                                   @RequestParam(required = false) String model) throws Exception {
        return carAucInfService.findSubModelsByManufacturerAndModel(manufacturer, model);
    }

    /**
     * 연료타입 목록 조회
     * @return 연료타입 목록
     * @throws Exception
     */
    @RequestMapping(value = "/fuelType", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getFuelTypes() throws Exception {
        return carAucInfService.findAllFuelTypes();
    }

    /**
     * 등록회원사 목록 조회
     * @return 등록회원사 목록
     * @throws Exception
     */
    @RequestMapping(value = "/registrationCompanies", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getRegistrationCompanies() throws Exception {
        return carAucInfService.findAllRegistrationCompanies();
    }
}