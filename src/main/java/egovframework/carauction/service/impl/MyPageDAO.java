package egovframework.carauction.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.carauction.myPageVO;
import egovframework.penalty.FineMngeVO;

@Repository("MyPageDAO")
public class MyPageDAO extends EgovAbstractMapper {
	
	//마이페이지 - 내 판매차량 입찰 상세 현황
	public List<FineMngeVO> myPageList(myPageVO myPageVO) {
		return selectList("MyPageDAO.myPageList", myPageVO);
	}
}
