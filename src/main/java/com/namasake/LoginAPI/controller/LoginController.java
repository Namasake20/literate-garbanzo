package com.namasake.LoginAPI.controller;

import com.namasake.LoginAPI.model.ResponseMessage;
import com.namasake.LoginAPI.model.User;
import com.namasake.LoginAPI.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody User loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean userExists = loginService.checkUserExists(username);
        if (!userExists) {
            ResponseMessage responseMessage = new ResponseMessage("User does not exist");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }

        boolean isAuthenticated = loginService.authenticate(username, password);
        if (!isAuthenticated) {
            ResponseMessage responseMessage = new ResponseMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }

        // Successful login
        ResponseMessage responseMessage = new ResponseMessage("Login successful");
        return ResponseEntity.ok(responseMessage);
    }
}
