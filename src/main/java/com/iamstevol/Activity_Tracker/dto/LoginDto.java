package com.iamstevol.Activity_Tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {

    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Invalid password")
    private String password;
}
