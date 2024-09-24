package egovframework.com.security;

import egovframework.com.jwt.JwtAuthenticationEntryPoint;
import egovframework.com.jwt.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${Globals.Allow.Origin}")
    private String allowOrigin;

    private String[] AUTH_GET_WHITELIST = {
        "/mainPage",
        "/board",
        "/board/{bbsId}/{nttId}",
        "/boardFileAtch/{bbsId}",
        "/schedule/daily",
        "/schedule/week",
        "/schedule/{schdulId}",
        "/image"
    };

    private String[] AUTH_WHITELIST = {
        "/",
        "/login/**",
        "/auth/login-jwt",
        "/auth/login",
        "/file",
        "/file/upload",
        "/ocr/process",
        "/ocr/save",
        "/excel/upload",
        "/excel/download",
        "/code/commonCode",
        "/excel/search",
        "/penaltyStd/**",
        "/fineStd/**",
        "/fineMnge/comboBoxList",
        "/fineMnge/list",
        "/fineMnge/cfmt",
        "/fineMnge/sendPlcDeptList",
        "/fineMnge/updateFine",
        "/fineMnge/deleteFine",
        "/fineMnge/uploadEfine",
        "/fineMnge/uploadEx",
        "/fineMnge/uploadWetax",
        "/fineMnge/uploadOCR",
        "/fineMnge/uploadCartax",
        "/fineMnge/downloadEfine",
        "/fineMnge/downloadEx",
        "/fineMnge/downloadWetax",
        "/fineMnge/downloadOCR",
        "/fineMnge/downloadCartax",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/api/report-server/test"
    };

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 여기 확인
        configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH")); // 여기 확인
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 여기 확인
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors() // CORS 활성화
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, AUTH_GET_WHITELIST).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandlingConfigurer -> 
                exceptionHandlingConfigurer.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            );
        return http.build();
    }



}
