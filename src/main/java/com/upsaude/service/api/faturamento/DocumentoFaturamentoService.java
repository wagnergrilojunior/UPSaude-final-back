package com.upsaude.service.api.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DocumentoFaturamentoService {

    DocumentoFaturamentoResponse criar(DocumentoFaturamentoRequest request);

    DocumentoFaturamentoResponse obterPorId(UUID id);

    Page<DocumentoFaturamentoResponse> listar(Pageable pageable);

    DocumentoFaturamentoResponse atualizar(UUID id, DocumentoFaturamentoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

