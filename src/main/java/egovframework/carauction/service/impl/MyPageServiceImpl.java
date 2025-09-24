package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.carauction.MyPageVO;
import egovframework.carauction.service.MyPageservice;


@Service("myPageservice")
public class MyPageServiceImpl extends EgovAbstractServiceImpl implements MyPageservice {
	@Resource(name = "myPageDAO")
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
	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용)
	@Override
	public Map<String, Object> mySaleCarAuctionRegList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", MyPageDAO.mySaleCarAuctionRegList(myPageVO));
		
		return map;
	}


}
