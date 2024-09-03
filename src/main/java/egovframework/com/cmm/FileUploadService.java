package egovframework.com.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import egovframework.let.utl.fcc.service.EgovFormBasedFileVo;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileUploadService {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FtpUploadService ftpUploadService;

    @Value("${file.upload.path}")
    private String uploadPath;
    
    public String saveFile(EgovFormBasedFileVo fileVo) throws IOException {
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File uploadFile = new File(uploadPath + File.separator + fileVo.getFileName());
        try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
            fos.write(fileVo.getData());
        }
        return uploadFile.getAbsolutePath();
    }


    public void uploadFileToFTP(String filePath, String fileName) {
        try {
            ftpUploadService.uploadFileToFTP(filePath, fileName);
            logger.info("File successfully uploaded to FTP: " + fileName);
        } catch (Exception e) {
            logger.error("Error uploading file to FTP: " + fileName, e);
            throw new RuntimeException("Failed to upload file to FTP", e);
        }
    }
}