package com.jagdish.SocailSphere.controller;

import com.jagdish.SocailSphere.model.dto.AuthRequest;
import com.jagdish.SocailSphere.model.dto.AuthResponse;
import com.jagdish.SocailSphere.model.dto.LoginRequest;
import com.jagdish.SocailSphere.model.entity.User;
import com.jagdish.SocailSphere.service.UserServiceImpl;
import com.jagdish.SocailSphere.utilies.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        final Optional<User> user = userService.getUser(request.getUsername()) ;
        final String jwt = jwtUtil.generateToken(user.get().getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

}
