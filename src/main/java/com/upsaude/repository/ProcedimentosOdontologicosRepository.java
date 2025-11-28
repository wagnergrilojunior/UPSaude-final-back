package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.entity.Tenant;

public interface ProcedimentosOdontologicosRepository extends JpaRepository<ProcedimentosOdontologicos, UUID> {
    
    /**
     * Busca todos os procedimentos odontológicos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de procedimentos do estabelecimento
     */
    Page<ProcedimentosOdontologicos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os procedimentos odontológicos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos do tenant
     */
    Page<ProcedimentosOdontologicos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os procedimentos odontológicos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos
     */
    Page<ProcedimentosOdontologicos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
