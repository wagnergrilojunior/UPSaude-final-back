package com.upsaude.service.api.support.usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.entity.sistema.auth.User;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.sistema.auth.UserRepository;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaValidationService {

    private final UsuariosSistemaRepository usuariosSistemaRepository;
    private final UserRepository userRepository;

    public void validarEmailUnicoParaCriacao(String email, UUID userId) {
        validarEmailUnico(null, email, userId);
    }

    public void validarEmailUnicoParaAtualizacao(UUID usuariosSistemaId, String email, UUID userId) {
        validarEmailUnico(usuariosSistemaId, email, userId);
    }

    public void validarUsernameUnicoParaCriacao(String username) {
        validarUsernameUnico(null, username);
    }

    public void validarUsernameUnicoParaAtualizacao(UUID usuariosSistemaId, String username) {
        validarUsernameUnico(usuariosSistemaId, username);
    }

    private void validarEmailUnico(UUID usuariosSistemaId, String email, UUID userId) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<User> userExistente = userRepository.findByEmail(email);

        if (userExistente.isPresent()) {
            User userEncontrado = userExistente.get();

            if (usuariosSistemaId != null) {
                UsuariosSistema usuariosSistemaExistente = usuariosSistemaRepository.findById(usuariosSistemaId)
                        .orElseThrow(() -> new BadRequestException(
                                "UsuariosSistema não encontrado com ID: " + usuariosSistemaId));

                if (usuariosSistemaExistente.getUser() == null
                        || !userEncontrado.getId().equals(usuariosSistemaExistente.getUser().getId())) {
                    throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
                }
            } else {

                if (userId != null) {

                    Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository.findByUserId(userId);
                    if (usuariosSistemaExistente.isPresent()) {
                        throw new BadRequestException(
                                "Já existe um usuário do sistema cadastrado com este userId: " + userId);
                    }

                    if (!userEncontrado.getId().equals(userId)) {
                        throw new BadRequestException("O email informado pertence a outro usuário. Email: " + email);
                    }

                    log.debug(
                            "Email {} existe em auth.users e corresponde ao userId {}, mas ainda não existe em usuarios_sistema. Permitindo cadastro.",
                            email, userId);
                } else {

                    Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository
                            .findByUserId(userEncontrado.getId());
                    if (usuariosSistemaExistente.isPresent()) {
                        throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
                    }

                    log.debug("Email {} existe em auth.users mas não existe em usuarios_sistema. Permitindo cadastro.",
                            email);
                }
            }
        }
    }

    private void validarUsernameUnico(UUID usuariosSistemaId, String username) {
        if (!StringUtils.hasText(username)) {
            return;
        }

        Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository.findByUsername(username);

        if (usuariosSistemaExistente.isPresent()) {
            UsuariosSistema usuarioEncontrado = usuariosSistemaExistente.get();

            if (usuariosSistemaId != null && !usuarioEncontrado.getId().equals(usuariosSistemaId)) {
                throw new BadRequestException("Já existe um usuário cadastrado com o username: " + username);
            }

            if (usuariosSistemaId == null) {
                throw new BadRequestException("Já existe um usuário cadastrado com o username: " + username);
            }
        }
    }
}
