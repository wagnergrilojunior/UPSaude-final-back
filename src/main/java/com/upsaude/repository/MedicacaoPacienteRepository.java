package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados relacionadas a Medicações de Paciente.
 *
 * @author UPSaúde
 */
public interface MedicacaoPacienteRepository extends JpaRepository<MedicacaoPaciente, UUID> {
    
    /**
     * Busca todas as medicações de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de medicações do estabelecimento
     */
    Page<MedicacaoPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as medicações de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medicações do tenant
     */
    Page<MedicacaoPaciente> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as medicações de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medicações
     */
    Page<MedicacaoPaciente> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

