package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.RegraClassificacaoContabilRequest;
import com.upsaude.api.response.financeiro.RegraClassificacaoContabilResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RegraClassificacaoContabilService {

    RegraClassificacaoContabilResponse criar(RegraClassificacaoContabilRequest request);

    RegraClassificacaoContabilResponse obterPorId(UUID id);

    Page<RegraClassificacaoContabilResponse> listar(Pageable pageable);

    RegraClassificacaoContabilResponse atualizar(UUID id, RegraClassificacaoContabilRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

