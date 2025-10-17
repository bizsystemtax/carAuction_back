package egovframework.carauction.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.CommonFileViewService;



@Service("commonFileViewService")
public class CommonFileViewServiceImpl extends EgovAbstractServiceImpl implements CommonFileViewService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFileViewServiceImpl.class);
	
	@Resource(name = "commonFileViewDAO")
	private CommonFileViewDAO commonFileViewDAO;

	@Override
	public AttachFileVO getFileById(String fileId) throws Exception {
		return commonFileViewDAO.getFileById(fileId);
			
	}


	
	
}
