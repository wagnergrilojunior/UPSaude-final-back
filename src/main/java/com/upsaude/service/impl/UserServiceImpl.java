package com.upsaude.service.impl;

import com.upsaude.api.request.UserRequest;
import com.upsaude.api.response.UserResponse;
import com.upsaude.dto.UserDTO;
import com.upsaude.entity.User;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.mapper.UserMapper;
import com.upsaude.repository.UserRepository;
import com.upsaude.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementação do serviço para operações CRUD de User.
 *
 * @author UPSaúde
 */
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
        log.info("Criando novo usuário no Supabase Auth via service_role key");
        
        // Senha padrão temporária (deve ser alterada pelo usuário no primeiro login)
        String senhaTemporaria = "Temp@123456";
        
        // Criar usuário no Supabase Auth via API Admin
        SupabaseAuthResponse.User supabaseUser = supabaseAuthService.signUp(
                request.getEmail(), 
                senhaTemporaria
        );
        
        log.info("Usuário criado no Supabase Auth com ID: {}", supabaseUser.getId());
        
        // Montar response com os dados do Supabase
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
        log.info("Buscando user por ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User não encontrado com ID: " + id));
        
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> listar(Pageable pageable) {
        log.info("Listando users - página: {}, tamanho: {}", pageable.getPageNumber(), pageable.getPageSize());
        
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse atualizar(UUID id, UserRequest request) {
        log.info("Atualizando usuário no Supabase Auth via API: {}", id);
        
        // Atualizar usuário via API do Supabase Auth (service_role)
        SupabaseAuthResponse.User supabaseUser = supabaseAuthService.updateUser(id, request.getEmail());
        
        log.info("Usuário atualizado no Supabase Auth com sucesso: {}", id);
        
        // Montar response com os dados atualizados
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
        
        // Deletar usuário via API do Supabase Auth (service_role)
        supabaseAuthService.deleteUser(id);
        
        log.info("Usuário deletado do Supabase Auth com sucesso: {}", id);
    }
}

