package egovframework.carauction.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.MyPageVO;

@Repository("MyPageDAO")
public class UserLoginDAO extends EgovAbstractMapper {
	
	//콤보박스 진행상태 조회
	public List<MyPageVO> comboBoxList(MyPageVO myPageVO) {
		return selectList("MyPageDAO.comboBoxList", myPageVO);
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	public List<MyPageVO> myPageList(MyPageVO myPageVO) {
		return selectList("MyPageDAO.myPageList", myPageVO);
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용)
	public List<MyPageVO> mySaleCarAuctionRegList(MyPageVO myPageVO) {
		return selectList("MyPageDAO.mySaleCarAuctionRegList", myPageVO);
	}
}
