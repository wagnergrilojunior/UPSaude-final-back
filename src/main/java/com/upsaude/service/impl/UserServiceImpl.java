package com.upsaude.service.impl;

import com.upsaude.api.request.UserRequest;
import com.upsaude.api.response.UserResponse;
import com.upsaude.entity.User;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.UserMapper;
import com.upsaude.repository.UserRepository;
import com.upsaude.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de User.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public UserResponse criar(UserRequest request) {
        log.debug("Criando novo user");

        validarDadosBasicos(request);

        User user = userMapper.fromRequest(request);

        User userSalvo = userRepository.save(user);
        log.info("User criado com sucesso. ID: {}", userSalvo.getId());

        return userMapper.toResponse(userSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "user", key = "#id")
    public UserResponse obterPorId(UUID id) {
        log.debug("Buscando user por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do user é obrigatório");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> listar(Pageable pageable) {
        log.debug("Listando Users paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#id")
    public UserResponse atualizar(UUID id, UserRequest request) {
        log.debug("Atualizando user. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do user é obrigatório");
        }

        validarDadosBasicos(request);

        User userExistente = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + id));

        atualizarDadosUser(userExistente, request);

        User userAtualizado = userRepository.save(userExistente);
        log.info("User atualizado com sucesso. ID: {}", userAtualizado.getId());

        return userMapper.toResponse(userAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo user. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do user é obrigatório");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + id));

        // User é uma entidade de autenticação, usa soft delete via deletedAt
        if (user.getDeletedAt() != null) {
            throw new BadRequestException("User já está deletado");
        }

        user.setDeletedAt(java.time.OffsetDateTime.now());
        userRepository.save(user);
        log.info("User excluído (soft delete) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(UserRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do user são obrigatórios");
        }
    }

    private void atualizarDadosUser(User user, UserRequest request) {
        User userAtualizado = userMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = user.getId();
        java.time.OffsetDateTime createdAtOriginal = user.getCreatedAt();
        java.time.OffsetDateTime deletedAtOriginal = user.getDeletedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(userAtualizado, user);
        
        // Restaura campos de controle
        user.setId(idOriginal);
        user.setCreatedAt(createdAtOriginal);
        user.setDeletedAt(deletedAtOriginal);
    }
}
