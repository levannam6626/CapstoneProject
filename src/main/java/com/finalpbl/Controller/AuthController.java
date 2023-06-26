package com.finalpbl.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Dto.Auth.AuthRequest;
import com.finalpbl.Dto.Auth.AuthResponse;
import com.finalpbl.Dto.Auth.RefreshTokenRequest;
import com.finalpbl.Service.Authentication.AuthenticationServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        System.out.println(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    
}
