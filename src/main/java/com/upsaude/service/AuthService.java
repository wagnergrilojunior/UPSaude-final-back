package com.upsaude.service;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;

import java.util.UUID;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    boolean verificarAcessoAoSistema(UUID userId);
}
