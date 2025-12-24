package com.upsaude.service.api.sistema.multitenancy;

import com.upsaude.api.request.sistema.multitenancy.TenantRequest;
import com.upsaude.api.response.sistema.multitenancy.TenantResponse;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TenantService {

    TenantResponse criar(TenantRequest request);

    TenantResponse obterPorId(UUID id);

    Page<TenantResponse> listar(Pageable pageable);

    TenantResponse atualizar(UUID id, TenantRequest request);

    void excluir(UUID id);

    Tenant obterTenantDoUsuarioAutenticado();

    java.util.UUID validarTenantAtual();
}
