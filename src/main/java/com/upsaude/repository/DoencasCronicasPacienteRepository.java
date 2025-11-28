package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DoencasCronicasPaciente;
import com.upsaude.entity.Tenant;

public interface DoencasCronicasPacienteRepository extends JpaRepository<DoencasCronicasPaciente, UUID> {
    
    /**
     * Busca todas as doenças crônicas de pacientes de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de doenças crônicas do estabelecimento
     */
    Page<DoencasCronicasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as doenças crônicas de pacientes de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de doenças crônicas do tenant
     */
    Page<DoencasCronicasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as doenças crônicas de pacientes de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de doenças crônicas
     */
    Page<DoencasCronicasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
