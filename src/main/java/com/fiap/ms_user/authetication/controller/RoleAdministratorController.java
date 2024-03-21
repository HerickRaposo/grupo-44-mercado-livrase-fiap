package com.fiap.ms_user.authetication.controller;


import com.fiap.ms_user.authetication.dto.RegisteredUser;
import com.fiap.ms_user.authetication.dto.SaveUser;
import com.fiap.ms_user.authetication.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting")
public class RoleAdministratorController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/administrator/cadastra_hole")
    public ResponseEntity<RegisteredUser> registerOneROLE_ADMINISTRATOR(@RequestBody @Valid SaveUser newUser){

        RegisteredUser registeredUser = authenticationService.registerOneROLE_ADMINISTRATOR(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }

}
