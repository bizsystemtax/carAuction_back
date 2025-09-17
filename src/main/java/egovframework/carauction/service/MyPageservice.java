package egovframework.carauction.service;

import java.util.Map;

import egovframework.carauction.myPageVO;

public interface MyPageservice {

	//마이페이지 - 내 판매차량 입찰 상세 현황
	public Map<String, Object> myPageList(myPageVO myPageVO) throws Exception;


}
