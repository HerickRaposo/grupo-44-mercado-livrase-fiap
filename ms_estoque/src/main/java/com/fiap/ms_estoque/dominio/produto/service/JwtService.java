package com.fiap.ms_estoque.dominio.produto.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret.key}")
    private String SECRET_KEY;

    private final String tokenPrefix = "Bearer ";

    private SecretKey generateKey(){

        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);

//        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(passwordDecoded);

    }

    public Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith( generateKey() ).build()
                .parseSignedClaims(jwt).getPayload();
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(tokenPrefix)) {
            return header.substring(tokenPrefix.length());
        }
        return null;
    }
}
