package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.VinculosPapeis;

public interface VinculosPapeisRepository extends JpaRepository<VinculosPapeis, UUID> {
    
    /**
     * Busca todos os vínculos de papéis de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos do estabelecimento
     */
    Page<VinculosPapeis> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os vínculos de papéis de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos do tenant
     */
    Page<VinculosPapeis> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os vínculos de papéis de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculosPapeis> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
