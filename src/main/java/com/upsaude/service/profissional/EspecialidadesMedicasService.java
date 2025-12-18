package com.upsaude.service.profissional;

import com.upsaude.api.request.profissional.EspecialidadesMedicasRequest;
import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EspecialidadesMedicasService {

    EspecialidadesMedicasResponse criar(EspecialidadesMedicasRequest request);

    EspecialidadesMedicasResponse obterPorId(UUID id);

    Page<EspecialidadesMedicasResponse> listar(Pageable pageable);

    EspecialidadesMedicasResponse atualizar(UUID id, EspecialidadesMedicasRequest request);

    void excluir(UUID id);
}
