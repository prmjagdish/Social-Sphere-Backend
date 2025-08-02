package com.jagdish.SocailSphere.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>(); // In-memory (can be Redis/DB in prod)

    public String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtp(String toEmail) {
        String otp = generateOtp();
        otpStorage.put(toEmail, otp); // Save OTP for later verification

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("jaagddissh@gmail.com");
        message.setSubject("Your OTP Code For ");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {
         if (otp.equals(otpStorage.get(email))){
             otpStorage.remove(email);
             return true;
         }
         return false;
    }
}
