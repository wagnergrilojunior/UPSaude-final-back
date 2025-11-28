package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Deficiencias;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados relacionadas a Deficiências.
 *
 * @author UPSaúde
 */
public interface DeficienciasRepository extends JpaRepository<Deficiencias, UUID> {
    
    /**
     * Busca todas as deficiências de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de deficiências do estabelecimento
     */
    Page<Deficiencias> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as deficiências de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de deficiências do tenant
     */
    Page<Deficiencias> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as deficiências de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de deficiências
     */
    Page<Deficiencias> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

