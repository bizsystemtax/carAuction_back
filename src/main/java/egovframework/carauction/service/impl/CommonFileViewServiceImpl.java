package egovframework.carauction.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.service.CommonFileViewService;



@Service("commonFileViewService")
public class CommonFileViewServiceImpl extends EgovAbstractServiceImpl implements CommonFileViewService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFileViewServiceImpl.class);
	
	@Resource(name = "commonFileViewDAO")
	private CommonFileViewDAO commonFileViewDAO;

	//첨부파일 PDF, 이미지 -> 웹브라우저 미리보기, 엑셀, 워드, 한글 직접 다운로드
	@Override
	public AttachFileVO getFileById(String fileId) throws Exception {
		return commonFileViewDAO.getFileById(fileId);
			
	}


	
	
}
