package com.jagdish.SocailSphere.controller;
import com.jagdish.SocailSphere.model.dto.AuthRequest;
import com.jagdish.SocailSphere.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceimpl;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String response = authServiceimpl.login(request) ;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> singUpUser(@RequestBody AuthRequest request){
        String result = authServiceimpl.register(request);

        if ( result.equals("User registered successfully.")){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }


}
