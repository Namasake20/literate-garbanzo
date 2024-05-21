package com.namasake.LoginAPI.service;

import com.namasake.LoginAPI.model.PasswordReset;
import com.namasake.LoginAPI.model.PasswordResetRequest;
import com.namasake.LoginAPI.model.User;
import com.namasake.LoginAPI.repo.UserRepository;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getStatus() == 0) {
            return false;
        }

        // Verify the password using BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }

    public boolean checkUserExists(String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }
    public PasswordReset resetPassword(String username){
        User user = userRepository.findByUsername(username);
        if (user == null || user.getStatus() == 0){
            throw new RuntimeException("user not found.");
        }
        //update password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String generatedPassword = generatePassword();
        String hashedPassword =  encoder.encode(generatedPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return new PasswordReset(generatedPassword,hashedPassword);
    }
    public String resetOwnPassword(PasswordResetRequest passwordResetRequest){
        User user = userRepository.findByUsername(passwordResetRequest.getUsername());
        if (user == null){
            throw new RuntimeException("Account with username "+passwordResetRequest.getUsername()+" not found.");
        }
        //compare existing password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passwordMatches = encoder.matches(passwordResetRequest.getCurrentPassword(),user.getPassword());
        if (!passwordMatches){
            throw new RuntimeException("Entered password does not match with current password");
        }
        String hashedPassword = encoder.encode(passwordResetRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "Password updated successfully";
    }
    private String generatePassword(){
        String NUMBER = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(NUMBER.length());
            sb.append(NUMBER.charAt(randomIndex));
        }
        return sb.toString();
    }

}
