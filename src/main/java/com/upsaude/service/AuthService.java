package com.upsaude.service;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}

