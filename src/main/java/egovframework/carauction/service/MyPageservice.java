package egovframework.carauction.service;

import java.util.Map;

import egovframework.carauction.MyPageVO;

public interface MyPageservice {

	//콤보박스 진행상태 조회
	public Map<String, Object> comboBoxList(MyPageVO myPageVO) throws Exception;
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	public Map<String, Object> myPageList(MyPageVO myPageVO) throws Exception;
}
