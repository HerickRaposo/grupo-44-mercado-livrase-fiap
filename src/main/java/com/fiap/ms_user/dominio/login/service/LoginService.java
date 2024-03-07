//package com.fiap.ms_user.dominio.login.service;
//
//import com.fiap.ms_user.config.security.TokenService;
//import com.fiap.ms_user.dominio.login.dto.LoginDTO;
//import com.fiap.ms_user.dominio.login.dto.LoginResponseDTO;
//import com.fiap.ms_user.dominio.usuario.entity.Usuario;
//import com.fiap.ms_user.dominio.usuario.service.UsuarioService;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class LoginService {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private TokenService tokenService;
//    @Autowired
//    private UsuarioService usuarioService;
//
//    public List<String> validate(LoginDTO dto){
//        Set<ConstraintViolation<LoginDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
//        List<String> violacoesToList = violacoes.stream()
//                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
//                .collect(Collectors.toList());
//        return violacoesToList;
//    }
//    public String doLogin(LoginDTO dto){
//        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
//        var auth = this.authenticationManager.authenticate(usernamePassword);
//
//        // Se a autenticação for bem-sucedida, gera o token JWT
//        if (auth.isAuthenticated()) {
//            var token = tokenService.generateToken((Usuario) auth.getPrincipal());
//            return token;
//        } else {
//            throw new BadCredentialsException("Credenciais inválidas");
//        }
//    }
//
//    public LoginResponseDTO validateToken(String token){
//        String login = tokenService.validateToken(token);
//        UserDetails user = usuarioService.findByEmail(login);
//
//        if (user != null){
//            LoginResponseDTO response = new LoginResponseDTO(token,user.getUsername(), user.getAuthorities().stream().findFirst().get().getAuthority());
//            return response;
//        }
//        return null;
//    }
//}
