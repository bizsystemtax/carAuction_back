package egovframework.penalty.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.penalty.CommonCodeVO;


public interface CommonCodeService {


	public Map<String, Object> getSelectCode(CommonCodeVO commoncodeVO) throws Exception;

	
}
