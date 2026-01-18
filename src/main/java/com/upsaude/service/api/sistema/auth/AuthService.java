package com.upsaude.service.api.sistema.auth;

import java.util.UUID;

import com.upsaude.api.request.sistema.auth.LoginRequest;
import com.upsaude.api.response.sistema.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(String refreshToken);

    boolean verificarAcessoAoSistema(UUID userId);

}
