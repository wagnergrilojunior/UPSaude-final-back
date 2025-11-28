package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Convenio;
import com.upsaude.entity.Tenant;

public interface ConvenioRepository extends JpaRepository<Convenio, UUID> {
    
    /**
     * Busca todos os convênios de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de convênios do estabelecimento
     */
    Page<Convenio> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os convênios de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de convênios do tenant
     */
    Page<Convenio> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os convênios de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de convênios
     */
    Page<Convenio> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
