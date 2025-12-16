package com.upsaude.service;

import com.upsaude.api.request.FaltaRequest;
import com.upsaude.api.response.FaltaResponse;
import com.upsaude.enums.TipoFaltaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface FaltaService {

    FaltaResponse criar(FaltaRequest request);

    FaltaResponse obterPorId(UUID id);

    Page<FaltaResponse> listar(Pageable pageable,
                              UUID profissionalId,
                              UUID medicoId,
                              UUID estabelecimentoId,
                              TipoFaltaEnum tipoFalta,
                              LocalDate dataInicio,
                              LocalDate dataFim);

    FaltaResponse atualizar(UUID id, FaltaRequest request);

    void excluir(UUID id);
}

