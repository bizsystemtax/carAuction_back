package egovframework.carauction.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface CommonFileService {
	
	//실제 파일 저장 처리
//	public String saveFile(EgovFormBasedFileVo fileVo) throws Exception;
//
//	//새 파일 업로드가 존재하면 덮어쓰기(기존 파일 삭제)
//	public void deleteFile(String savedFilePath, String savedFileSvrNm) throws Exception;
//
//	//등록
//	public void insertFile(AttachFileVO data) throws Exception;
//
//	//수정
//	public void updateFile(AttachFileVO data) throws Exception;
//
//	//DB데이터 삭제
//	public void deleteDataFile(AttachFileVO vo) throws Exception;
//
//	public void uploadFiles(List<MultipartFile> fileList, String string, Long noticeId);

	public void saveFiles(String string, Integer noticeId, List<MultipartFile> files, Map<String, Object> paramMap);



}
