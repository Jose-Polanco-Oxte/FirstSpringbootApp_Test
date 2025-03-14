package com.devtony.app.services.interfaces;

import com.devtony.app.dto.login.LoginRequestDto;
import com.devtony.app.dto.login.LoginResponseDto;

public interface IAuthService {
    public LoginResponseDto login(LoginRequestDto request);
}
