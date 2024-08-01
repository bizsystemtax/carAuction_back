package egovframework.com.cmm.service;

import egovframework.com.cmm.vo.ExcelDataVO;

import java.util.Map;

public interface ExcelSelectService {
  public Map<String, Object> getExcelData(ExcelDataVO excelDataVO) throws Exception;
}
