package com.upsaude.service;

import com.upsaude.api.request.UserRequest;
import com.upsaude.api.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a User.
 *
 * @author UPSaúde
 */
public interface UserService {

    UserResponse criar(UserRequest request);

    UserResponse obterPorId(UUID id);

    Page<UserResponse> listar(Pageable pageable);

    UserResponse atualizar(UUID id, UserRequest request);

    void excluir(UUID id);
}
