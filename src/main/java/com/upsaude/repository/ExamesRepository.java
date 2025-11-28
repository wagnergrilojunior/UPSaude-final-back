package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Exames;
import com.upsaude.entity.Tenant;

public interface ExamesRepository extends JpaRepository<Exames, UUID> {
    
    /**
     * Busca todos os exames de um estabelecimento, ordenados por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de exames do estabelecimento
     */
    Page<Exames> findByEstabelecimentoIdOrderByDataExameDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os exames de um tenant, ordenados por data decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de exames do tenant
     */
    Page<Exames> findByTenantOrderByDataExameDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os exames de um estabelecimento e tenant, ordenados por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de exames
     */
    Page<Exames> findByEstabelecimentoIdAndTenantOrderByDataExameDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
