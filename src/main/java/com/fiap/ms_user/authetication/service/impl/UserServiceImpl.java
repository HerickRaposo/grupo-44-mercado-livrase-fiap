package com.fiap.ms_user.authetication.service.impl;

import com.fiap.ms_user.authetication.dto.SaveUser;
import com.fiap.ms_user.authetication.exceptions.DataIntegrityViolationException;
import com.fiap.ms_user.authetication.exceptions.InvalidPasswordException;
import com.fiap.ms_user.authetication.persistence.entity.User;
import com.fiap.ms_user.authetication.persistence.repository.UserRepository;
import com.fiap.ms_user.authetication.persistence.util.Role;
import com.fiap.ms_user.authetication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerOneCustomer(SaveUser newUser) {

        validatePassword(newUser);

        User user = new User();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        user.setUsername(newUser.getUsername_or_email());
        user.setName(newUser.getName());

        user.setRole(Role.ROLE_CUSTOMER);
        User saved = null;
        try {
            saved = userRepository.save(user);

        }catch (RuntimeException e){
            System.out.println(e);
            throw new DataIntegrityViolationException("Usuário repetido na base " + e.getMessage());

        }
        return saved;
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser dto) {

        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }

        if(!dto.getPassword().equals(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }
    }


}
