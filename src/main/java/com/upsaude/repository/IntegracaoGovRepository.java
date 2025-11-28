package com.upsaude.repository;

import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IntegracaoGovRepository extends JpaRepository<IntegracaoGov, UUID> {
    
    Optional<IntegracaoGov> findByPacienteId(UUID pacienteId);
    
    Optional<IntegracaoGov> findByUuidRnds(UUID uuidRnds);

    /**
     * Busca todas as integrações governamentais de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de integrações do estabelecimento
     */
    Page<IntegracaoGov> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as integrações governamentais de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de integrações do tenant
     */
    Page<IntegracaoGov> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as integrações governamentais de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de integrações
     */
    Page<IntegracaoGov> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

