package com.jagdish.SocailSphere.service;
import com.jagdish.SocailSphere.model.dto.AuthRequest;
import com.jagdish.SocailSphere.model.dto.AuthResponse;
import com.jagdish.SocailSphere.model.entity.User;
import com.jagdish.SocailSphere.repository.UserRepository;
import com.jagdish.SocailSphere.utilies.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), passwordEncoder.encode(user.getPassword()))) {
            return jwtUtil.generateToken(user.getUsername());
        } else {
            return "Invalid password.";
        }
    }

    @Override
    public String register(AuthRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            return "Username already taken.";
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);

        return "User registered successfully.";
    }

}
