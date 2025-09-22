package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.carauction.MyPageVO;
import egovframework.carauction.service.MyPageservice;


@Service("MyPageservice")
public class MySaleCarBidInfServiceImpl extends EgovAbstractServiceImpl implements MyPageservice {
	@Resource(name = "MyPageDAO")
	private MyPageDAO MyPageDAO;
	
	//콤보박스 진행상태 조회
	@Override
	public Map<String, Object> comboBoxList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", MyPageDAO.comboBoxList(myPageVO));
		
		return map;
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황
	@Override
	public Map<String, Object> myPageList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", MyPageDAO.myPageList(myPageVO));
		
		return map;
	}

}
