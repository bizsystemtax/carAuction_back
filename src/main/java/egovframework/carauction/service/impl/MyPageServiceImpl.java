package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.carauction.MyPageVO;
import egovframework.carauction.service.MyPageservice;


@Service("myPageservice")
public class MyPageServiceImpl extends EgovAbstractServiceImpl implements MyPageservice {
	
	private static final Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);
	
	@Resource(name = "myPageDAO")
	private MyPageDAO myPageDAO;
	
	//콤보박스 진행상태 조회
	@Override
	public Map<String, Object> comboBoxList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.comboBoxList(myPageVO));
		
		logger.info("map ▶▶▶▶▶▶ {}", map);
		
		return map;
	}

	//마이페이지 - 내 판매차량 입찰 상세 현황
	@Override
	public Map<String, Object> myPageList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.myPageList(myPageVO));
		
		logger.info("map ▶▶▶▶▶▶ {}", map);
		
		return map;
	}
	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용)
	@Override
	public Map<String, Object> mySaleCarAuctionRegList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.mySaleCarAuctionRegList(myPageVO));
		
		logger.info("map ▶▶▶▶▶▶ {}", map);
		
		return map;
	}

	
	//마이페이지 - 내 판매차량 입찰 상세 현황(경매(공매) 등록내용) - 유찰(update)
	@Override
	public Map<String, Object> faileBidUpdate(MyPageVO myPageVO) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//경매등록순번 확인
		int regSeqCnt = myPageDAO.selectRegSeq(myPageVO);
		
		if(regSeqCnt > 0) {
			logger.info("regSeqCnt ▶▶▶▶▶▶ {}", regSeqCnt);
			int cnt = myPageDAO.faileBidUpdate(myPageVO);
			
			logger.info("cnt ▶▶▶▶▶▶ {}", cnt);
			
			if(cnt > 0) {
				myPageDAO.failecaCarAucInfUpdate(myPageVO);  
			}
			
			logger.info("map ▶▶▶▶▶▶ {}", map);
			
			map.put("result", "success"); // 성공 시
		}else {
			map.put("result", "fail");
	        map.put("message", "해당 경매 등록 정보가 존재하지 않습니다.");
		}
	
		//int cnt = myPageDAO.faileBidUpdate(myPageVO);
		
		
		
		return map;
		
		
	}
	//마이페이지 - 내 판매차량 입찰 상세 현황(입찰현황)
	@Override
	public Map<String, Object> mySaleCarBidList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.mySaleCarBidList(myPageVO));

		logger.info("map ▶▶▶▶▶▶ {}", map);
		
		return map;
	}

	//내 판매차량 입찰 상세 현황 - 콤보박스 진행상태 조회
	@Override
	public Map<String, Object> statusComboBoxList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.statusComboBoxList(myPageVO));

		logger.info("map ▶▶▶▶▶▶ {}", map);
		
		return map;
	}

	//내 입찰 현황
	@Override
	public Map<String, Object> myBidList(MyPageVO myPageVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", myPageDAO.myBidList(myPageVO));

		logger.info("map ▶▶▶▶▶▶ {}", map);
		
		return map;
	}

	//내 판매차량 입찰 상세 현황 - 저장(선택한 값)
	@Override
	public void myBidInfoSelectedUpdate(MyPageVO myPageVO) throws Exception {
		
		String aucProgStatCd = myPageVO.getAucProgStatCd();
		String aucProgYn;		//낙찰여부
		
		if("0020".equals(aucProgStatCd) || "0030".equals(aucProgStatCd) || "0040".equals(aucProgStatCd) ) {
			aucProgYn = "Y";
		}else {
			aucProgYn = "N";
		}
		
		myPageVO.setAucProgYn(aucProgYn);
		
		int cnt = myPageDAO.caCarAucDtlBidInfUpdate(myPageVO); 

		logger.info("cnt ▶▶▶▶▶▶ {}", cnt);
		
		if(cnt > 0) {
			myPageDAO.caCarAucInfUpdate(myPageVO);  
		}		
	}

	//내 판매차량 입찰 상세 현황 - 저장(선택되지 않은 값)
	@Override
	public void myBidInfoUnSelectedUpdate(MyPageVO myPageVO) throws Exception {
		
		myPageDAO.myBidInfoUnSelectedUpdate(myPageVO);   
		
	}

	//보증금 수납처리
	@Override
	public void depositPaymentUpdate(MyPageVO bidVO) throws Exception {
		
		bidVO.setSdepPayYn("Y");
		myPageDAO.depositPaymentUpdate(bidVO); 
		
	}
}
