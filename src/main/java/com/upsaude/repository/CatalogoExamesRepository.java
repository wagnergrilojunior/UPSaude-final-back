package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Tenant;

public interface CatalogoExamesRepository extends JpaRepository<CatalogoExames, UUID> {
    
    /**
     * Busca todos os exames de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de exames do estabelecimento
     */
    Page<CatalogoExames> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os exames de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de exames do tenant
     */
    Page<CatalogoExames> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os exames de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de exames
     */
    Page<CatalogoExames> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

