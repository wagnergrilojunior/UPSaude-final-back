package com.upsaude.service.impl.api.sistema.auth;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.sistema.auth.LoginRequest;
import com.upsaude.api.response.sistema.auth.LoginResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaInfoResponse;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.repository.estabelecimento.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.service.api.sistema.auth.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SupabaseAuthService supabaseAuthService;
    private final UsuariosSistemaRepository usuariosSistemaRepository;
    private final UsuarioEstabelecimentoRepository usuarioEstabelecimentoRepository;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String loginIdentifier = request.getEmail();
        log.debug("Iniciando processo de login para: {}", loginIdentifier);

        String emailParaLogin = loginIdentifier;

        if (!loginIdentifier.contains("@")) {
            log.debug("Identificador não contém @, tentando buscar por campo 'username'");
            UsuariosSistema usuarioSistema = usuariosSistemaRepository.findByUsername(loginIdentifier)
                    .orElse(null);

            if (usuarioSistema != null) {

                SupabaseAuthResponse.User user = supabaseAuthService.getUserById(usuarioSistema.getUser() != null ? usuarioSistema.getUser().getId() : null);

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

        SupabaseAuthResponse authResponse = supabaseAuthService.signInWithEmail(
                emailParaLogin,
                request.getPassword()
        );

        UUID userId = authResponse.getUser() != null ? authResponse.getUser().getId() : null;

        UsuarioSistemaInfoResponse usuarioSistemaInfo = null;
        if (userId != null) {
            usuarioSistemaInfo = buscarInformacoesUsuarioSistema(userId);
        }

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

    private UsuarioSistemaInfoResponse buscarInformacoesUsuarioSistema(UUID userId) {
        log.debug("Buscando informações do usuário sistema para userId: {}", userId);

        UsuariosSistema usuarioSistema = usuariosSistemaRepository.findByUserId(userId)
                .orElse(null);

        if (usuarioSistema == null) {
            log.debug("Usuário {} não possui registro em usuarios_sistema", userId);
            return null;
        }

        List<UsuarioEstabelecimento> estabelecimentosVinculados =
                usuarioEstabelecimentoRepository.findByUsuarioUserId(userId);

        List<com.upsaude.api.response.sistema.usuario.EstabelecimentoVinculoSimplificadoResponse> estabelecimentosResponse =
                estabelecimentosVinculados.stream()
                        .map(ue -> {
                            com.upsaude.entity.paciente.Endereco estabelecimentoEndereco = ue.getEstabelecimento().getEndereco();
                            return com.upsaude.api.response.sistema.usuario.EstabelecimentoVinculoSimplificadoResponse.builder()
                                    .id(ue.getId())
                                    .tenantId(ue.getTenant() != null ? ue.getTenant().getId() : null)
                                    .estabelecimentoId(ue.getEstabelecimento().getId())
                                    .estabelecimentoTenantId(ue.getEstabelecimento().getTenant() != null ? ue.getEstabelecimento().getTenant().getId() : null)
                                    .estabelecimentoEnderecoId(estabelecimentoEndereco != null ? estabelecimentoEndereco.getId() : null)
                                    .estabelecimentoEnderecoEstadoId(estabelecimentoEndereco != null && estabelecimentoEndereco.getEstado() != null ? estabelecimentoEndereco.getEstado().getId() : null)
                                    .estabelecimentoEnderecoCidadeId(estabelecimentoEndereco != null && estabelecimentoEndereco.getCidade() != null ? estabelecimentoEndereco.getCidade().getId() : null)
                                    .tipoUsuario(ue.getTipoUsuario())
                                    .build();
                        })
                        .collect(Collectors.toList());

        com.upsaude.api.response.sistema.usuario.TenantSimplificadoResponse tenantResponse = null;
        if (usuarioSistema.getTenant() != null) {
            com.upsaude.entity.paciente.Endereco tenantEndereco = usuarioSistema.getTenant().getEndereco();
            tenantResponse = com.upsaude.api.response.sistema.usuario.TenantSimplificadoResponse.builder()
                    .id(usuarioSistema.getTenant().getId())
                    .nome(usuarioSistema.getTenant().getDadosIdentificacao() != null ? usuarioSistema.getTenant().getDadosIdentificacao().getNome() : null)
                    .enderecoId(tenantEndereco != null ? tenantEndereco.getId() : null)
                    .enderecoEstadoId(tenantEndereco != null && tenantEndereco.getEstado() != null ? tenantEndereco.getEstado().getId() : null)
                    .enderecoCidadeId(tenantEndereco != null && tenantEndereco.getCidade() != null ? tenantEndereco.getCidade().getId() : null)
                    .build();
        }

        UsuarioSistemaInfoResponse response = UsuarioSistemaInfoResponse.builder()
                .id(usuarioSistema.getId())
                .userId(usuarioSistema.getUser() != null ? usuarioSistema.getUser().getId() : null)
                .nomeExibicao(usuarioSistema.getDadosExibicao() != null ? usuarioSistema.getDadosExibicao().getNomeExibicao() : null)
                .username(usuarioSistema.getDadosIdentificacao() != null ? usuarioSistema.getDadosIdentificacao().getUsername() : null)
                .fotoUrl(usuarioSistema.getDadosExibicao() != null ? usuarioSistema.getDadosExibicao().getFotoUrl() : null)
                .adminTenant(usuarioSistema.getConfiguracao() != null ? usuarioSistema.getConfiguracao().getAdminTenant() : null)
                .profissionalSaudeId(usuarioSistema.getProfissionalSaude() != null ? usuarioSistema.getProfissionalSaude().getId() : null)
                .medicoId(usuarioSistema.getMedico() != null ? usuarioSistema.getMedico().getId() : null)
                .pacienteId(usuarioSistema.getPaciente() != null ? usuarioSistema.getPaciente().getId() : null)
                .tenant(tenantResponse)
                .estabelecimentos(estabelecimentosResponse)
                .build();

        log.debug("Informações do usuário sistema carregadas: {} estabelecimentos, tenant: {}",
                estabelecimentosResponse.size(), tenantResponse != null ? tenantResponse.getNome() : null);

        return response;
    }

    @Override
    public boolean verificarAcessoAoSistema(UUID userId) {
        log.debug("Verificando acesso ao sistema para userId: {}", userId);
        if (userId == null) {
            return false;
        }
        boolean temAcesso = usuariosSistemaRepository.findByUserId(userId)
                .map(usuario -> usuario.getAtivo() != null && usuario.getAtivo())
                .orElse(false);
        log.debug("Usuário {} tem acesso ao sistema: {}", userId, temAcesso);
        return temAcesso;
    }
}
