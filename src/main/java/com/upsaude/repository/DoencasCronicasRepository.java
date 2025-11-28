package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DoencasCronicas;
import com.upsaude.entity.Tenant;

public interface DoencasCronicasRepository extends JpaRepository<DoencasCronicas, UUID> {
    
    /**
     * Busca todas as doenças crônicas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de doenças crônicas do estabelecimento
     */
    Page<DoencasCronicas> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as doenças crônicas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de doenças crônicas do tenant
     */
    Page<DoencasCronicas> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as doenças crônicas de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de doenças crônicas
     */
    Page<DoencasCronicas> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
