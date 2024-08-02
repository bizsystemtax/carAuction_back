package egovframework.com.cmm.mapper;

import egovframework.com.cmm.vo.ExcelDataVO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("ExcelSelectDAO")
public class ExcelSelectDAO extends EgovAbstractMapper {
    @SuppressWarnings("unchecked")    
    public List<ExcelDataVO> selectExcelDataByDateRange(ExcelDataVO excelDataVO) throws Exception {
        return (List<ExcelDataVO>) list("ExcelSelectDAO.selectExcelDataByDateRange", excelDataVO);
    }
}
