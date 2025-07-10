package com.dailyword.gateway.filter;

import com.dailyword.gateway.adapter.out.client.AuthClient;
import com.dailyword.gateway.dto.auth.TokenVerifyRequest;
import com.dailyword.gateway.exception.AuthApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = resolveToken(request);

        if (StringUtils.hasText(accessToken)) {
            try {
                // 🔥 module-auth 호출해서 subject(refCode) 반환받음
                String refCode = authClient.verifyToken(accessToken).getData().getMemberRefCode();

                Authentication authentication = new UsernamePasswordAuthenticationToken(refCode, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthApiException e) {
                log.warn("Invalid JWT: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
