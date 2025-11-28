package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Tenant;

public interface EspecialidadesMedicasRepository extends JpaRepository<EspecialidadesMedicas, UUID> {
    
    /**
     * Busca todas as especialidades médicas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de especialidades do estabelecimento
     */
    Page<EspecialidadesMedicas> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as especialidades médicas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de especialidades do tenant
     */
    Page<EspecialidadesMedicas> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as especialidades médicas de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de especialidades
     */
    Page<EspecialidadesMedicas> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
