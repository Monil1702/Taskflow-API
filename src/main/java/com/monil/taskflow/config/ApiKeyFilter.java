package com.monil.taskflow.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    private final byte[] expectedKey;
    public ApiKeyFilter(@Value("${taskflow.api-key}") String expectedKey) {
        this.expectedKey = expectedKey.getBytes(StandardCharsets.UTF_8);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator/health") || path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        byte[] supplied = request.getHeader("X-API-Key") == null ? new byte[0]
            : request.getHeader("X-API-Key").getBytes(StandardCharsets.UTF_8);
        if (!MessageDigest.isEqual(expectedKey, supplied)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            response.getWriter().write("{\"title\":\"Unauthorized\",\"status\":401,\"detail\":\"A valid X-API-Key header is required\"}");
            return;
        }
        chain.doFilter(request, response);
    }
}
