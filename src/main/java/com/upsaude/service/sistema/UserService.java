package com.upsaude.service.sistema;

import com.upsaude.api.request.sistema.UserRequest;
import com.upsaude.api.response.sistema.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserResponse criar(UserRequest request);

    UserResponse obterPorId(UUID id);

    Page<UserResponse> listar(Pageable pageable);

    UserResponse atualizar(UUID id, UserRequest request);

    void excluir(UUID id);
}
