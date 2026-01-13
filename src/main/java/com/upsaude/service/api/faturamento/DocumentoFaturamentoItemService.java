package com.upsaude.service.api.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoItemRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DocumentoFaturamentoItemService {

    DocumentoFaturamentoItemResponse criar(UUID documentoId, DocumentoFaturamentoItemRequest request);

    DocumentoFaturamentoItemResponse obterPorId(UUID id);

    Page<DocumentoFaturamentoItemResponse> listar(Pageable pageable);

    DocumentoFaturamentoItemResponse atualizar(UUID id, DocumentoFaturamentoItemRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

