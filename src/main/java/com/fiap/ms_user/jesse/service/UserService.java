package com.fiap.ms_user.jesse.service;

import com.fiap.ms_user.jesse.dto.SaveUser;
import com.fiap.ms_user.jesse.persistence.entity.User;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);
}
