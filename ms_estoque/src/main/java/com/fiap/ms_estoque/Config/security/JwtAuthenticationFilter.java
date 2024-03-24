package com.fiap.ms_estoque.Config.security;

import com.fiap.ms_estoque.adapters.out.ValidateTokenOutService;
import com.fiap.ms_estoque.dominio.produto.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ValidateTokenOutService validateTokenOutService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtService.extractToken(request);

            if (token != null) {
                Claims claims = jwtService.extractAllClaims(token);
                Boolean isActive = validateTokenOutService.tokenIsActive(token);
                if (claims != null && Boolean.TRUE.equals(isActive)) {
                    Authentication authentication = jwtService.buildAuthentication(claims);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);

    }


}

