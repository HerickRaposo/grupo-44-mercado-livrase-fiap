package com.fiap.grupo44.ms_carrinho.config.security;


import com.fiap.grupo44.ms_carrinho.dominio.item.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        System.out.println("passou aqui");
        String token = (String) authentication.getCredentials();

        Claims claims = jwtService.extractAllClaims(token);

        if (claims != null) {
            String username = claims.get("sub", String.class);
            String password = claims.get("exp", String.class);
            List<String> authorities = claims.get("authorities", List.class);

            Collection<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());

            UserDetails userDetails = new User(username, password, grantedAuthorities);

            var userdetails = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
            return userdetails;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}