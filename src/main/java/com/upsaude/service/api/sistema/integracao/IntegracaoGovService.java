package com.upsaude.service.api.sistema.integracao;

import com.upsaude.api.request.sistema.integracao.IntegracaoGovRequest;
import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
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

    void inativar(UUID id);
}
