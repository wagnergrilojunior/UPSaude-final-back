package com.upsaude.service.api.support.usuario;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.usuario.UserRequest;
import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.usuario.UserResponse;
import com.upsaude.entity.sistema.auth.User;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.sistema.usuario.UsuariosSistemaMapper;
import com.upsaude.repository.sistema.auth.UserRepository;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.service.api.sistema.usuario.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaCreator {

    private final UsuariosSistemaRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UsuariosSistemaMapper mapper;
    private final UsuariosSistemaValidationService validationService;
    private final UsuariosSistemaRelacionamentosHandler relacionamentosHandler;
    private final UsuariosSistemaVinculosHandler vinculosHandler;
    private final com.upsaude.service.sistema.notificacao.NotificacaoOrchestrator notificacaoOrchestrator;

    public UsuariosSistema criar(UsuariosSistemaRequest request) {
        String email = request.getEmail();
        if (email == null || email.trim().isEmpty()) {
            throw new BadRequestException("Email é obrigatório para criar usuário");
        }

        log.debug("Criando User no Supabase Auth primeiro");
        UserRequest userRequestCriar = UserRequest.builder()
                .email(email)
                .build();

        UserResponse userResponse = userService.criar(userRequestCriar);
        UUID userIdFinal = userResponse.getId();
        log.info("User criado com sucesso no Supabase Auth. ID: {}", userIdFinal);

        String senha = request.getSenha();
        if (senha != null && !senha.trim().isEmpty()) {
            log.debug("Atualizando senha do User recém-criado");
            UserRequest userRequestAtualizar = UserRequest.builder()
                    .email(email)
                    .senha(senha)
                    .build();
            userService.atualizar(userIdFinal, userRequestAtualizar);
            log.info("Senha do User atualizada com sucesso");
        }

        User user = userRepository.findById(userIdFinal)
                .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + userIdFinal));

        String username = null;
        if (request.getDadosIdentificacao() != null) {
            username = request.getDadosIdentificacao().getUsername();
        }

        validationService.validarEmailUnicoParaCriacao(user.getEmail(), userIdFinal);
        validationService.validarUsernameUnicoParaCriacao(username);

        UsuariosSistema entity = mapper.fromRequest(request);
        entity.setAtivo(true);

        relacionamentosHandler.resolverParaCriacao(entity, request, user);

        UsuariosSistema saved = repository.save(entity);
        log.info("UsuariosSistema criado com sucesso. ID: {}", saved.getId());

        List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> estabelecimentosComPapel = request
                .getEstabelecimentos();
        List<UUID> estabelecimentosIds = request.getEstabelecimentosIds();

        if (estabelecimentosComPapel != null && !estabelecimentosComPapel.isEmpty()) {
            vinculosHandler.criarVinculosComPapel(saved, estabelecimentosComPapel);
        } else if (estabelecimentosIds != null && !estabelecimentosIds.isEmpty()) {
            vinculosHandler.criarVinculosEstabelecimentos(saved, estabelecimentosIds);
        }

        enviarNotificacaoCriacao(saved, user);

        return saved;
    }

    private void enviarNotificacaoCriacao(UsuariosSistema usuario, User user) {
        try {
            String nomeUsuario = null;
            if (usuario.getDadosExibicao() != null) {
                nomeUsuario = usuario.getDadosExibicao().getNomeExibicao();
            } else if (usuario.getDadosIdentificacao() != null) {
                nomeUsuario = usuario.getDadosIdentificacao().getUsername();
            }

            notificacaoOrchestrator.notificarUsuarioCriado(usuario, user.getEmail(), nomeUsuario);
        } catch (Exception e) {
            log.warn("Erro ao enviar notificação de usuário criado. Usuário ID: {}", usuario.getId(), e);
        }
    }
}
