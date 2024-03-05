package com.fiap.ms_user.dominio.login.controller;

import com.fiap.ms_user.dominio.login.dto.LoginDTO;
import com.fiap.ms_user.dominio.login.dto.LoginResponseDTO;
import com.fiap.ms_user.dominio.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class loginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping()
    public ResponseEntity login(@RequestBody @Valid LoginDTO dto){
        List<String> violacoesToList = loginService.validate(dto);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        String token = loginService.doLogin(dto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validateToken")
    public ResponseEntity<LoginResponseDTO> validateToken() {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO response = loginService.validateToken(token);
        if (response != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
