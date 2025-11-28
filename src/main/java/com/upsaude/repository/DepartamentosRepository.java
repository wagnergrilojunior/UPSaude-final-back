package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Departamentos;
import com.upsaude.entity.Tenant;

public interface DepartamentosRepository extends JpaRepository<Departamentos, UUID> {
    
    /**
     * Busca todos os departamentos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de departamentos do estabelecimento
     */
    Page<Departamentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os departamentos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de departamentos do tenant
     */
    Page<Departamentos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os departamentos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de departamentos
     */
    Page<Departamentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
