//package com.fiap.ms_user.config.security;
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fiap.ms_user.dominio.usuario.repository.UsuarioRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class SecurityFilter extends OncePerRequestFilter {
//    @Autowired
//    TokenService tokenService;
//    @Autowired
//    UsuarioRepository usuarioRepository;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("Entrou");
//        var token = this.recoverToken(request);
//        if(token != null){
//            var login = tokenService.validateToken(token);
//            if (!login.isEmpty()){
//                // Obtém a autoridade do token
//                String authority = extractAuthorityFromToken(token);
////                UserDetails user = usuarioRepository.findByEmail(login);
//
//
//                // Verifica se o usuário já está autenticado
//                if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails user = usuarioRepository.findByEmail(login);
//                    if (user != null) {
//                        // Define as autoridades do usuário na autenticação
//                        var authentication = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(authority));
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
//                }
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractAuthorityFromToken(String token) {
//        // Extrai a autoridade do token
//        DecodedJWT decodedJWT = JWT.decode(token);
//        return decodedJWT.getClaim("role").asString();
//    }
//
//    private String recoverToken(HttpServletRequest request){
//        var authHeader = request.getHeader("Authorization");
//        if(authHeader == null) return null;
//        return authHeader.replace("Bearer ", "");
//    }
//}
//
