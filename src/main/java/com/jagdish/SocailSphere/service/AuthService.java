package com.jagdish.SocailSphere.service;
import com.jagdish.SocailSphere.model.dto.AuthRequest;


public interface AuthService {
    String register(AuthRequest request);
    String login(AuthRequest request);

}
