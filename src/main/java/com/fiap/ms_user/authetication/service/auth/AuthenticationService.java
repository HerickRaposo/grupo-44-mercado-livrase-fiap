package com.fiap.ms_user.authetication.service.auth;


import com.fiap.ms_user.authetication.dto.RegisteredUser;
import com.fiap.ms_user.authetication.dto.SaveUser;
import com.fiap.ms_user.authetication.dto.auth.AuthenticationReponse;
import com.fiap.ms_user.authetication.dto.auth.AuthenticationRequest;
import com.fiap.ms_user.authetication.exceptions.DataIntegrityViolationException;
import com.fiap.ms_user.authetication.persistence.entity.User;
import com.fiap.ms_user.authetication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {




    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {
        return getGenericUser(userService.registerOneCustomer(newUser));

    }

    private RegisteredUser getGenericUser(User userService) {
        RegisteredUser userDto = new RegisteredUser();
        try {

            return getRegisteredUser(userService, userDto);
        } catch (DataIntegrityViolationException e) {
//            System.out.println(e);
            throw new DataIntegrityViolationException("Usuário já cadastrado na base ");

        }
    }

    public RegisteredUser registerOneROLE_ADMINISTRATOR(SaveUser newUser) {
        return getGenericUser(userService.registerOneROLE_ADMINISTRATOR(newUser));

    }

    public RegisteredUser registerOneOneRoleAssitentAdministrator(SaveUser newUser) {
        return getGenericUser(userService.registerOneRoleAssitentAdministrator(newUser));

    }

    private RegisteredUser getRegisteredUser(User userService, RegisteredUser userDto) {
        User user = userService;

//            RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().name());

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



        try{


        authenticationManager.authenticate(authentication);

        Optional<UserDetails> userOptional = Optional.of(userService.findOneByUsername(authRequest.getUsername()).get());
            if (userOptional.isPresent()) {

                UserDetails user = userOptional.get();

                String jwt = jwtService.genereteToken(user, generateExtraClaims((User) user));

                AuthenticationReponse authRsp = new AuthenticationReponse();
                authRsp.setJwt(jwt);
                return authRsp;
            }else{
                throw new UsernameNotFoundException("Usuário não encontrado");
            }
        }catch (InternalAuthenticationServiceException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            throw new InternalAuthenticationServiceException("Erro ao autenticar: Usuário não encontrado!", e);
        }
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
