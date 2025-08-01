package com.jagdish.SocailSphere.controller;
import com.jagdish.SocailSphere.model.dto.LoginRequest;
import com.jagdish.SocailSphere.model.dto.SignUpRequest;
import com.jagdish.SocailSphere.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("api/")
public class WithoutAuthontroller {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/singup")
    public ResponseEntity<String> singUpUser(@RequestBody SignUpRequest request){
        String result = userService.register(request);

        if ( result.equals("User registered successfully.")){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> singUpUser(@RequestBody LoginRequest request){
        String result = userService.login(request);

        if ( result.equals("Login successful.")){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
    }
