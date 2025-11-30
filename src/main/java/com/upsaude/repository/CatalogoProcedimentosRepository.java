package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.entity.Tenant;

public interface CatalogoProcedimentosRepository extends JpaRepository<CatalogoProcedimentos, UUID> {
    
    /**
     * Busca todos os procedimentos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de procedimentos do estabelecimento
     */
    Page<CatalogoProcedimentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os procedimentos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos do tenant
     */
    Page<CatalogoProcedimentos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os procedimentos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos
     */
    Page<CatalogoProcedimentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

