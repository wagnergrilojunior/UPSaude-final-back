package com.upsaude.repository;

import com.upsaude.entity.MedicaoClinica;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a MedicaoClinica.
 *
 * @author UPSaúde
 */
@Repository
public interface MedicaoClinicaRepository extends JpaRepository<MedicaoClinica, UUID> {

    /**
     * Busca todas as medições clínicas de um paciente, ordenadas por data/hora decrescente.
     *
     * @param pacienteId ID do paciente
     * @param pageable informações de paginação
     * @return página de medições clínicas do paciente
     */
    Page<MedicaoClinica> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todas as medições clínicas de um estabelecimento, ordenadas por data/hora decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de medições clínicas do estabelecimento
     */
    Page<MedicaoClinica> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as medições clínicas de um tenant, ordenadas por data/hora decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medições clínicas do tenant
     */
    Page<MedicaoClinica> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as medições clínicas de um estabelecimento e tenant, ordenadas por data/hora decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medições clínicas
     */
    Page<MedicaoClinica> findByEstabelecimentoIdAndTenantOrderByDataHoraDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

