package com.jagdish.SocailSphere.service;

import com.jagdish.SocailSphere.model.dto.LoginRequest;
import com.jagdish.SocailSphere.model.dto.SignUpRequest;
import com.jagdish.SocailSphere.model.entity.User;

import java.util.Optional;

public interface UserService {
    String register(SignUpRequest request);
    String login(LoginRequest request);
    Optional<User> getUser(String username);
}
