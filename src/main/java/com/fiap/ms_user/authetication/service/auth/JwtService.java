package com.fiap.ms_user.authetication.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret.key}")
    private String SECRET_KEY;


    public String genereteToken(UserDetails user,  Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

//        String jwt = Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(user.getUsername())
//                .setIssuedAt(issuedAt)
//                .setExpiration(expiration)
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
//                .signWith(generateKey(), SignatureAlgorithm.HS256)
//                .compact();
//
        String jwt = Jwts.builder()
                .header()
                    .type("JWT")
                    .and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)

                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();


        return jwt;
    }

    private SecretKey generateKey(){

        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);

//        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(passwordDecoded);

    }

    public String extractUsername(String jwt) {

        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith( generateKey() ).build()
                .parseSignedClaims(jwt).getPayload();
    }


}
