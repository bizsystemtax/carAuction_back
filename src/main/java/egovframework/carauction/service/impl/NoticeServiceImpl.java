package egovframework.carauction.service.impl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.NoticeVO;
import egovframework.carauction.service.NoticeService;

@Service("NoticeService")
public class NoticeServiceImpl extends EgovAbstractServiceImpl implements NoticeService {
	
	@Resource(name = "NoticeDAO")
	private NoticeDAO noticeDAO;
	
	// 공지사항 목록
	@Override
	public Map<String, Object> noticeList(NoticeVO noticeVO) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", noticeDAO.noticeList(noticeVO));
		
		return map;
	}
	
	// 공지사항 조회수 + 1
	@Override
	public int updateViewCnt(NoticeVO noticeVO) throws Exception {
		
		int result = noticeDAO.updateViewCnt(noticeVO);
		
		return result;
	}
	
	// 공지사항 상세조회
	@Override
	public Map<String, Object> getNoticeDetail(NoticeVO noticeVO) throws Exception {
		
		// 상세조회시 조회수 + 1
		int updCnt = noticeDAO.updateViewCnt(noticeVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
//		map = noticeDAO.getNoticeDetail(noticeVO);
		map.put("resultData", noticeDAO.getNoticeDetail(noticeVO));
		
		return map;
	}
	
	// 공지사항 삭제
	@Override
	public int delNotice(NoticeVO noticeVO) throws Exception {
		
		int result = 0;
		result = noticeDAO.delNotice(noticeVO);

		return result;
	}
	
	// 첨부파일 목록 조회
	@Override
	public List<AttachFileVO> getNotiFileList(AttachFileVO attachFileVO) throws Exception {
		
		return noticeDAO.getNotiFileList(attachFileVO);
	}
	
	
}