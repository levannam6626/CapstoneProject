package com.finalpbl.Service;

import com.finalpbl.Dto.AuthRequest;
import com.finalpbl.Dto.AuthResponse;

public interface IAuththenticationService {
    public AuthResponse authenticate(AuthRequest request);
}
