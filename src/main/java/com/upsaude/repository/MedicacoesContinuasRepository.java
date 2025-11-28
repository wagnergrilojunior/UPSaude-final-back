package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacoesContinuas;
import com.upsaude.entity.Tenant;

public interface MedicacoesContinuasRepository extends JpaRepository<MedicacoesContinuas, UUID> {
    
    /**
     * Busca todas as medicações contínuas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de medicações contínuas do estabelecimento
     */
    Page<MedicacoesContinuas> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as medicações contínuas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medicações contínuas do tenant
     */
    Page<MedicacoesContinuas> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as medicações contínuas de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medicações contínuas
     */
    Page<MedicacoesContinuas> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
