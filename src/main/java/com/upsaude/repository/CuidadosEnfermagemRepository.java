package com.upsaude.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoCuidadoEnfermagemEnum;

/**
 * Repositório para operações de banco de dados da entidade CuidadosEnfermagem.
 *
 * @author UPSaúde
 */
public interface CuidadosEnfermagemRepository extends JpaRepository<CuidadosEnfermagem, UUID> {
    
    /**
     * Busca todos os cuidados de enfermagem de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de cuidados de enfermagem
     */
    Page<CuidadosEnfermagem> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os cuidados de enfermagem de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de cuidados de enfermagem
     */
    Page<CuidadosEnfermagem> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca cuidados de enfermagem por paciente.
     *
     * @param pacienteId ID do paciente
     * @param pageable informações de paginação
     * @return página de cuidados de enfermagem do paciente
     */
    Page<CuidadosEnfermagem> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca cuidados de enfermagem por tipo.
     *
     * @param tipo tipo de cuidado
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de cuidados de enfermagem
     */
    Page<CuidadosEnfermagem> findByTipoCuidadoAndEstabelecimentoIdOrderByDataHoraDesc(TipoCuidadoEnfermagemEnum tipo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca cuidados de enfermagem por período.
     *
     * @param dataInicio data inicial
     * @param dataFim data final
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de cuidados de enfermagem
     */
    Page<CuidadosEnfermagem> findByDataHoraBetweenAndEstabelecimentoIdOrderByDataHoraDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca cuidados de enfermagem vinculados a um atendimento.
     *
     * @param atendimentoId ID do atendimento
     * @return lista de cuidados de enfermagem
     */
    List<CuidadosEnfermagem> findByAtendimentoIdOrderByDataHoraAsc(UUID atendimentoId);

    /**
     * Busca cuidados de enfermagem por profissional.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de cuidados de enfermagem
     */
    Page<CuidadosEnfermagem> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);
}

