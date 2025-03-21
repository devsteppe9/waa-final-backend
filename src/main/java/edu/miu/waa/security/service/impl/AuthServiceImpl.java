package edu.miu.waa.security.service.impl;

import edu.miu.waa.dto.response.UserOutDto;
import edu.miu.waa.security.dto.LoginRequest;
import edu.miu.waa.security.dto.RefreshTokenRequest;
import edu.miu.waa.security.dto.LoginResponse;
import edu.miu.waa.service.UserService;
import edu.miu.waa.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseCookie;
import edu.miu.waa.security.service.AuthService;
import edu.miu.waa.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication result = null;
        try {
            result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        final UserDetails userDetails = userService.loadUserByUsername(result.getName());

        final UserOutDto userOut = modelMapper.map(userService.findByUsername(loginRequest.getUsername()), UserOutDto.class);
        final String accessToken = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());

        return new LoginResponse(accessToken, refreshToken, userOut);
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (jwtUtil.validateToken(refreshTokenRequest.getRefreshToken()) && jwtUtil.isRefreshToken(refreshTokenRequest.getRefreshToken())) { //Added check for refresh token type
            if (jwtUtil.isTokenExpired(refreshTokenRequest.getAccessToken())) {
                final String accessToken = jwtUtil.doGenerateToken(jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken()));
                return new LoginResponse(accessToken, refreshTokenRequest.getRefreshToken());
            } else {
                log.info("Access token is not expired.");
                return new LoginResponse(refreshTokenRequest.getAccessToken(), refreshTokenRequest.getRefreshToken()); //Return the same tokens if access token is still valid.
            }
        } else {
            log.warn("Invalid refresh token.");
            return new LoginResponse(); // Or throw an exception
        }
    }

    @Override
    public ResponseCookie clearRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
    }
}
