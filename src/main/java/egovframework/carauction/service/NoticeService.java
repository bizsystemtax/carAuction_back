package egovframework.carauction.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.NoticeVO;

public interface NoticeService {

	//공지사항 - 공지사항 목록 
	public Map<String, Object> noticeList(NoticeVO noticeVO) throws Exception;
	
	// 공지사항 조회수 + 1
//	public int updateViewCnt(NoticeVO noticeVO) throws Exception;
	
	// 공지사항 상세조회
	public Map<String, Object> getNoticeDetail(NoticeVO noticeVO) throws Exception;
	
	// 공지사항 등록
	public int insNotice(Map<String, Object> paramMap, List<MultipartFile> files) throws Exception;  //files  추가(20251016)
	
	// 공지사항 수정
	public int updNotice(Map<String, Object> paramMap, List<MultipartFile> files) throws Exception;
	
	// 공지사항 삭제
	public int delNotice(NoticeVO noticeVO) throws Exception;
	
	// 첨부파일 목록 조회
	public List<AttachFileVO> getNotiFileList(AttachFileVO attachFileVO) throws Exception;

	
}
