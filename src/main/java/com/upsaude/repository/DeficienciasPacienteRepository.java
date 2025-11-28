package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DeficienciasPaciente;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados relacionadas a Deficiências de Paciente.
 *
 * @author UPSaúde
 */
public interface DeficienciasPacienteRepository extends JpaRepository<DeficienciasPaciente, UUID> {
    
    /**
     * Busca todas as deficiências de pacientes de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de deficiências do estabelecimento
     */
    Page<DeficienciasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as deficiências de pacientes de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de deficiências do tenant
     */
    Page<DeficienciasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as deficiências de pacientes de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de deficiências
     */
    Page<DeficienciasPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

