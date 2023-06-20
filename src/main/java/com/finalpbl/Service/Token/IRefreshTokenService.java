package com.finalpbl.Service.Token;

import java.util.Optional;

import com.finalpbl.Model.RefreshToken;

public interface IRefreshTokenService {
    public RefreshToken creaRefreshToken(String email);
    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken verifyExpiration(RefreshToken token);
}
