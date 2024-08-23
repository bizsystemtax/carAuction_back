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

@Service
public class ExcelUploadService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FtpUploadService ftpUploadService;

    @Value("${file.upload.path}")
    private String uploadPath;

    public String saveFile(EgovFormBasedFileVo fileVo) throws IOException {
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();  // 디렉토리가 존재하지 않을 경우 생성
        }
        File uploadFile = new File(uploadPath + File.separator + fileVo.getFileName());

        try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
            fos.write(fileVo.getData());
        }

        return uploadFile.getAbsolutePath();
    }

    public void saveExcelDataToDatabase(String filePath, JsonNode jsonData) throws Exception {
        String insertSql = "INSERT INTO penalty.excel (col1, col2, col3, col4, col5, col6, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        for (JsonNode row : jsonData) {
            String col1 = row.has("col1") ? row.get("col1").asText() : null;
            String col2 = row.has("col2") ? row.get("col2").asText() : null;
            String col3 = row.has("col3") ? row.get("col3").asText() : null;
            String col4 = row.has("col4") ? row.get("col4").asText() : null;
            String col5 = row.has("col5") ? row.get("col5").asText() : null;
            String col6 = row.has("col6") ? row.get("col6").asText() : null;
            Date createdAt = new Date();

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, col1);
                ps.setString(2, col2);
                ps.setString(3, col3);
                ps.setString(4, col4);
                ps.setString(5, col5);
                ps.setString(6, col6);
                ps.setDate(7, new java.sql.Date(createdAt.getTime()));

                ps.executeUpdate();
            }
        }

        // FTP 서버에 파일 업로드
        ftpUploadService.uploadFileToFTP(filePath, new File(filePath).getName());
    }
}
