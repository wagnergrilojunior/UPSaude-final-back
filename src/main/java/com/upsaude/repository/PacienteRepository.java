package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    
    /**
     * Busca um paciente por CPF.
     *
     * @param cpf CPF do paciente
     * @return Optional contendo o paciente encontrado, se existir
     */
    Optional<Paciente> findByCpf(String cpf);

    /**
     * Busca todos os pacientes de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de pacientes do estabelecimento
     */
    Page<Paciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os pacientes de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de pacientes do tenant
     */
    Page<Paciente> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os pacientes de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de pacientes
     */
    Page<Paciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
