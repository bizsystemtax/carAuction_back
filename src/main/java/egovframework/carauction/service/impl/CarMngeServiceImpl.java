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

	// 차량관리 등록
	@Override
	public Map<String, Object> insCarMnge(CarMngeVO carMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		if ("input".equals(carMngeVO.getCarModelCd())) {
			String msg = directInput("model", carMngeVO);

			if (!"".equals(msg)) {
				map.put("resultMsg", msg);

				logger.info("map ▶▶▶▶▶▶ {}", map);

				return map;
			}
		}

		if ("input".equals(carMngeVO.getCarLevelCd())) {
			String msg = directInput("level", carMngeVO);

			if (!"".equals(msg)) {
				map.put("resultMsg", msg);

				logger.info("map ▶▶▶▶▶▶ {}", map);

				return map;
			}
		}

		String msg = directInput("dtLevel", carMngeVO);

		if (!"".equals(msg)) {
			map.put("resultMsg", msg);

			logger.info("map ▶▶▶▶▶▶ {}", map);

			return map;
		}

		String carMainNo = carMngeVO.getMkrCd() + carMngeVO.getCarModelCd() + carMngeVO.getCarLevelCd()
				+ carMngeVO.getCarDtLevelCd();

		carMngeVO.setCarMainNo(carMainNo);
		carMngeVO.setRepCarYn("010".equals(carMngeVO.getCarDtLevelCd()) ? "Y" : "N");

		int resultCnt = carMngeDAO.insCarMnge(carMngeVO);

		map.put("resultCnt", resultCnt);

		if (resultCnt > 0) {
			map.put("carMainNo", carMainNo);
		}

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}

	// 차량관리 수정
	@Override
	public Map<String, Object> updCarMnge(CarMngeVO carMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultCnt", carMngeDAO.updCarMnge(carMngeVO));

		logger.info("map ▶▶▶▶▶▶ {}", map);

		return map;
	}

	private String directInput(String key, CarMngeVO carMngeVO) {
		String msg = "";
		String text = "";
		int chkCnt = 0;

		switch (key) {
		case "model":
			chkCnt = carMngeDAO.selChkCarModelNm(carMngeVO);
			text = "모델";
			break;

		case "level":
			chkCnt = carMngeDAO.selChkCarLevelNm(carMngeVO);
			text = "등급명";
			break;

		case "dtLevel":
			chkCnt = carMngeDAO.selChkCarDtLevelNm(carMngeVO);
			text = "세부등급명";
			break;
		}

		if (chkCnt > 0) {
			msg = "중복된 차량 " + text + "입니다.";

			return msg;
		}

		String maxCd = "";

		switch (key) {
		case "model":
			maxCd = carMngeDAO.selMaxCarModelCd(carMngeVO);
			break;

		case "level":
			maxCd = carMngeDAO.selMaxCarLevelCd(carMngeVO);
			break;

		case "dtLevel":
			maxCd = carMngeDAO.selMaxCarDtLevelCd(carMngeVO);
			break;
		}

		if (maxCd != null) {
			int intMaxCarModelCd = Integer.parseInt(maxCd) + 10;
			maxCd = zeroFill(intMaxCarModelCd, 3);
		} else {
			maxCd = "010";
		}

		switch (key) {
		case "model":
			carMngeVO.setCarModelCd(maxCd);
			break;

		case "level":
			carMngeVO.setCarLevelCd(maxCd);
			break;

		case "dtLevel":
			carMngeVO.setCarDtLevelCd(maxCd);
			break;
		}

		return msg;
	}

	private String zeroFill(int num, int digits) {
		return String.format("%0" + digits + "d", num);
	}
}