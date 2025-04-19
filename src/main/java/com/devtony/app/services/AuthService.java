package com.devtony.app.services;


import com.devtony.app.dto.login.LoginRequestDto;
import com.devtony.app.dto.login.LoginResponseDto;
import com.devtony.app.security.details.CustomUserDetails;
import com.devtony.app.security.auth.JwtUtil;
import com.devtony.app.services.interfaces.IAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getId().toString(), userDetails.getRoles());
        return new LoginResponseDto(token, userDetails.getUsername(), userDetails.getEmail(), userDetails.getQrCode());
    }
}