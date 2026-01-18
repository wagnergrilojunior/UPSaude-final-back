package com.upsaude.service.api.support.usuario;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.usuario.UserRequest;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.service.api.sistema.usuario.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaSenhaHandler {

    private final UsuariosSistemaRepository repository;
    private final UserService userService;
    private final SupabaseAuthService supabaseAuthService;
    private final com.upsaude.service.sistema.notificacao.NotificacaoOrchestrator notificacaoOrchestrator;

    public void trocarSenha(UUID id, String novaSenha) {
        log.debug("Trocando senha do usuário: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuário do sistema é obrigatório");
        }

        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new BadRequestException("Nova senha é obrigatória");
        }

        UsuariosSistema usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (usuario.getUser() == null || usuario.getUser().getId() == null) {
            throw new BadRequestException("Usuário não possui User vinculado. Não é possível trocar a senha.");
        }

        UUID userId = usuario.getUser().getId();
        String email = usuario.getUser().getEmail();

        if (email == null || email.trim().isEmpty()) {
            log.warn("Email do User não encontrado. Tentando buscar do Supabase Auth");
            try {
                SupabaseAuthResponse.User supabaseUser = supabaseAuthService.getUserById(userId);
                if (supabaseUser != null && supabaseUser.getEmail() != null) {
                    email = supabaseUser.getEmail();
                } else {
                    throw new BadRequestException("Não foi possível obter o email do usuário no Supabase Auth");
                }
            } catch (Exception e) {
                log.error("Erro ao buscar email do Supabase Auth para userId: {}", userId, e);
                throw new BadRequestException("Não foi possível obter o email do usuário");
            }
        }

        log.debug("Atualizando senha do User no Supabase Auth. UserID: {}", userId);
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .senha(novaSenha)
                .build();

        userService.atualizar(userId, userRequest);
        log.info("Senha trocada com sucesso para o usuário do sistema ID: {}, UserID: {}", id, userId);

        enviarNotificacao(usuario, email);
    }

    private void enviarNotificacao(UsuariosSistema usuario, String email) {
        try {
            String nomeUsuario = null;
            if (usuario.getDadosExibicao() != null) {
                nomeUsuario = usuario.getDadosExibicao().getNomeExibicao();
            } else if (usuario.getDadosIdentificacao() != null) {
                nomeUsuario = usuario.getDadosIdentificacao().getUsername();
            }

            notificacaoOrchestrator.notificarSenhaAlterada(email, nomeUsuario);
        } catch (Exception e) {
            log.warn("Erro ao enviar notificação de senha alterada. Usuário ID: {}", usuario.getId(), e);
        }
    }
}
