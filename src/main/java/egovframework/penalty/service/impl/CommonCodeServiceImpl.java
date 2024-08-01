package egovframework.penalty.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.penalty.CommonCodeVO;
import egovframework.penalty.service.CommonCodeService;
          
@Service("CommonCodeService")
public class CommonCodeServiceImpl extends EgovAbstractServiceImpl implements CommonCodeService {

	@Resource(name = "CommonCodeDAO")
	private CommonCodeDAO cmmCodeDAO;
	
//	@Resource(name = "CommonCodeService")
//	private  CommonCodeService commService;

	@Override
	public Map<String, Object> getSelectCode(CommonCodeVO commoncodeVO) throws Exception {
		
		List<CommonCodeVO> list = cmmCodeDAO.getSelectCode(commoncodeVO);
		//List<CommonCodeVO> result = new ArrayList<CommonCodeVO>();
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);
		
		return map;
	}

}