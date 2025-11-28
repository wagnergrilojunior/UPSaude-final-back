package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Permissoes;
import com.upsaude.entity.Tenant;

public interface PermissoesRepository extends JpaRepository<Permissoes, UUID> {
    
    /**
     * Busca todas as permissões de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de permissões do estabelecimento
     */
    Page<Permissoes> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as permissões de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de permissões do tenant
     */
    Page<Permissoes> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as permissões de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de permissões
     */
    Page<Permissoes> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
