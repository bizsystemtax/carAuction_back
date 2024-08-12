package egovframework.penalty.service.impl;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;
import egovframework.penalty.PenaltyOcrVO;

@Repository("PenaltyOcrDAO")
public class PenaltyOcrDAO extends EgovAbstractMapper {

    public int countSimilarRecords(PenaltyOcrVO penaltyOcrVO) throws Exception {
        return selectOne("PenaltyOcrDAO.countSimilarRecords", penaltyOcrVO);
    }

    public void insertOcr(PenaltyOcrVO penaltyOcrVO) throws Exception {
        int similarRecordCount = countSimilarRecords(penaltyOcrVO);
        penaltyOcrVO.setFineSeq(similarRecordCount + 1);
        insert("PenaltyOcrDAO.insertOcr", penaltyOcrVO);
    }
}