package com.finalpbl.Service.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finalpbl.Config.UserDetailsImpl;
import com.finalpbl.Dto.Auth.AuthRequest;
import com.finalpbl.Dto.Auth.AuthResponse;
import com.finalpbl.Dto.Auth.RefreshTokenRequest;
import com.finalpbl.Model.RefreshToken;
import com.finalpbl.Repository.UserRepository;
import com.finalpbl.Service.Token.RefreshTokenService;
import com.finalpbl.Utils.JwtUtils;

@Service
public class AuthenticationServiceImpl implements IAuththenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired 
    private RefreshTokenService refreshTokenService;
    
    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        if(authentication.isAuthenticated())
        {
            var user = userRepository.findByEmail(request.getEmail()).filter(u -> !u.getIsDeleted()).orElseThrow(null);
            UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
            RefreshToken refreshToken = refreshTokenService.creaRefreshToken(request.getEmail());
            return AuthResponse.builder()
                .accessToken(jwtUtils.generateToken(userDetailsImpl))
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .build();
        }
        else{
            throw new UsernameNotFoundException("Invalid user request !");
        }
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
         return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateToken(new UserDetailsImpl(user));
                    return AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .id(user.getId())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }
}
