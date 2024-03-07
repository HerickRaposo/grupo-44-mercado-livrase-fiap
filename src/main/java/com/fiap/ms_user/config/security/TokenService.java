//package com.fiap.ms_user.config.security;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.fiap.ms_user.dominio.usuario.entity.Usuario;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//
//@Service
//public class TokenService {
//    @Value("${api.security.token.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private Long expiration;
//
////    public String generateToken(Usuario usuario) {
////        Map<String, Object> claims = new HashMap<>();
////        return createToken(claims, usuario.getUsername());
////    }
//
////    private String generateToken(Map<String, Object> claims, String subject) {
////        return Jwts.builder()
////                .setClaims(claims)
////                .setSubject(subject)
////                .setIssuedAt(new Date(System.currentTimeMillis()))
////                .setExpiration(new Date(System.currentTimeMillis() + expiration))
////                .signWith(SignatureAlgorithm.HS512, secret)
////                .compact();
////    }
//
//    public String generateToken(Usuario usuario){
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//            String token = JWT.create()
//                    .withIssuer("auth-api")
//                    .withSubject(usuario.getEmail())
//                    .withClaim("role", usuario.getRole().name()) // Adiciona a autoridade do usuário ao token
//                    .withExpiresAt(genExpirationDate())
//                    .sign(algorithm);
//            return token;
//        } catch (JWTCreationException exception) {
//            throw new RuntimeException("Error while generating token", exception);
//        }
//    }
//
////    public Boolean validateToken(String token, Usuario usuario) {
////        final String username = extractUsername(token);
////        return (username.equals(usuario.getUsername()) && !isTokenExpired(token));
////    }
////
////    public String extractUsername(String token) {
////        return extractClaim(token, Claims::getSubject);
////    }
////
////    public Date extractExpiration(String token) {
////        return extractClaim(token, Claims::getExpiration);
////    }
////
////    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
////        final Claims claims = extractAllClaims(token);
////        return claimsResolver.apply(claims);
////    }
////
////    private Claims extractAllClaims(String token) {
////        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
////    }
////
////    private Boolean isTokenExpired(String token) {
////        return extractExpiration(token).before(new Date());
////    }
////
//
//
//
//    public String validateToken(String token){
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//            return JWT.require(algorithm)
//                    .withIssuer("auth-api")
//                    .build()
//                    .verify(token)
//                    .getSubject();
//        } catch (JWTVerificationException exception){
//            return "";
//        }
//    }
//
//    private Instant genExpirationDate(){
//
//        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
//    }
//}
