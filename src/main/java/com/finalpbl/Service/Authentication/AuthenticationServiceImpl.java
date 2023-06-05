package com.finalpbl.Service.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.finalpbl.Config.UserDetailsImpl;
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
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getEmail()).filter(u -> !u.getIsDeleted()).orElseThrow(null);
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
        var jwtToken = jwtUtils.generateToken(userDetailsImpl);
        return AuthResponse.builder()
            .token(jwtToken)
            .id(user.getId())
            .build();
      }
}
