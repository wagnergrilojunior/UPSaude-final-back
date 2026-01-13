package com.upsaude.service.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.AtendimentoProcedimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoProcedimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AtendimentoProcedimentoService {

    AtendimentoProcedimentoResponse criar(AtendimentoProcedimentoRequest request);

    AtendimentoProcedimentoResponse obterPorId(UUID id);

    Page<AtendimentoProcedimentoResponse> listar(Pageable pageable);

    AtendimentoProcedimentoResponse atualizar(UUID id, AtendimentoProcedimentoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

