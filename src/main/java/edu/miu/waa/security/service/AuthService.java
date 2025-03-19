package edu.miu.waa.security.service;

import edu.miu.waa.security.dto.LoginRequest;
import edu.miu.waa.security.dto.RefreshTokenRequest;
import edu.miu.waa.security.dto.LoginResponse;
import org.springframework.http.ResponseCookie;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    public ResponseCookie clearRefreshTokenCookie();
}
