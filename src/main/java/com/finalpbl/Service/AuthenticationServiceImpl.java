package com.finalpbl.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.finalpbl.Dto.AuthRequest;
import com.finalpbl.Dto.AuthResponse;
import com.finalpbl.Repository.UserRepository;
import com.finalpbl.Utils.JwtUtils;

@Service
public class AuthenticationServiceImpl implements IAuththenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        var user = userRepository.findByUsername(request.getUsername()).filter(u -> !u.getIsDeleted()).orElseThrow(null);
        var jwtToken = jwtUtils.generateToken(user);
        return AuthResponse.builder()
            .token(jwtToken)
            .build();
      }
}
