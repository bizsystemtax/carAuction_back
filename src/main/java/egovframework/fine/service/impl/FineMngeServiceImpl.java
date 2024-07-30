package egovframework.fine.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.fine.FineMngeVO;
import egovframework.fine.service.FineMngeService;
          
@Service("FineMngeService")
public class FineMngeServiceImpl extends EgovAbstractServiceImpl implements FineMngeService {

	@Resource(name = "FineMngeDAO")
	private FineMngeDAO FineMngeDAO;
	
	@Override
	public Map<String, Object> getRetrieveFineMnge(FineMngeVO finemngeVO) throws Exception {
		
		List<FineMngeVO> list = FineMngeDAO.getRetrieveFineMnge(finemngeVO);
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);
		
		return map;
	}

}