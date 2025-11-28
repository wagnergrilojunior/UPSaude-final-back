package com.upsaude.repository;

import com.upsaude.entity.ResponsavelLegal;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResponsavelLegalRepository extends JpaRepository<ResponsavelLegal, UUID> {
    
    Optional<ResponsavelLegal> findByPacienteId(UUID pacienteId);

    /**
     * Busca todos os responsáveis legais de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de responsáveis legais do estabelecimento
     */
    Page<ResponsavelLegal> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os responsáveis legais de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de responsáveis legais do tenant
     */
    Page<ResponsavelLegal> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os responsáveis legais de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de responsáveis legais
     */
    Page<ResponsavelLegal> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

