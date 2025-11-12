package egovframework.carauction.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.AttachFileVO;

public interface CommonFileService {
	
	//첨부파일 공통(등록, 수정)
	//public void saveFiles(String targetType, String targetId, List<MultipartFile> files, Map<String, Object> paramMap) throws Exception;
	public void saveFiles(String targetType, String targetId, List<MultipartFile> files, Map<String, Object> paramMap, String userId) throws Exception;

	//파일삭제
	public void deleteFile(AttachFileVO attachFileVO) throws Exception;
}
