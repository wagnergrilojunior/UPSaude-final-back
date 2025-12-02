package com.upsaude.service;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;

import java.util.UUID;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    
    /**
     * Verifica se um usuário tem acesso ao sistema (se existe UsuariosSistema criado).
     * 
     * @param userId ID do usuário do Supabase (auth.users.id)
     * @return true se o usuário tem acesso ao sistema, false caso contrário
     */
    boolean verificarAcessoAoSistema(UUID userId);
}
