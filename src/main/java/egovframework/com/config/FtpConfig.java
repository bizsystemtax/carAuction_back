package egovframework.com.config;

import egovframework.com.cmm.FtpUploadService;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class FtpConfig {

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory factory = new DefaultFtpSessionFactory();
        factory.setHost(ftpHost);
        factory.setPort(ftpPort);
        factory.setUsername(ftpUsername);
        factory.setPassword(ftpPassword);
        return new CachingSessionFactory<>(factory);
    }

    @Bean
    public DirectChannel ftpChannel() {
        return new DirectChannel();
    }

    @Bean(name = "customFtpUploadService")
    public FtpUploadService ftpUploadService() {
        return new FtpUploadService();
    }
}
