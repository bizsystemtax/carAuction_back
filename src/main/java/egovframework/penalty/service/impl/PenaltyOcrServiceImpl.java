package egovframework.penalty.service.impl;

import egovframework.penalty.PenaltyOcrVO;
import egovframework.penalty.service.PenaltyOcrService;

import javax.annotation.Resource;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Service;

@Service("PenaltyOcrService")
public class PenaltyOcrServiceImpl extends EgovAbstractMapper implements PenaltyOcrService {
	@Resource(name = "PenaltyOcrDAO")
	private PenaltyOcrDAO penaltyOcrDAO;
	
    @Override
    public void insertOcr(PenaltyOcrVO penaltyOcrVO) throws Exception {
    	penaltyOcrDAO.insertOcr(penaltyOcrVO);
    }
}
