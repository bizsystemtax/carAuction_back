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
	private MyPageDAO myPageDAO;
	
	//콤보박스 진행상태 조회
	@Override
	public Map<String, Object> comboBoxList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.comboBoxList(myPageVO));
		
		return map;
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황
	@Override
	public Map<String, Object> myPageList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.myPageList(myPageVO));
		
		return map;
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용)
	@Override
	public Map<String, Object> mySaleCarAuctionRegList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.mySaleCarAuctionRegList(myPageVO));
		
		return map;
	}

	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용) - 유찰(update)
	@Override
	public Map<String, Object> faileBidUpdate(MyPageVO myPageVO) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		int cnt = myPageDAO.faileBidUpdate(myPageVO);
		
		if(cnt > 0) {
			myPageDAO.failecaCarAucInfUpdate(myPageVO);  
		}

		return map;
		
		
	}
	//마이페이지 - 내 판매차량 입찰 상세 현황(입찰현황)
	@Override
	public Map<String, Object> mySaleCarBidList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.mySaleCarBidList(myPageVO));
		
		return map;
	}

	//내 판매차량 입찰 상세 현황 - 콤보박스 진행상태 조회
	@Override
	public Map<String, Object> statusComboBoxList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.statusComboBoxList(myPageVO));
		
		return map;
	}


}
