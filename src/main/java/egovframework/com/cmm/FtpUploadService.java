package egovframework.com.cmm;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FtpUploadService {

    public boolean uploadFileToFTP(String localFilePath, String remoteFileName) {
        String ftpHost = "192.168.0.46";
        int ftpPort = 21;
        String ftpUser = "bizsystem_dev";
        String ftpPassword = "bizsystem#99";
        String remoteDir = "/remote/path"; // 원격 경로

        FTPClient ftpClient = new FTPClient();

        boolean isUploaded = false;

        try {
            ftpClient.setControlEncoding("UTF-8");  // 인코딩 설정
            ftpClient.connect(InetAddress.getByName(ftpHost), ftpPort);  // 커넥션 수립
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.out.println(ftpHost + " FTP server refused connection");
                return false;
            } else {
                System.out.println(ftpHost + " FTP server connection success");
            }

            if (ftpClient.login(ftpUser, ftpPassword)) {
                System.out.println("login success");
            } else {
                System.out.println("login failed");
                return false;
            }

            File localFile = new File(localFilePath);
            try (InputStream inputStream = new FileInputStream(localFile)) {
                // 시간을 파일명에 추가하기 위한 포맷터 설정
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                String timestamp = LocalDateTime.now().format(formatter);

                // 원격 파일 경로에 시간을 추가
                String remoteFileFullPath = remoteDir + "/" + timestamp + "_" + remoteFileName;
                System.out.println("업로드 시도 중: 로컬 파일 = " + localFilePath + ", 원격 파일 = " + remoteFileFullPath);

                // 한글 파일명을 UTF-8로 인코딩 후 전송
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.sendCommand("OPTS UTF8 ON");

                isUploaded = ftpClient.storeFile(new String(remoteFileFullPath.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), inputStream);
                if (isUploaded) {
                    System.out.println("파일 업로드 성공: " + remoteFileFullPath);
                } else {
                    System.out.println("파일 업로드 실패: " + remoteFileFullPath);
                    System.out.println("FTP Reply Code: " + ftpClient.getReplyCode());
                    System.out.println("FTP Reply String: " + ftpClient.getReplyString());
                }
            } catch (IOException e) {
                System.out.println("로컬 파일을 읽는 중 에러 발생: " + e.getMessage());
            }

            ftpClient.logout();
        } catch (IOException e) {
            System.out.println("FTP 서버와의 연결 중 에러 발생: " + e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                System.out.println("FTP 클라이언트 종료 중 에러 발생: " + e.getMessage());
            }
        }

        return isUploaded;
    }
}
