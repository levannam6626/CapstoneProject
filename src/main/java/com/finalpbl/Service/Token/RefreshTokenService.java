package com.finalpbl.Service.Token;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Model.RefreshToken;
import com.finalpbl.Repository.RefreshTokenRepository;
import com.finalpbl.Repository.UserRepository;

@Service
public class RefreshTokenService implements IRefreshTokenService{

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken creaRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                    .user(userRepository.findByEmail(email).orElseThrow(null))
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(300000))
                    .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0)
        {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    
    
}
