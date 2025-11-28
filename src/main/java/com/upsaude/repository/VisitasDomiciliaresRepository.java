package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.VisitasDomiciliares;

public interface VisitasDomiciliaresRepository extends JpaRepository<VisitasDomiciliares, UUID> {
    
    /**
     * Busca todas as visitas domiciliares de um estabelecimento, ordenadas por data de visita decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de visitas domiciliares do estabelecimento
     */
    Page<VisitasDomiciliares> findByEstabelecimentoIdOrderByDataVisitaDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as visitas domiciliares de um tenant, ordenadas por data de visita decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de visitas domiciliares do tenant
     */
    Page<VisitasDomiciliares> findByTenantOrderByDataVisitaDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as visitas domiciliares de um estabelecimento e tenant, ordenadas por data de visita decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de visitas domiciliares
     */
    Page<VisitasDomiciliares> findByEstabelecimentoIdAndTenantOrderByDataVisitaDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
