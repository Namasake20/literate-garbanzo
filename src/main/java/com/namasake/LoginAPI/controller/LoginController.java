package com.namasake.LoginAPI.controller;

import com.namasake.LoginAPI.model.HttpResponse;
import com.namasake.LoginAPI.model.PasswordResetRequest;
import com.namasake.LoginAPI.model.ResponseMessage;
import com.namasake.LoginAPI.model.User;
import com.namasake.LoginAPI.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController @RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody User loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean userExists = loginService.checkUserExists(username);
        if (!userExists) {
            ResponseMessage responseMessage = new ResponseMessage("User does not exist");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    HttpResponse.builder()
                            .timestamp(LocalDateTime.now().toString())
                            .status(HttpStatus.UNAUTHORIZED)
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .message("User does not exist")
                            .developerMessage("Error.")
                            .data(Map.of("message","User does not exist"))
                            .build()
            );
        }

        boolean isAuthenticated = loginService.authenticate(username, password);
        if (!isAuthenticated) {
            ResponseMessage responseMessage = new ResponseMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    HttpResponse.builder()
                            .timestamp(LocalDateTime.now().toString())
                            .status(HttpStatus.UNAUTHORIZED)
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .message("Invalid username or password")
                            .developerMessage("Success")
                            .data(Map.of("message","Invalid username or password"))
                            .build()
            );
        }

        // Successful login
        ResponseMessage responseMessage = new ResponseMessage("Login successful");
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Login successful")
                        .developerMessage("Success")
                        .data(Map.of("message","Login successful"))
                        .build()
        );
    }

    @PatchMapping("/change-password")
    public ResponseEntity<HttpResponse> changePassword(@RequestParam("username") String username){
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Password changed successful")
                        .developerMessage("Success")
                        .data(Map.of("credentials",loginService.resetPassword(username)))
                        .build()
        );
    }
    @PatchMapping("/change-own-password")
    public ResponseEntity<HttpResponse> changeOwnPassword(@RequestBody PasswordResetRequest passwordResetRequest){
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Password changed successful")
                        .developerMessage("Success")
                        .data(Map.of("message",loginService.resetOwnPassword(passwordResetRequest)))
                        .build()
        );
    }
}
