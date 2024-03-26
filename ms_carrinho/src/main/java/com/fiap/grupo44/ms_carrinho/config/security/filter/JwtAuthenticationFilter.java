package com.fiap.grupo44.ms_carrinho.config.security.filter;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fiap.grupo44.ms_carrinho.adapters.out.ValidateTokenOutService;
import com.fiap.grupo44.ms_carrinho.dominio.item.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ValidateTokenOutService valiiValidateTokenOutService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtService.extractToken(request);

            if (token != null) {
                Claims claims = jwtService.extractAllClaims(token);
                Boolean isActive = valiiValidateTokenOutService.tokenIsActive(token);
                if (claims != null && Boolean.TRUE.equals(isActive)) {
                    String role = claims.get("role", String.class);
                    if (role != null) {
                        filterChain.doFilter(request, response);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(request.getRequestURI());
            System.out.println(e.getMessage());
            //SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
}

