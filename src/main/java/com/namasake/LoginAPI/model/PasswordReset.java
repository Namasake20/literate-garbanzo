package com.namasake.LoginAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordReset {
    private String password;
    private String hashedPassword;
}
