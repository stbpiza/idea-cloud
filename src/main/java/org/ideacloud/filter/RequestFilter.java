package org.ideacloud.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ideacloud.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Slf4j
public class RequestFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        long start = System.currentTimeMillis();
        chain.doFilter(requestWrapper, response);
        long end = System.currentTimeMillis();
        long elapseTime = end - start;
        ObjectMapper mapper = new ObjectMapper();
        String params = "";
        try {
            params = mapper.writeValueAsString(request.getParameterMap());
        } catch (JsonProcessingException ignored){
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info(authentication.getPrincipal().toString());
        AuthUser authUser = authentication != null &&
                !authentication.getPrincipal().equals("anonymousUser") ?
                (AuthUser) authentication.getPrincipal() :
                null;
        log.info("{" +
                        "\"method\":\"{}\", " +
                        "\"url\":\"{}\", " +
                        "\"statusCode\" : {}, " +
                        "\"latency\" : {}, " +
                        "\"id\" : {}, " +
                        "\"ip\" : {}, " +
                        "\"userAgent\" : \"{}\", " +
                        "\"param\" : {}, " +
                        "\"body\" : {}" +
                        "}",
                ((HttpServletRequest) request).getMethod(),
                ((HttpServletRequest)request).getRequestURI(),
                responseWrapper.getStatus(),
                elapseTime,
                authUser != null ? authUser.id() : null,
                getIpAddress((HttpServletRequest)request),
                ((HttpServletRequest) request).getHeader("user-agent"),
                params,
                getRequestBody(requestWrapper));
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        String payload = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            if (wrapper.getContentLength() > 0) {
                payload = new String(wrapper.getContentAsByteArray());
            }
        }
        return payload != null ? payload.replace("\n", "") : "\"\"";
    }


    private String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.contains(",")) {
            log.info("Multiple IP detected. IP: {}", ip);
            String[] split = ip.split(",");
            ip = split[0];
        }
        return ip;

    }
}