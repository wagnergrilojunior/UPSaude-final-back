package com.upsaude.service.api.faturamento;

import com.upsaude.api.request.faturamento.GlosaRequest;
import com.upsaude.api.response.faturamento.GlosaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GlosaService {

    GlosaResponse criar(GlosaRequest request);

    GlosaResponse obterPorId(UUID id);

    Page<GlosaResponse> listar(Pageable pageable);

    GlosaResponse atualizar(UUID id, GlosaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

