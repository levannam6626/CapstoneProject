package com.finalpbl.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    
    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    @NotBlank
    private String role;

    @NotBlank
    private String gender;

    public Boolean validatePassword(UserRequest userRequest)
    {
        if(userRequest.getPassword().equals(userRequest.getConfirmPassword()))
        {
            return true;
        }
        return false;
    }
}
