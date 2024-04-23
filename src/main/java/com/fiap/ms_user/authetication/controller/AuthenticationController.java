package com.fiap.ms_user.authetication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.ms_user.authetication.dto.auth.AuthenticationReponse;
import com.fiap.ms_user.authetication.dto.auth.AuthenticationRequest;
import com.fiap.ms_user.authetication.service.auth.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {



    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate (@RequestParam String jwt){
        boolean isTokenValid = authenticationService.validateToken(jwt);
        System.err.println(isTokenValid);
        return ResponseEntity.ok(isTokenValid);
    }


    @PostMapping("authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){

        try {
            AuthenticationReponse response = authenticationService.login(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (InternalAuthenticationServiceException e) {
            System.out.println("Usuário não encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado: " + e.getCause().getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro ao autenticar <---> Usuário ou password incorretos" + e.getMessage());
        }
    }
}
