package com.upsaude.service;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a UsuariosSistema.
 *
 * @author UPSaúde
 */
public interface UsuariosSistemaService {

    UsuariosSistemaResponse criar(UsuariosSistemaRequest request);

    UsuariosSistemaResponse obterPorId(UUID id);

    Page<UsuariosSistemaResponse> listar(Pageable pageable);

    UsuariosSistemaResponse atualizar(UUID id, UsuariosSistemaRequest request);

    void excluir(UUID id);
}
