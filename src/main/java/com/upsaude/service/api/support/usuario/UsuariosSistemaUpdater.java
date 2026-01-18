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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaUpdater {

    private final UsuariosSistemaRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UsuariosSistemaMapper mapper;
    private final UsuariosSistemaValidationService validationService;
    private final UsuariosSistemaRelacionamentosHandler relacionamentosHandler;
    private final UsuariosSistemaVinculosHandler vinculosHandler;
    private final com.upsaude.service.sistema.notificacao.NotificacaoOrchestrator notificacaoOrchestrator;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuariosSistema atualizar(UUID id, UsuariosSistemaRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        User userAtualizado = entity.getUser();

        if (userAtualizado == null) {
            throw new BadRequestException("Não é possível atualizar: usuário não possui User vinculado");
        }

        UUID userIdParaAtualizar = userAtualizado.getId();

        boolean senhaAlterada = atualizarUserSeNecessario(request, userAtualizado, userIdParaAtualizar);

        validationService.validarEmailUnicoParaAtualizacao(id, userAtualizado.getEmail(), userIdParaAtualizar);

        String username = null;
        if (request.getDadosIdentificacao() != null) {
            username = request.getDadosIdentificacao().getUsername();
        }
        validationService.validarUsernameUnicoParaAtualizacao(id, username);

        mapper.updateFromRequest(request, entity);
        mapper.updateEmbeddablesFromRequest(request, entity);

        relacionamentosHandler.resolverParaAtualizacao(entity, request);

        if (userAtualizado != null) {
            entity.setUser(userAtualizado);
        }

        UsuariosSistema saved = repository.save(entity);
        log.info("UsuariosSistema atualizado com sucesso. ID: {}", saved.getId());

        atualizarVinculos(saved, request);

        UUID usuariosSistemaId = saved.getId();
        entityManager.clear();
        
        UsuariosSistema recarregado = repository.findById(usuariosSistemaId)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + usuariosSistemaId));

        enviarNotificacoes(recarregado, userAtualizado, request, senhaAlterada);

        return recarregado;
    }

    private boolean atualizarUserSeNecessario(UsuariosSistemaRequest request, User userAtualizado,
            UUID userIdParaAtualizar) {
        boolean precisaAtualizarUser = false;
        boolean senhaAlterada = false;

        String emailRequest = request.getEmail();
        if (emailRequest != null && !emailRequest.trim().isEmpty()) {
            if (!emailRequest.equals(userAtualizado.getEmail())) {
                precisaAtualizarUser = true;
            }
        }

        String senhaRequest = request.getSenha();
        if (senhaRequest != null && !senhaRequest.trim().isEmpty()) {
            precisaAtualizarUser = true;
            senhaAlterada = true;
        }

        if (precisaAtualizarUser) {
            log.debug("Atualizando User no Supabase Auth primeiro");

            UserRequest userRequest = UserRequest.builder()
                    .email(emailRequest)
                    .senha(senhaRequest)
                    .build();

            UserResponse userResponse = userService.atualizar(userIdParaAtualizar, userRequest);
            log.info("User atualizado com sucesso no Supabase Auth. ID: {}", userResponse.getId());

            User userRecarregado = userRepository.findById(userIdParaAtualizar)
                    .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + userIdParaAtualizar));

            userAtualizado.setEmail(userRecarregado.getEmail());
        }

        return senhaAlterada;
    }

    private void atualizarVinculos(UsuariosSistema usuario, UsuariosSistemaRequest request) {
        List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> estabelecimentosComPapel = request
                .getEstabelecimentos();
        List<UUID> estabelecimentosIds = request.getEstabelecimentosIds();

        boolean temEstabelecimentosComPapel = estabelecimentosComPapel != null && !estabelecimentosComPapel.isEmpty();
        boolean temEstabelecimentosIds = estabelecimentosIds != null && !estabelecimentosIds.isEmpty();

        if (temEstabelecimentosComPapel) {
            vinculosHandler.atualizarVinculosComPapel(usuario, estabelecimentosComPapel);
        } else if (temEstabelecimentosIds) {
            vinculosHandler.atualizarVinculosEstabelecimentos(usuario, estabelecimentosIds);
        }
    }

    private void enviarNotificacoes(UsuariosSistema usuario, User user, UsuariosSistemaRequest request,
            boolean senhaAlterada) {
        try {
            String email = null;
            if (user != null) {
                email = user.getEmail();
            }

            String nomeUsuario = null;
            if (usuario.getDadosExibicao() != null) {
                nomeUsuario = usuario.getDadosExibicao().getNomeExibicao();
            } else if (usuario.getDadosIdentificacao() != null) {
                nomeUsuario = usuario.getDadosIdentificacao().getUsername();
            }

            if (senhaAlterada && email != null) {
                notificacaoOrchestrator.notificarSenhaAlterada(email, nomeUsuario);
            }

            boolean temDadosIdentificacao = request.getDadosIdentificacao() != null;
            boolean temDadosExibicao = request.getDadosExibicao() != null;

            if (email != null && (temDadosIdentificacao || temDadosExibicao)) {
                UUID pacienteId = null;
                if (usuario.getPaciente() != null) {
                    pacienteId = usuario.getPaciente().getId();
                }
                notificacaoOrchestrator.notificarDadosPessoaisAtualizados(email, nomeUsuario, pacienteId, null);
            }
        } catch (Exception e) {
            log.warn("Erro ao enviar notificações de atualização. Usuário ID: {}", usuario.getId(), e);
        }
    }
}
