package com.upsaude.service;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.api.response.IntegracaoGovResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IntegracaoGovService {

    IntegracaoGovResponse criar(IntegracaoGovRequest request);

    IntegracaoGovResponse obterPorId(UUID id);

    IntegracaoGovResponse obterPorPacienteId(UUID pacienteId);

    Page<IntegracaoGovResponse> listar(Pageable pageable);

    IntegracaoGovResponse atualizar(UUID id, IntegracaoGovRequest request);

    void excluir(UUID id);
}
