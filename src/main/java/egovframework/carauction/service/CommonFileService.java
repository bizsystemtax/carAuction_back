package egovframework.carauction.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface CommonFileService {
	
	//첨부파일 공통(등록, 수정)
	public void saveFiles(String string, Integer noticeId, List<MultipartFile> files, Map<String, Object> paramMap);



}
