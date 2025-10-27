package egovframework.carauction.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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

@Transactional
@Service("commonFileService")
public class CommonFileServiceImpl extends EgovAbstractServiceImpl implements CommonFileService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonFileServiceImpl.class);
	
	@Resource(name = "commonFileDAO")
	private CommonFileDAO commonFileDAO;
	
	@Resource(name = "noticeDAO")
	private NoticeDAO noticeDAO;
	
	@Value("${file.upload.path}")  // properties 파일의 file.upload.path 값을 읽어옴
    private String baseUploadDir;
	
	@Value("${Globals.posblAtchFileSize}")
	private long maxFileSize; //파일 사이즈 50MB 제한

	//파일 등록
	@Override                             
	public void saveFiles(String targetType, String targetId, List<MultipartFile> files, Map<String, Object> paramMap) throws Exception {
		
		//LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		logger.error("Start :::::::: {} ", targetId);
		String entryIdno = null;
		String updatIdno = null;
		
		if("user".equals(targetType)) {
			logger.error("targetType :::::::: {} ", targetType);
			entryIdno = targetId;
			updatIdno = targetId;
		} else {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			entryIdno = loginVO.getId();
	        updatIdno = loginVO.getId();
		}

	    logger.error("entryIdno :::::::: {} ", entryIdno);
	    logger.error("updatIdno :::::::: {} ", updatIdno);
		
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
            
            //파일 크기 제한
            if (file.getSize() > maxFileSize) {
                logger.info("업로드 파일 크기 초과 {} ", file.getSize(), maxFileSize);
                throw new IllegalArgumentException("업로드 가능한 파일 크기는 최대 " + (maxFileSize / (1024 * 1024)) + "MB입니다.");
            }

            String originalFilename = file.getOriginalFilename();
            String ext = "";
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex > -1) {
                ext = originalFilename.substring(dotIndex);
            }
            
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

            // 파일 DB 저장용 파라미터 설정
            Map<String, Object> fileParam = new HashMap<>();
            fileParam.put("targetType", targetType);                    // 대상 타입 (예: "notice")
            fileParam.put("targetId", targetId);                        // 대상 ID (공지사항 ID)
            fileParam.put("attFileNm", originalFilename);               // 원본 파일명
            fileParam.put("attFileSize", (int) file.getSize());         // 파일 사이즈
            fileParam.put("attFilePath", targetPath.toString());       	// 절대 경로
            fileParam.put("attFileType", ext);                          // 확장자만 저장 (ex: ".jpg")
            fileParam.put("entryIdno", entryIdno);        				// 등록자
            fileParam.put("updatIdno", updatIdno);        				// 수정자
            fileParam.put("fileSeq", fileSeq);                          // 파일 순번
            fileParam.put("fileSvrName", finalFileName);                // UUID+확장자 파일명

            commonFileDAO.insertFile(fileParam);

            fileSeq++; // 순번 증가
        }
    }

	//파일삭제
	@Override
	public void deleteFile(AttachFileVO attachFileVO) throws Exception {
		
		logger.info("▶▶▶▶▶▶▶▶▶▶▶▶▶ {} ", attachFileVO);
		
		//DB 테이터 삭제하기 전 데이터 조회
		AttachFileVO deleteFile = commonFileDAO.selectFileInfo(attachFileVO);
		
		if(deleteFile == null) {
			throw new FileNotFoundException("파일 정보를 찾을 수 없습니다.");
		}
		
		String filePath = deleteFile.getAttFilePath(); //파일경로
		String fileName = deleteFile.getFileSvrName(); //파일명
		
		logger.info("filePath ▶▶▶▶▶▶▶▶▶▶▶▶▶ {} ", filePath);
		logger.info("fileName ▶▶▶▶▶▶▶▶▶▶▶▶▶ {} ", fileName);
		
		if(filePath.endsWith(fileName)) {
			filePath = filePath.substring(0, filePath.length() - fileName.length());
			
			// (/ 또능 \ 제거)
			if(filePath.endsWith("\\") || filePath.endsWith("/")) {
				filePath = filePath.substring(0, filePath.length() - 1);
			}
		}
		
		Path fullFilePath = Paths.get(filePath, fileName);
		File file = fullFilePath.toFile();
		
		logger.info("fullFilePath ▶▶▶▶▶▶▶▶▶▶▶▶▶ {} ", fullFilePath);
		logger.info("file         ▶▶▶▶▶▶▶▶▶▶▶▶▶ {} ", file);
		
		//실제 경로에서 파일 삭제
		if(file.exists()) {
			boolean deleted = file.delete();
			
			if (!deleted) {
	            throw new IOException("실제 파일 삭제 실패 {}" + fullFilePath);
	        } else {
	            logger.info("파일 삭제 성공 ▶▶▶▶▶▶▶▶▶▶▶▶▶ {}", fullFilePath);
	        }
	    } else {
	        logger.warn("파일이 존재하지 않아 삭제되지 않음 ▶▶▶▶▶▶▶▶▶▶▶▶▶ {}", fullFilePath);
	    }
		
		//실제 DB데이터 삭제
		int result = commonFileDAO.deleteFile(attachFileVO);
		
	    if (result < 1) {
	        throw new RuntimeException("DB 파일 정보 삭제 실패");
	    } else {
	        logger.info("DB 파일 정보 삭제 성공 ▶▶▶▶▶▶▶▶▶▶▶▶▶ {}", attachFileVO.getAttFileId());
	        
	        int targetId = deleteFile.getTargetId();
	        logger.info("targetId ▶▶▶▶▶▶▶▶▶▶▶▶▶ {}", targetId);
	        
	        //noticeDAO 호출
	        NoticeVO noticeVO = new NoticeVO();
	        noticeVO.setNoticeId(deleteFile.getTargetId());
	        
	        int fileCnt = noticeDAO.getAttFileCnt(noticeVO);
	        logger.info("fileCnt ▶▶▶▶▶▶▶▶▶▶▶▶▶ {}", fileCnt);
	        
	        if(fileCnt > 0) {
	        	noticeVO.setAttFileYn("Y");
	        	noticeDAO.updAttFileYn(noticeVO);
	        	logger.info("첨부파일이 한건이라도 존재하는 경우 Y {}", noticeVO.getAttFileYn());
	        }else {
	        	noticeVO.setAttFileYn("N");
	        	noticeDAO.updAttFileYn(noticeVO);
	        	logger.info("첨부파일이 단 한건도 존재하지 않는 경우 N {}", noticeVO.getAttFileYn());
	        }
	        
	    }
	}
	
}
