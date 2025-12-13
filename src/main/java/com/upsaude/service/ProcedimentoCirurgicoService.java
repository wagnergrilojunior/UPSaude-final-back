package com.upsaude.service;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.api.response.ProcedimentoCirurgicoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProcedimentoCirurgicoService {

    ProcedimentoCirurgicoResponse criar(ProcedimentoCirurgicoRequest request);

    ProcedimentoCirurgicoResponse obterPorId(UUID id);

    Page<ProcedimentoCirurgicoResponse> listar(Pageable pageable, UUID cirurgiaId, String codigoProcedimento);

    ProcedimentoCirurgicoResponse atualizar(UUID id, ProcedimentoCirurgicoRequest request);

    void excluir(UUID id);
}

