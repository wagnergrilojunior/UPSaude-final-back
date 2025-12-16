package com.upsaude.service;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PerfisUsuariosService {

    PerfisUsuariosResponse criar(PerfisUsuariosRequest request);

    PerfisUsuariosResponse obterPorId(UUID id);

    Page<PerfisUsuariosResponse> listar(Pageable pageable);

    Page<PerfisUsuariosResponse> listar(Pageable pageable, UUID usuarioId, UUID estabelecimentoId);

    PerfisUsuariosResponse atualizar(UUID id, PerfisUsuariosRequest request);

    void excluir(UUID id);
}
