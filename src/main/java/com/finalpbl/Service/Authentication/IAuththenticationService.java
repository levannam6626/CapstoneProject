package com.finalpbl.Service.Authentication;

import com.finalpbl.Dto.Auth.AuthRequest;
import com.finalpbl.Dto.Auth.AuthResponse;

public interface IAuththenticationService {
    public AuthResponse authenticate(AuthRequest request);
}
