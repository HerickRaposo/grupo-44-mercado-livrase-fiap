package com.fiap.ms_user.jesse.service.impl;

import com.fiap.ms_user.jesse.dto.SaveUser;
import com.fiap.ms_user.jesse.exceptions.InvalidPasswordException;
import com.fiap.ms_user.jesse.persistence.entity.User;
import com.fiap.ms_user.jesse.persistence.repository.UserRepository;
import com.fiap.ms_user.jesse.persistence.util.Role;
import com.fiap.ms_user.jesse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());

        user.setRole(Role.ROLE_CUSTOMER);

        return userRepository.save(user);
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
