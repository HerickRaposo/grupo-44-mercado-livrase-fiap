package com.fiap.ms_estoque.Config.security.filter;

import com.fiap.ms_estoque.adapters.out.ValidateTokenOutService;
import com.fiap.ms_estoque.dominio.produto.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ValidateTokenOutService validateTokenOutService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getRequestURI());
        try {
            String token = jwtService.extractToken(request);

            if (token != null) {
                Claims claims = jwtService.extractAllClaims(token);
                Boolean isActive = validateTokenOutService.tokenIsActive(token);
                if (claims != null && Boolean.TRUE.equals(isActive)) {
                    String username = claims.get("sub", String.class);
                    String password = claims.get("ext", String.class);
                    String role = claims.get("role", String.class);
                    System.out.println("Aqui veio a role  " + role);
                    if (role.equals("ROLE_ADMINISTRATOR")) {
                        filterChain.doFilter(request, response);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }



    }
}

