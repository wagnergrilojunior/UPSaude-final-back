package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.entity.Tenant;

public interface AlergiasPacienteRepository extends JpaRepository<AlergiasPaciente, UUID> {
    
    /**
     * Busca todas as alergias de pacientes de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de alergias do estabelecimento
     */
    Page<AlergiasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as alergias de pacientes de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de alergias do tenant
     */
    Page<AlergiasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as alergias de pacientes de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de alergias
     */
    Page<AlergiasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
