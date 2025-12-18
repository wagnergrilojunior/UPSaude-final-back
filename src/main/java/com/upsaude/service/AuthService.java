package com.upsaude.service;

import java.util.UUID;

import com.upsaude.api.request.sistema.LoginRequest;
import com.upsaude.api.response.sistema.LoginResponse;

public interface AuthService {
	
    LoginResponse login(LoginRequest request);
    boolean verificarAcessoAoSistema(UUID userId);
    
}
