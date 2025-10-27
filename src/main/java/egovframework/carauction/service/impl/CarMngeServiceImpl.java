package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.carauction.CarMngeVO;
import egovframework.carauction.service.CarMngeService;

@Service("CarMngeService")
public class CarMngeServiceImpl extends EgovAbstractServiceImpl implements CarMngeService {

	private static final Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);

	@Resource(name = "carMngeDAO")
	private CarMngeDAO carMngeDAO;

	// 차량관리 검색조건(제조사) 조회
	@Override
	public Map<String, Object> selMkrList(@Param("locImpGb") String locImpGb) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", carMngeDAO.selMkrList(locImpGb));

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}

	// 차량관리 검색조건(모델, 등급명, 세부등급명) 조회
	@Override
	public Map<String, Object> selSrchCondList(CarMngeVO carMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", carMngeDAO.selSrchCondList(carMngeVO));

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}

	// 차량관리 검색조건(연료) 조회
	@Override
	public Map<String, Object> selFuelList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", carMngeDAO.selFuelList());

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}

	// 차량관리 리스트 조회
	@Override
	public Map<String, Object> getCarMngeList(CarMngeVO carMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", carMngeDAO.selCarMngeList(carMngeVO));

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}

	// 차량관리 상세 조회
	@Override
	public Map<String, Object> getCarMnge(@Param("carMainNo") String carMainNo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", carMngeDAO.selCarMnge(carMainNo));

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}
}