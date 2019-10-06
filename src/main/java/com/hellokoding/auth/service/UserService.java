package com.hellokoding.auth.service;

import com.hellokoding.auth.model.User;

public interface UserService {

    User findByUsername(String username);
}
