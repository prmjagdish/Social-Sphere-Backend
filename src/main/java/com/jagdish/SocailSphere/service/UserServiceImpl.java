package com.jagdish.SocailSphere.service;
import com.jagdish.SocailSphere.model.dto.LoginRequest;
import com.jagdish.SocailSphere.model.dto.SignUpRequest;
import com.jagdish.SocailSphere.model.entity.User;
import com.jagdish.SocailSphere.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(SignUpRequest request) {
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

    @Override
    public String login(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if (user.isPresent()) {
            if (passwordEncoder.matches(user.get().getPassword(), passwordEncoder.encode(user.get().getPassword()))) {
                return "Login successful.";
            } else {
                return "Invalid password.";
            }
        }

        return "Invalid username.";
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
