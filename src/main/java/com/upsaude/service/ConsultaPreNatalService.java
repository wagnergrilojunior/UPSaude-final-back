package com.upsaude.service;

import com.upsaude.api.request.ConsultaPreNatalRequest;
import com.upsaude.api.response.ConsultaPreNatalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ConsultaPreNatalService {

    ConsultaPreNatalResponse criar(ConsultaPreNatalRequest request);

    ConsultaPreNatalResponse obterPorId(UUID id);

    Page<ConsultaPreNatalResponse> listar(Pageable pageable, UUID preNatalId, UUID estabelecimentoId, OffsetDateTime inicio, OffsetDateTime fim);

    long countPorPreNatalId(UUID preNatalId);

    ConsultaPreNatalResponse atualizar(UUID id, ConsultaPreNatalRequest request);

    void excluir(UUID id);
}

