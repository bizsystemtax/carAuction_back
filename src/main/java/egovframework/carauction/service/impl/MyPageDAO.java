package egovframework.carauction.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.myPageVO;

@Repository("MyPageDAO")
public class MyPageDAO extends EgovAbstractMapper {
	
	//콤보박스 진행상태 조회
	public List<myPageVO> comboBoxList(myPageVO myPageVO) {
		return selectList("MyPageDAO.comboBoxList", myPageVO);
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	public List<myPageVO> myPageList(myPageVO myPageVO) {
		return selectList("MyPageDAO.myPageList", myPageVO);
	}

}
