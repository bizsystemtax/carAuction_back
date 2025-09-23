package egovframework.carauction.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.carauction.service.UserLoginService;

/**
 * 차량 경매 정보 컨트롤러
 */
@Controller
@RequestMapping("/api")
public class UserLoginController {

    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;



    /**
     * 제조사 목록 조회
     * @return 제조사 목록
     * @throws Exception
     */
//    @RequestMapping(value = "/manufacturers", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Map<String, Object>> getManufacturers() throws Exception {
//        return carAucInfService.findAllManufacturers();
//    }


}