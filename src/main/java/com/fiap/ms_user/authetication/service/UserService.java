package com.fiap.ms_user.authetication.service;

import com.fiap.ms_user.authetication.dto.SaveUser;
import com.fiap.ms_user.authetication.persistence.entity.User;

import java.util.Optional;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);

     Optional<User> findOneByUsername(String username);
}
