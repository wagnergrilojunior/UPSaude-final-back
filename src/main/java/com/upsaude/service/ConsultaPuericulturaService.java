package com.upsaude.service;

import com.upsaude.api.request.ConsultaPuericulturaRequest;
import com.upsaude.api.response.ConsultaPuericulturaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConsultaPuericulturaService {

    ConsultaPuericulturaResponse criar(ConsultaPuericulturaRequest request);

    ConsultaPuericulturaResponse obterPorId(UUID id);

    Page<ConsultaPuericulturaResponse> listar(Pageable pageable, UUID puericulturaId, UUID estabelecimentoId);

    ConsultaPuericulturaResponse atualizar(UUID id, ConsultaPuericulturaRequest request);

    void excluir(UUID id);
}

