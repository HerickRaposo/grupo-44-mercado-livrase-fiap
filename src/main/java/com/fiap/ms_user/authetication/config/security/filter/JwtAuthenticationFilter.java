package com.fiap.ms_user.authetication.config.security.filter;

import com.fiap.ms_user.authetication.exceptions.ObjectNotFoundException;
import com.fiap.ms_user.authetication.persistence.entity.User;
import com.fiap.ms_user.authetication.service.UserService;
import com.fiap.ms_user.authetication.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. obter http cabeçalho chamado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //2. obetr token Jwt desde o cabeçalho
        String jwt = authorizationHeader.split("")[1];

        //3. obter o subject username desde o token; esta ação valida o formato do token, a assinatura e data de expiração
        String username = jwtService.extractUsername(jwt);

        //4. Setar o object authentication dentro de security context holder
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                username,null,user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. Executar o registro de filtros
        filterChain.doFilter(request,response);

    }
}
