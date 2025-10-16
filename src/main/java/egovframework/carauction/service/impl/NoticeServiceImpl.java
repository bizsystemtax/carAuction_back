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

@Service("noticeService")
public class NoticeServiceImpl extends EgovAbstractServiceImpl implements NoticeService {
	
	@Resource(name = "noticeDAO")
	private NoticeDAO noticeDAO;
	
	// 공지사항 목록
	@Override
	public Map<String, Object> noticeList(NoticeVO noticeVO) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", noticeDAO.noticeList(noticeVO));
		
		return map;
	}
	
	// 공지사항 상세조회
	@Override
	public Map<String, Object> getNoticeDetail(NoticeVO noticeVO) throws Exception {
		
		// 상세조회시 조회수 + 1
		int updCnt = noticeDAO.updateViewCnt(noticeVO);
		
		Map<String, Object> map = new HashMap<String, Object>();

		// 상세조회 
		map.put("resultData", noticeDAO.getNoticeDetail(noticeVO));
		
		// 이전글, 다음글 조회
		map.put("prevNotice", noticeDAO.getPrevNotice(noticeVO));
		map.put("nextNotice", noticeDAO.getNextNotice(noticeVO));
		
		// 첨부파일 목록 조회
		AttachFileVO attachFileVO = new AttachFileVO();
		
		attachFileVO.setTargetId(noticeVO.getNoticeId());
	    List<AttachFileVO> fileList = noticeDAO.getNotiFileList(attachFileVO); 

		map.put("resultFiles", fileList);
		
		return map;
	}
	
	// 공지사항 등록
	@Override
	public int insNotice(Map<String, Object> paramMap) throws Exception {
		
		int result = noticeDAO.insNotice(paramMap);
		
		return result;
	}
	
	// 공지사항 수정
	@Override
	public int updNotice(Map<String, Object> paramMap) throws Exception {
		
		int result = noticeDAO.updNotice(paramMap);
		
		return result;
	}
	
	// 공지사항 삭제
	@Override
	public int delNotice(NoticeVO noticeVO) throws Exception {
		
		int result = noticeDAO.delNotice(noticeVO);
		
		return result;
	}
	
	// 첨부파일 목록 조회
	@Override
	public List<AttachFileVO> getNotiFileList(AttachFileVO attachFileVO) throws Exception {
		
		return noticeDAO.getNotiFileList(attachFileVO);
	}
	
	
}