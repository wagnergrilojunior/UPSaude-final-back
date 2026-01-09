package com.upsaude.service.api.farmacia;

import com.upsaude.api.request.farmacia.ReceitaRequest;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReceitaService {

    ReceitaResponse criar(ReceitaRequest request);

    ReceitaResponse obterPorId(UUID id);

    Page<ReceitaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);
}
