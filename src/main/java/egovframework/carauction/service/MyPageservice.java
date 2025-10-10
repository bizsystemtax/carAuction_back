package egovframework.carauction.service;

import java.util.Map;



import egovframework.carauction.MyPageVO;

public interface MyPageservice {
	
	//콤보박스 진행상태 조회
	public Map<String, Object> comboBoxList(MyPageVO myPageVO) throws Exception;
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	public Map<String, Object> myPageList(MyPageVO myPageVO) throws Exception;

	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용)	
	public Map<String, Object> mySaleCarAuctionRegList(MyPageVO myPageVO) throws Exception;

	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용) >>>> 유찰
	public Map<String, Object> faileBidUpdate(MyPageVO myPageVO) throws Exception;

	//마이페이지 - 내 판매차량 입찰 상세 현황(입찰현황)
	public Map<String, Object> mySaleCarBidList(MyPageVO myPageVO) throws Exception;

	//내 판매차량 입찰 상세 현황 - 콤보박스 진행상태 조회
	public Map<String, Object> statusComboBoxList(MyPageVO myPageVO) throws Exception;

	//내 입찰 현황
	public Map<String, Object> myBidList(MyPageVO myPageVO) throws Exception;

	//내 판매차량 입찰 상세 현황 - 저장(선택한 값)
	public void myBidInfoSelectedUpdate(MyPageVO myPageVO) throws Exception;

	//내 판매차량 입찰 상세 현황 - 저장(선택되지 않은 값)
	public void myBidInfoUnSelectedUpdate(MyPageVO myPageVO) throws Exception;
}
