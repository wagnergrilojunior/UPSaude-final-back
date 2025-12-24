package com.upsaude.service.api.sistema.usuario;

import com.upsaude.api.request.sistema.usuario.PerfisUsuariosRequest;
import com.upsaude.api.response.sistema.usuario.PerfisUsuariosResponse;
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
