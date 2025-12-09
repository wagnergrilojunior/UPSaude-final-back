package com.upsaude.service;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Tenant.
 *
 * @author UPSaúde
 */
public interface TenantService {

    TenantResponse criar(TenantRequest request);

    TenantResponse obterPorId(UUID id);

    Page<TenantResponse> listar(Pageable pageable);

    TenantResponse atualizar(UUID id, TenantRequest request);

    void excluir(UUID id);

    /**
     * Obtém o tenant do usuário autenticado.
     * 
     * @return Tenant do usuário autenticado ou null se não encontrado
     */
    Tenant obterTenantDoUsuarioAutenticado();
}
