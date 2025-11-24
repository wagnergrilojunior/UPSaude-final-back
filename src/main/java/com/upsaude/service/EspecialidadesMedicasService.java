package com.upsaude.service;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a EspecialidadesMedicas.
 *
 * @author UPSaúde
 */
public interface EspecialidadesMedicasService {

    EspecialidadesMedicasResponse criar(EspecialidadesMedicasRequest request);

    EspecialidadesMedicasResponse obterPorId(UUID id);

    Page<EspecialidadesMedicasResponse> listar(Pageable pageable);

    EspecialidadesMedicasResponse atualizar(UUID id, EspecialidadesMedicasRequest request);

    void excluir(UUID id);
}
