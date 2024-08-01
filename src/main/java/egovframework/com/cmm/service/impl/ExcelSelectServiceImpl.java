package egovframework.com.cmm.service.impl;

import egovframework.com.cmm.mapper.ExcelSelectDAO;
import egovframework.com.cmm.service.ExcelSelectService;
import egovframework.com.cmm.vo.ExcelDataVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ExcelSelectService")
public class ExcelSelectServiceImpl extends EgovAbstractServiceImpl implements ExcelSelectService {

    @Resource(name = "ExcelSelectDAO")
    private ExcelSelectDAO excelSelectDAO;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Map<String, Object> getExcelData(ExcelDataVO excelDataVO) throws Exception {
        validateDateRange(excelDataVO);

        List<ExcelDataVO> dataList = excelSelectDAO.selectExcelDataByDateRange(excelDataVO);
        Map<String, Object> result = new HashMap<>();
        result.put("data", dataList);
        return result;
    }

    /**
     * 날짜 범위의 유효성을 검사합니다.
     * @param excelDataVO 날짜 범위 정보를 포함하는 VO 객체
     * @throws IllegalArgumentException 날짜 범위가 유효하지 않을 경우 예외 발생
     */
    private void validateDateRange(ExcelDataVO excelDataVO) throws IllegalArgumentException {
        try {
            if (excelDataVO.getStartDate() != null && !excelDataVO.getStartDate().isEmpty()) {
                DATE_FORMAT.parse(excelDataVO.getStartDate());
            }
            if (excelDataVO.getEndDate() != null && !excelDataVO.getEndDate().isEmpty()) {
                DATE_FORMAT.parse(excelDataVO.getEndDate());
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다. 형식: yyyy-MM-dd", e);
        }
    }
}
