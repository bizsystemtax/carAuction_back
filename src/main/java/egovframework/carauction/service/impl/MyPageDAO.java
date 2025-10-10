package egovframework.carauction.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.MyPageVO;

@Repository("myPageDAO")
public class MyPageDAO extends EgovAbstractMapper {
	
	//콤보박스 진행상태 조회
	public List<MyPageVO> comboBoxList(MyPageVO myPageVO) throws Exception {
		return selectList("myPageDAO.comboBoxList", myPageVO);
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	public List<MyPageVO> myPageList(MyPageVO myPageVO) throws Exception {
		return selectList("myPageDAO.myPageList", myPageVO);
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용)
	public List<MyPageVO> mySaleCarAuctionRegList(MyPageVO myPageVO) throws Exception {
		return selectList("myPageDAO.mySaleCarAuctionRegList", myPageVO);
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용) - 유찰(update)
	public int faileBidUpdate(MyPageVO myPageVO) throws Exception {
		return update("myPageDAO.faileBidUpdate", myPageVO);
	}

	public void failecaCarAucInfUpdate(MyPageVO myPageVO) throws Exception  {
		update("myPageDAO.failecaCarAucInfUpdate", myPageVO);
		
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황(입찰현황)
	public List<MyPageVO> mySaleCarBidList(MyPageVO myPageVO) throws Exception {
		return selectList("myPageDAO.mySaleCarBidList", myPageVO);
	}

	//내 판매차량 입찰 상세 현황 - 콤보박스 진행상태 조회
	public List<MyPageVO> statusComboBoxList(MyPageVO myPageVO) throws Exception {
		return selectList("myPageDAO.statusComboBoxList", myPageVO);
	}

	//내 입찰 현황
	public List<MyPageVO> myBidList(MyPageVO myPageVO) throws Exception {
		return selectList("myPageDAO.myBidList", myPageVO);
	}

	//내 판매차량 입찰 상세 현황 - 저장
	public int caCarAucDtlBidInfUpdate(MyPageVO myPageVO) throws Exception {
		return update("myPageDAO.caCarAucDtlBidInfUpdate", myPageVO);
	}

	public void caCarAucInfUpdate(MyPageVO myPageVO) throws Exception {
		update("myPageDAO.caCarAucInfUpdate", myPageVO);
	}

	public void myBidInfoUnSelectedUpdate(MyPageVO myPageVO) {
		update("myPageDAO.myBidInfoUnSelectedUpdate", myPageVO);
		
	}
}
