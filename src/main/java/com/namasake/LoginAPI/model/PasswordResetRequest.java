package com.namasake.LoginAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordResetRequest {
    String username;
    String newPassword;
    String currentPassword;
}
