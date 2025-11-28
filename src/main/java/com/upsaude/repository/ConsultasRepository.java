package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Consultas;
import com.upsaude.entity.Tenant;

public interface ConsultasRepository extends JpaRepository<Consultas, UUID> {
    
    /**
     * Busca todas as consultas de um estabelecimento, ordenadas por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de consultas do estabelecimento
     */
    Page<Consultas> findByEstabelecimentoIdOrderByInformacoesDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as consultas de um tenant, ordenadas por data decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de consultas do tenant
     */
    Page<Consultas> findByTenantOrderByInformacoesDataConsultaDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as consultas de um estabelecimento e tenant, ordenadas por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de consultas
     */
    Page<Consultas> findByEstabelecimentoIdAndTenantOrderByInformacoesDataConsultaDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
