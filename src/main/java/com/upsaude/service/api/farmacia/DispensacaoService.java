package com.upsaude.service.api.farmacia;

import com.upsaude.api.request.farmacia.DispensacaoRequest;
import com.upsaude.api.response.farmacia.DispensacaoResponse;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DispensacaoService {

    Page<ReceitaResponse> listarReceitasPendentes(UUID farmaciaId, Pageable pageable);

    DispensacaoResponse registrarDispensacao(UUID farmaciaId, DispensacaoRequest request);

    Page<DispensacaoResponse> listarDispensacoes(UUID farmaciaId, Pageable pageable);
}

