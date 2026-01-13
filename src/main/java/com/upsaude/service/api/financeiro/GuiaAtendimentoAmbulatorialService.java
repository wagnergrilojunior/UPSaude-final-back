package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GuiaAtendimentoAmbulatorialService {

    GuiaAtendimentoAmbulatorialResponse criar(GuiaAtendimentoAmbulatorialRequest request);

    GuiaAtendimentoAmbulatorialResponse obterPorId(UUID id);

    Page<GuiaAtendimentoAmbulatorialResponse> listar(Pageable pageable);

    GuiaAtendimentoAmbulatorialResponse atualizar(UUID id, GuiaAtendimentoAmbulatorialRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

