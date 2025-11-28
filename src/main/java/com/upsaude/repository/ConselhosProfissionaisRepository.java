package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.entity.Tenant;

public interface ConselhosProfissionaisRepository extends JpaRepository<ConselhosProfissionais, UUID> {
    
    /**
     * Busca todos os conselhos profissionais de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de conselhos do estabelecimento
     */
    Page<ConselhosProfissionais> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os conselhos profissionais de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de conselhos do tenant
     */
    Page<ConselhosProfissionais> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os conselhos profissionais de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de conselhos
     */
    Page<ConselhosProfissionais> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
