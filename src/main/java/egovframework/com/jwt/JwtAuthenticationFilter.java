package egovframework.com.jwt;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import egovframework.com.cmm.LoginVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * fileName       : JwtAuthenticationFilter
 * author         : crlee
 * date           : 2023/06/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/06/11        crlee       최초 생성
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private EgovJwtTokenUtil jwtTokenUtil;
    public static final String HEADER_STRING = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        boolean verificationFlag = true;

        // step 1. request header에서 토큰을 가져온다.
        String jwtToken = EgovStringUtil.isNullToString(req.getHeader(HEADER_STRING));
        
        // step 2. 토큰에 내용이 있는지 확인해서 id값을 가져옴
        // Exception 핸들링 추가처리 (토큰 유효성, 토큰 변조 여부, 토큰 만료여부)
        // 내부적으로 parse하는 과정에서 해당 여부들이 검증됨
        String id = null;

        try {
            id = jwtTokenUtil.getUserIdFromToken(jwtToken);
            if (id == null) {
                logger.debug("jwtToken not validate");
                verificationFlag =  false;
            }
            logger.debug("===>>> id = " + id);
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal argument while verifying JWT Token: " + e.getMessage());
            verificationFlag = false;
        } catch (ExpiredJwtException e) {
            logger.debug("Expired JWT Token: " + e.getMessage());
            verificationFlag = false;
        } catch (MalformedJwtException e) {
            logger.debug("Malformed JWT Token: " + e.getMessage());
            verificationFlag = false;
        } catch (UnsupportedJwtException e) {
            logger.debug("Unsupported JWT Token: " + e.getMessage());
            verificationFlag = false;
        } catch (SignatureException e) {
            logger.debug("Invalid JWT signature: " + e.getMessage());
            verificationFlag = false;
        } catch (Exception e) {
            logger.debug("Unable to verify JWT Token: " + e.getMessage());
            verificationFlag = false;
        }

        if (verificationFlag) {
        	String ip = req.getHeader("X-Forwarded-For");
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("Proxy-Client-IP");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("WL-Proxy-Client-IP");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("HTTP_CLIENT_IP");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("X-Real-IP");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("X-RealIP");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getHeader("REMOTE_ADDR");
        	}
        	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        		ip = req.getRemoteAddr();
        	}
    		if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
	        	InetAddress address = InetAddress.getLocalHost();
	        	ip = address.getHostAddress();
        	}

            logger.debug("jwtToken validated");
            LoginVO loginVO = new LoginVO();
            loginVO.setId(id);
            loginVO.setUserSe(jwtTokenUtil.getUserSeFromToken(jwtToken));
            loginVO.setOrgnztId(jwtTokenUtil.getInfoFromToken("orgnztId", jwtToken));
            loginVO.setName(jwtTokenUtil.getInfoFromToken("name", jwtToken));
            loginVO.setIp(ip != null ? ip : "unknown");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginVO, null,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // If JWT is invalid, clear the SecurityContext
            SecurityContextHolder.clearContext();
            logger.debug("SecurityContext cleared due to invalid JWT Token");
        }

        chain.doFilter(req, res);
    }
}
