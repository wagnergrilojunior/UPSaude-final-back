package com.upsaude.service.impl;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;
import com.upsaude.api.response.UsuarioSistemaInfoResponse;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.repository.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SupabaseAuthService supabaseAuthService;
    private final UsuariosSistemaRepository usuariosSistemaRepository;
    private final UsuarioEstabelecimentoRepository usuarioEstabelecimentoRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        String loginIdentifier = request.getEmail(); // Pode ser email ou user
        log.debug("Iniciando processo de login para: {}", loginIdentifier);
        
        // Determinar se é email ou user
        String emailParaLogin = loginIdentifier;
        
        // Se não contém @, pode ser um username (user)
        if (!loginIdentifier.contains("@")) {
            log.debug("Identificador não contém @, tentando buscar por campo 'user'");
            UsuariosSistema usuarioSistema = usuariosSistemaRepository.findByUser(loginIdentifier)
                    .orElse(null);
            
            if (usuarioSistema != null) {
                // Buscar email do usuário no Supabase Auth usando o userId
                SupabaseAuthResponse.User user = supabaseAuthService.getUserById(usuarioSistema.getUserId());
                
                if (user != null && user.getEmail() != null) {
                    emailParaLogin = user.getEmail();
                    log.debug("Email encontrado para user '{}': {}", loginIdentifier, emailParaLogin);
                } else {
                    log.warn("Não foi possível obter email do usuário '{}' no Supabase Auth", loginIdentifier);
                    throw new com.upsaude.exception.UnauthorizedException(
                            "Não foi possível obter email do usuário. Entre em contato com o administrador."
                    );
                }
            } else {
                log.warn("Usuário não encontrado com 'user': {}", loginIdentifier);
                throw new com.upsaude.exception.UnauthorizedException("Credenciais inválidas");
            }
        }
        
        // Chama o serviço do Supabase para autenticar com email
        SupabaseAuthResponse authResponse = supabaseAuthService.signInWithEmail(
                emailParaLogin, 
                request.getPassword()
        );
        
        UUID userId = authResponse.getUser() != null ? authResponse.getUser().getId() : null;
        
        // Busca informações do usuário no sistema
        UsuarioSistemaInfoResponse usuarioSistemaInfo = null;
        if (userId != null) {
            usuarioSistemaInfo = buscarInformacoesUsuarioSistema(userId);
        }
        
        // Converte a resposta do Supabase para o formato esperado pela API
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(authResponse.getAccessToken())
                .refreshToken(authResponse.getRefreshToken())
                .tokenType(authResponse.getTokenType() != null ? authResponse.getTokenType() : "Bearer")
                .expiresIn(authResponse.getExpiresIn())
                .userId(userId)
                .email(authResponse.getUser() != null ? authResponse.getUser().getEmail() : null)
                .userMetadata(authResponse.getUser() != null ? authResponse.getUser().getUserMetadata() : new HashMap<>())
                .appMetadata(authResponse.getUser() != null ? authResponse.getUser().getAppMetadata() : new HashMap<>())
                .role(authResponse.getUser() != null ? authResponse.getUser().getRole() : null)
                .usuarioSistema(usuarioSistemaInfo)
                .build();
        
        log.info("Login realizado com sucesso para: {}", loginIdentifier);
        
        return loginResponse;
    }
    
    /**
     * Busca todas as informações do usuário no sistema, incluindo estabelecimentos.
     */
    private UsuarioSistemaInfoResponse buscarInformacoesUsuarioSistema(UUID userId) {
        log.debug("Buscando informações do usuário sistema para userId: {}", userId);
        
        UsuariosSistema usuarioSistema = usuariosSistemaRepository.findByUserId(userId)
                .orElse(null);
        
        if (usuarioSistema == null) {
            log.debug("Usuário {} não possui registro em usuarios_sistema", userId);
            return null;
        }
        
        // Buscar estabelecimentos vinculados
        List<UsuarioEstabelecimento> estabelecimentosVinculados = 
                usuarioEstabelecimentoRepository.findByUsuarioUserId(userId);
        
        // Montar lista de estabelecimentos vinculados com seus tipos de acesso
        List<UsuarioSistemaInfoResponse.EstabelecimentoVinculoResponse> estabelecimentosResponse = 
                estabelecimentosVinculados.stream()
                        .map(ue -> UsuarioSistemaInfoResponse.EstabelecimentoVinculoResponse.builder()
                                .estabelecimentoId(ue.getEstabelecimento().getId())
                                .estabelecimentoNome(ue.getEstabelecimento().getNome())
                                .tipoUsuario(ue.getTipoUsuario())
                                .build())
                        .collect(Collectors.toList());
        
        // Montar resposta completa
        UsuarioSistemaInfoResponse response = UsuarioSistemaInfoResponse.builder()
                .id(usuarioSistema.getId())
                .createdAt(usuarioSistema.getCreatedAt())
                .updatedAt(usuarioSistema.getUpdatedAt())
                .active(usuarioSistema.getActive())
                .tenantId(usuarioSistema.getTenant() != null ? usuarioSistema.getTenant().getId() : null)
                .tenantNome(usuarioSistema.getTenant() != null ? usuarioSistema.getTenant().getNome() : null)
                .tenantSlug(usuarioSistema.getTenant() != null ? usuarioSistema.getTenant().getSlug() : null)
                .userId(usuarioSistema.getUserId())
                .profissionalSaudeId(usuarioSistema.getProfissionalSaude() != null ? usuarioSistema.getProfissionalSaude().getId() : null)
                .medicoId(usuarioSistema.getMedico() != null ? usuarioSistema.getMedico().getId() : null)
                .pacienteId(usuarioSistema.getPaciente() != null ? usuarioSistema.getPaciente().getId() : null)
                .adminTenant(usuarioSistema.getAdminTenant())
                .nomeExibicao(usuarioSistema.getNomeExibicao())
                .user(usuarioSistema.getUser())
                .fotoUrl(usuarioSistema.getFotoUrl())
                .estabelecimentos(estabelecimentosResponse)
                .build();
        
        log.debug("Informações do usuário sistema carregadas: {} estabelecimentos, tenant: {}", 
                estabelecimentosResponse.size(), response.getTenantNome());
        
        return response;
    }

    @Override
    public boolean verificarAcessoAoSistema(UUID userId) {
        log.debug("Verificando acesso ao sistema para userId: {}", userId);
        if (userId == null) {
            return false;
        }
        boolean temAcesso = usuariosSistemaRepository.findByUserId(userId)
                .map(usuario -> usuario.getActive() != null && usuario.getActive())
                .orElse(false);
        log.debug("Usuário {} tem acesso ao sistema: {}", userId, temAcesso);
        return temAcesso;
    }
}

