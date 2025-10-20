package egovframework.carauction.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.carauction.AttachFileVO;
import egovframework.carauction.NoticeVO;
import egovframework.carauction.service.CommonFileService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.utl.fcc.service.EgovFormBasedFileVo;


@Service("commonFileService")
public class CommonFileServiceImpl extends EgovAbstractServiceImpl implements CommonFileService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFileServiceImpl.class);
	
	@Resource(name = "commonFileDAO")
	private CommonFileDAO commonFileDAO;
	
	//private static final String BASE_UPLOAD_DIR = "/upload/files"; // 실제 경로로 수정 필요
	
	@Value("${file.upload.path}")  // properties 파일의 file.upload.path 값을 읽어옴
    private String baseUploadDir;

	//파일 등록
	@Override                             
	public void saveFiles(String targetType, String targetId, List<MultipartFile> files, Map<String, Object> paramMap) {
		
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		logger.error("loginVO :::::::: {} ", loginVO);
		
		//파일 없으면 바로 종료
		if (files == null || files.isEmpty()) {
            return; 
        }
		
		String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        Path targetFolder = Paths.get(baseUploadDir, targetType, today);

        try {
            if (!Files.exists(targetFolder)) {
                Files.createDirectories(targetFolder);
            }
        } catch (IOException e) {
            logger.error("디렉토리 생성 실패: {}", targetFolder.toString(), e);
            throw new RuntimeException("업로드 디렉토리 생성 중 오류 발생", e);
        }

        int fileSeq = 1; // 파일 순번 시작

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalFilename = file.getOriginalFilename();
            String ext = "";
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex > -1) {
                ext = originalFilename.substring(dotIndex);
            }

            //String savedFileName = originalFilename + "_" + UUID.randomUUID().toString() + ext;
            //Path targetPath = targetFolder.resolve(savedFileName);
            
            // 현재 시간 추가
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            // UUID 추가
            String uuid = UUID.randomUUID().toString();

            // 최종 파일명 (확장자 포함)
            String finalFileName = originalFilename.replace(ext, "") + "_" + timestamp + "_" + uuid + ext;
            // → "판매차량 입찰 현황_20251016_130733_xxxx-xxxx.xlsx"
            
            
            logger.info("데이터 확인 ▶▶▶▶▶▶▶▶▶▶▶▶ {} ", finalFileName);
            // 절대 경로
            Path targetPath = targetFolder.resolve(finalFileName);
            
            try {
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("파일 저장 실패: {}", originalFilename, e);
                throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
            }
            
            //String attFileId = commonFileDAO.insertFile(fileParam);

            // 파일 DB 저장용 파라미터 설정
            Map<String, Object> fileParam = new HashMap<>();
            fileParam.put("targetType", targetType);                        // 대상 타입 (예: "notice")
            fileParam.put("targetId", targetId);                          // 대상 ID (공지사항 ID)
            fileParam.put("attFileNm", originalFilename);                 // 원본 파일명
            fileParam.put("attFileSize", (int) file.getSize());           // 파일 사이즈
            fileParam.put("attFilePath", targetPath.toString());       	  // 절대 경로
            fileParam.put("attFileType", ext);                            // 확장자만 저장 (ex: ".jpg")
            fileParam.put("entryIdno", loginVO.getId());        // 등록자
            fileParam.put("updatIdno", loginVO.getId());        // 수정자
            fileParam.put("fileSeq", fileSeq);                            // 파일 순번
            fileParam.put("fileSvrName", finalFileName);                  // UUID+확장자 파일명

            commonFileDAO.insertFile(fileParam);

            fileSeq++; // 순번 증가
        }
    }
	
}
