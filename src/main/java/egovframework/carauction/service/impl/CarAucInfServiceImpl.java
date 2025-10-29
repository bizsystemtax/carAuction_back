package egovframework.carauction.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.CarInfoVO;
import egovframework.carauction.CarSaleDetailVO;
import egovframework.carauction.CarSaleVO;
import egovframework.carauction.CarSearchCriteriaVO;
import egovframework.carauction.NoticeVO;
import egovframework.carauction.service.CarAucInfService;
import egovframework.carauction.service.CommonFileService;
import egovframework.carauction.web.NoticeController;

/**
 * 차량 경매 정보 서비스 구현 클래스
 */
@Service("carAucInfService")
public class CarAucInfServiceImpl extends EgovAbstractServiceImpl implements CarAucInfService {
	
	private static final Logger logger = LoggerFactory.getLogger(CarAucInfServiceImpl.class);

	@Resource(name = "carAucInfDAO")
	private CarAucInfDAO carAucInfDAO;
	
	@Resource(name = "commonFileService")
	private CommonFileService commonFileService;

	/************************************************************************************************************************
	 * 차량 경매 정보
	 ************************************************************************************************************************/

	@Override
	public List<CarInfoVO> findCarsWithConditions(CarSearchCriteriaVO criteria) throws Exception {
		// 검색 조건 전처리
		processSearchCriteria(criteria);
		return carAucInfDAO.findCarsWithConditions(criteria);
	}

	@Override
	public List<Map<String, Object>> findAllManufacturers() throws Exception {
		return carAucInfDAO.findAllManufacturers();
	}

	@Override
	public List<Map<String, Object>> findAllModels() throws Exception {
		return carAucInfDAO.findAllModels();
	}

	@Override
	public List<Map<String, Object>> findModelsByManufacturer(String manufacturer) throws Exception {
		if (manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer)) {
			return carAucInfDAO.findModelsByManufacturer(manufacturer);
		} else {
			return carAucInfDAO.findAllModels();
		}
	}

	@Override
	public List<Map<String, Object>> findAllSubModels() throws Exception {
		return carAucInfDAO.findAllSubModels();
	}

	@Override
	public List<Map<String, Object>> findSubModelsByManufacturerAndModel(String manufacturer, String model)
			throws Exception {
		CarSearchCriteriaVO criteria = new CarSearchCriteriaVO();
		criteria.setManufacturer(manufacturer);
		criteria.setModel(model);

		if ((manufacturer != null && !manufacturer.trim().isEmpty() && !"전체".equals(manufacturer))
				|| (model != null && !model.trim().isEmpty() && !"전체".equals(model))) {
			return carAucInfDAO.findSubModelsByManufacturerAndModel(criteria);
		} else {
			return carAucInfDAO.findAllSubModels();
		}
	}

	@Override
	public List<Map<String, Object>> findAllFuelTypes() throws Exception {
		return carAucInfDAO.findAllFuelTypes();
	}

	@Override
	public List<Map<String, Object>> findAllRegistrationCompanies() throws Exception {
		return carAucInfDAO.findAllRegistrationCompanies();
	}

	@Override
	public List<String> getBanks() throws Exception {
		return carAucInfDAO.findAllBanks();
	}

	@Override
	public List<String> getAccountNumbers(String bankName) throws Exception {
		return carAucInfDAO.findAccountsByBank(bankName);
	}

	@Override
	public CarInfoVO getCarDetail(String aucRegNo) throws Exception {
		return carAucInfDAO.findCarByAucRegNo(aucRegNo);
	}

	/**
	 * 검색 조건 전처리
	 * 
	 * @param criteria 검색 조건
	 */
	private void processSearchCriteria(CarSearchCriteriaVO criteria) {
		// "전체" 값을 null로 변환
		if ("전체".equals(criteria.getManufacturer())) {
			criteria.setManufacturer(null);
		}
		if ("전체".equals(criteria.getModel())) {
			criteria.setModel(null);
		}
		if ("전체".equals(criteria.getSubModel())) {
			criteria.setSubModel(null);
		}
		if ("전체".equals(criteria.getFuelType())) {
			criteria.setFuelType(null);
		}
		if ("전체".equals(criteria.getRegistrationCompany())) {
			criteria.setRegistrationCompany(null);
		}

		if ("".equals(criteria.getManufacturer())) {
			criteria.setManufacturer(null);
		}
		if ("".equals(criteria.getModel())) {
			criteria.setModel(null);
		}
		if ("".equals(criteria.getSubModel())) {
			criteria.setSubModel(null);
		}
		if ("".equals(criteria.getFuelType())) {
			criteria.setFuelType(null);
		}
		if ("".equals(criteria.getRegistrationCompany())) {
			criteria.setRegistrationCompany(null);
		}
	}
	
	@Override
	public int checkUserBidHistory(String aucRegNo, String userId) throws Exception {
	    Map<String, String> params = new HashMap<>();
	    params.put("aucRegNo", aucRegNo);
	    params.put("userId", userId);
	    
	    return carAucInfDAO.checkUserBidHistory(params);
	}

	@Override
	public String getNextAucRegSeq(String aucRegNo) throws Exception {
		return carAucInfDAO.getNextAucRegSeq(aucRegNo);
	}

	@Override
	public void insertBid(Map<String, Object> bidData) throws Exception {
		String aucRegNo = (String) bidData.get("aucRegNo");

		// 1. 다음 순번 조회
		String nextSeq = getNextAucRegSeq(aucRegNo);

		// 2. Map에 순번 추가
		bidData.put("aucRegSeq", nextSeq);

		// 3. INSERT
		carAucInfDAO.insertBid(bidData);
		
		// 4. 입찰건수 추가
		carAucInfDAO.incrementBidCount(bidData);
	}

	/************************************************************************************************************************
	 * 차량 판매 정보
	 ************************************************************************************************************************/

	// 차량정보_제조사코드, 제조사 조회
	@Override
	public Map<String, Object> manufacturList(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", carAucInfDAO.manufacturList(carSaleVO));

		return map;
	}

	// 차량정보_모델코드, 모델명 조회
	@Override
	public Map<String, Object> modNmList(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", carAucInfDAO.modNmList(carSaleVO));

		return map;
	}

	// 차량정보_등급코드, 등급명 조회
	@Override
	public Map<String, Object> grdNmList(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", carAucInfDAO.grdNmList(carSaleVO));

		return map;
	}

	// 차량정보_세부등급코드, 등급명 조회
	@Override
	public Map<String, Object> dtlGrdNmList(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", carAucInfDAO.dtlGrdNmList(carSaleVO));

		return map;
	}

	// 차량정보_기타 조회
	@Override
	public Map<String, Object> getCarAucInfo(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("result", carAucInfDAO.getCarAucInfo(carSaleVO));

		return map;
	}

	// 차량정보_연료코드 조회
	@Override
	public Map<String, Object> getCarEngCd(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", carAucInfDAO.getCarEngCd(carSaleVO));

		return map;
	}

	// 경매 판매차량 등록
//	@Override
//	public void insertCarSale(CarSaleDetailVO carSaleDetailVO) {
//		// 경매등록번호 시퀀스
//		carSaleDetailVO.setAucRegNo(carAucInfDAO.getNextId());
//
//		carAucInfDAO.insertCarSale(carSaleDetailVO);
//	}
	
	@Override
	public void insertCarSale(Map<String, Object> param, List<MultipartFile> files) throws Exception {
		CarSaleDetailVO carSaleDetailVO = (CarSaleDetailVO) param.get("carSaleDetailVO"); 
		
		logger.info("param :::::::::::: {} ", param);
		logger.info("files :::::::::::: {} ", files);
		
		String aucRegNo = carAucInfDAO.getNextId();
		carSaleDetailVO.setAucRegNo(aucRegNo); 
		
		param.put("aucRegNo", aucRegNo);
		logger.info("carSaleDetailVO :::::::::::: {} ", carSaleDetailVO);
		
		int result = carAucInfDAO.insertCarSale(carSaleDetailVO);
		
		//로직 추가
		if (result > 0 && files != null && !files.isEmpty()) {
			logger.info("일단 여기 들어와야 합니다.");
            //try {
			String aucRegNoId = (String)param.get("aucRegNo");
                
                logger.info("aucRegNoId :::::::::::: {} ", aucRegNoId);
                commonFileService.saveFiles("auc", aucRegNoId, files, param);
            //} catch (NumberFormatException e) {
             //   logger.error("aucRegNo 형식이 올바르지 않습니다: {}", aucRegNo, e);
              //  throw new Exception("경매등록번호 형식 오류", e);
            //}


            //commonFileService.saveFiles("auc", aucRegNoId, files, param);
               
        }
		
	}

	// 경매 판매차량 상세 조회
	@Override
	public Map<String, Object> getCarSaleDetail(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("result", carAucInfDAO.getCarSaleDetail(carSaleVO));

		return map;
	}
	
	// 경매 판매차량 이미지 상세 조회
	@Override
	public Map<String, Object> getCarSaleImgDetail(CarSaleVO carSaleVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", carAucInfDAO.getCarSaleImgDetail(carSaleVO));

		return map;
	}

	// 경매 판매차량 수정
	@Override
	public void updateCarSale(CarSaleDetailVO carSaleDetailVO) {

		carAucInfDAO.updateCarSale(carSaleDetailVO);
	}
	
	// 경매 판매차량 삭제
	@Override
	public void deleteCarSale(CarSaleDetailVO carSaleDetailVO) {

		carAucInfDAO.deleteCarSale(carSaleDetailVO);
	}

	
}