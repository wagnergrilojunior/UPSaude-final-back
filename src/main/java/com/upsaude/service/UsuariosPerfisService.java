package com.upsaude.service;

import com.upsaude.api.request.UsuariosPerfisRequest;
import com.upsaude.api.response.UsuariosPerfisResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a UsuariosPerfis.
 *
 * @author UPSaúde
 */
public interface UsuariosPerfisService {

    UsuariosPerfisResponse criar(UsuariosPerfisRequest request);

    UsuariosPerfisResponse obterPorId(UUID id);

    Page<UsuariosPerfisResponse> listar(Pageable pageable);

    UsuariosPerfisResponse atualizar(UUID id, UsuariosPerfisRequest request);

    void excluir(UUID id);
}
