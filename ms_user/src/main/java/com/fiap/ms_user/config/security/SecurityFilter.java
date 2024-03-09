package com.fiap.ms_user.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.ms_user.dominio.usuario.enumeration.UserRole;
import com.fiap.ms_user.dominio.usuario.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null && !token.isEmpty()) {
            try {
                String login = tokenService.validateToken(token);
                if (login != null && !login.isEmpty()) {
                    String authority = tokenService.extractAuthorityFromToken(token);
                    UserDetails user = usuarioRepository.findByEmail(login);
                    if (user != null) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                                AuthorityUtils.createAuthorityList(authority));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        if (authentication != null && authentication.isAuthenticated()) {
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.name()))) {
                                // Permitir acesso a todos os recursos
                                filterChain.doFilter(request, response);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Erro ao processar token: {}: " + e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }


    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}

