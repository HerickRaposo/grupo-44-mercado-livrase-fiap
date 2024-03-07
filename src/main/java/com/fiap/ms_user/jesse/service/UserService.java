package com.fiap.ms_user.jesse.service;

import com.fiap.ms_user.jesse.dto.SaveUser;
import com.fiap.ms_user.jesse.persistence.entity.User;

import java.util.Optional;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);

     Optional<User> findOneByUsername(String username);
}
