package com.jagdish.SocailSphere.controller;
import com.jagdish.SocailSphere.model.dto.AuthRequest;
import com.jagdish.SocailSphere.model.dto.AuthResponse;
import com.jagdish.SocailSphere.model.dto.LoginRequest;
import com.jagdish.SocailSphere.model.dto.OtpRequest;
import com.jagdish.SocailSphere.service.AuthServiceImpl;
import com.jagdish.SocailSphere.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceimpl;

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest request) {
        otpService.sendOtp(request.getEmail());
        return ResponseEntity.ok("OTP sent successfully to " + request.getEmail());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {
        if (otpService.verifyOtp(request.getEmail(), request.getOtp())) {
            return ResponseEntity.ok("{\"verified\": true}");
        } else {
            return ResponseEntity.status(400).body("{\"verified\": false}");
        }
    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
            try {
                String token = authServiceimpl.login(request);
                return ResponseEntity.ok(new AuthResponse("Login successful", token));
            } catch (UsernameNotFoundException | BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(ex.getMessage(), null));
            }
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
