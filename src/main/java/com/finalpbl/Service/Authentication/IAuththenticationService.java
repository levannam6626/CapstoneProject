package com.finalpbl.Service.Authentication;

import com.finalpbl.Dto.AuthRequest;
import com.finalpbl.Dto.AuthResponse;

public interface IAuththenticationService {
    public AuthResponse authenticate(AuthRequest request);
}
