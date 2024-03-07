package com.fiap.ms_user.jesse.controller;


import com.fiap.ms_user.jesse.dto.auth.AuthenticationReponse;
import com.fiap.ms_user.jesse.dto.auth.AuthenticationRequest;
import com.fiap.ms_user.jesse.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {



    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate (@RequestParam String jwt){
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }


    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationReponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){

        AuthenticationReponse response = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(response);




    }
}
