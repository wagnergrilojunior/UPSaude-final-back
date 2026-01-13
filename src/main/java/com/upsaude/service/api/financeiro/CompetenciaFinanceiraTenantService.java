package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraTenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompetenciaFinanceiraTenantService {

    CompetenciaFinanceiraTenantResponse criar(CompetenciaFinanceiraTenantRequest request);

    CompetenciaFinanceiraTenantResponse obterPorId(UUID id);

    Page<CompetenciaFinanceiraTenantResponse> listar(Pageable pageable);

    CompetenciaFinanceiraTenantResponse atualizar(UUID id, CompetenciaFinanceiraTenantRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

