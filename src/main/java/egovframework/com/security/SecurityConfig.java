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
        //(↑ 추후에 삭제 처리)
        //여기부터 추가해주세요 Start
        "/myPage/list", //20250917 추가
        "/myPage/comboBoxList", //20250917 추가
        
        "/myPage/mySaleCarAuctionRegList", //내 판매차량 입찰 상세 현황 - 경매(공매)등록 내용
        "/myPage/mySaleCarBidList",		   //내 판매차량 입찰 상세 현황 - 입찰현황
        "/myPage/mySaleCarBidStatusList",  //내 판매차량 입찰 상세 현황 - 낙찰진행 및 여부 조회
        "/myPage/faileBidUpdate",          //내 판매차량 입찰 상세 현황 - 유찰
        "/myPage/successfulBidUpdate",     //내 판매차량 입찰 상세 현황 - 낙찰
        
        "/carAucInf/**",   //경매차량
        "/carAucSaleReg/**",   //경매차량판매등록
        
        "/notice/**",		// 공지사항

        //End
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/api/report-server/**"
    };

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowOrigin)); // 특정 도메인만 허용
        configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    } 

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors().configurationSource(corsConfigurationSource()).and()
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
                exceptionHandlingConfigurer
                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            )
            .build();
    }
}
