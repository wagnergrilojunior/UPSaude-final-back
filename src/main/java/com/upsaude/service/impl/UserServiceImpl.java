package com.upsaude.service.impl;

import com.upsaude.api.request.sistema.UserRequest;
import com.upsaude.api.response.sistema.UserResponse;
import com.upsaude.dto.UserDTO;
import com.upsaude.entity.sistema.User;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.mapper.UserMapper;
import com.upsaude.repository.sistema.UserRepository;
import com.upsaude.service.sistema.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SupabaseAuthService supabaseAuthService;

    @Override
    @Transactional
    public UserResponse criar(UserRequest request) {
        log.debug("Criando novo usuário no Supabase Auth via service_role key");

        String senhaTemporaria = "Temp@123456";

        SupabaseAuthResponse.User supabaseUser = supabaseAuthService.signUp(
                request.getEmail(),
                senhaTemporaria
        );

        log.info("Usuário criado no Supabase Auth com ID: {}", supabaseUser.getId());

        UserResponse response = UserResponse.builder()
                .id(supabaseUser.getId())
                .email(supabaseUser.getEmail())
                .role(supabaseUser.getRole())
                .build();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse obterPorId(UUID id) {
        log.debug("Buscando user por ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de User");
            throw new BadRequestException("ID do usuário é obrigatório");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> listar(Pageable pageable) {
        log.debug("Listando users - página: {}, tamanho: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse atualizar(UUID id, UserRequest request) {
        log.debug("Atualizando usuário no Supabase Auth via API: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de User");
            throw new BadRequestException("ID do usuário é obrigatório");
        }

        if (request == null) {
            log.warn("Request nulo recebido para atualização de User. ID: {}", id);
            throw new BadRequestException("Dados do usuário são obrigatórios");
        }

        String senha = request.getSenha();
        if (senha != null && !senha.trim().isEmpty()) {
            log.info("Atualizando email e senha do usuário: {}", id);
        } else {
            log.info("Atualizando apenas email do usuário (senha permanece inalterada): {}", id);
        }

        SupabaseAuthResponse.User supabaseUser = supabaseAuthService.updateUser(id, request.getEmail(), senha);

        log.info("User atualizado com sucesso. ID: {}", id);

        UserResponse response = UserResponse.builder()
                .id(supabaseUser.getId())
                .email(supabaseUser.getEmail())
                .role(supabaseUser.getRole())
                .build();

        return response;
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.info("Excluindo usuário no Supabase Auth via API: {}", id);

        supabaseAuthService.deleteUser(id);

        log.info("Usuário deletado do Supabase Auth com sucesso: {}", id);
    }
}
