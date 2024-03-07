package com.fiap.ms_user.jesse.service.auth;


import com.fiap.ms_user.jesse.dto.RegisteredUser;
import com.fiap.ms_user.jesse.dto.SaveUser;
import com.fiap.ms_user.jesse.dto.auth.AuthenticationReponse;
import com.fiap.ms_user.jesse.dto.auth.AuthenticationRequest;
import com.fiap.ms_user.jesse.persistence.entity.User;
import com.fiap.ms_user.jesse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.getRole(user.getRole().name());

        String jwt = jwtService.genereteToken(user, generateExtraClaims(user));

        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities",user.getAuthorities());

        return extraClaims;


    }

    public AuthenticationReponse login(AuthenticationRequest authRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

         UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();

         String jwt = jwtService.genereteToken(user, generateExtraClaims((User) user));

         AuthenticationReponse authRsp = new AuthenticationReponse();
         authRsp.setJwt(jwt);
         return authRsp;



    }

    public boolean validateToken(String jwt) {
        try {

            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
