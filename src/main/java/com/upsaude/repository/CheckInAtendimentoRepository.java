package com.upsaude.repository;

import com.upsaude.entity.CheckInAtendimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a CheckInAtendimento.
 *
 * @author UPSaúde
 */
@Repository
public interface CheckInAtendimentoRepository extends JpaRepository<CheckInAtendimento, UUID> {

    /**
     * Busca check-in por agendamento.
     */
    Optional<CheckInAtendimento> findByAgendamentoId(UUID agendamentoId);

    /**
     * Busca check-in por atendimento.
     */
    Optional<CheckInAtendimento> findByAtendimentoId(UUID atendimentoId);

    /**
     * Busca todos os check-ins de um paciente, ordenados por data de check-in decrescente.
     */
    Page<CheckInAtendimento> findByPacienteIdOrderByDataCheckinDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todos os check-ins de um estabelecimento, ordenados por data de check-in decrescente.
     */
    Page<CheckInAtendimento> findByEstabelecimentoIdOrderByDataCheckinDesc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os check-ins em um período, ordenados por data de check-in decrescente.
     */
    Page<CheckInAtendimento> findByDataCheckinBetweenOrderByDataCheckinDesc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca check-ins por tipo, ordenados por data de check-in decrescente.
     */
    Page<CheckInAtendimento> findByTipoCheckinOrderByDataCheckinDesc(String tipoCheckin, Pageable pageable);

    /**
     * Busca todos os check-ins de um tenant.
     */
    Page<CheckInAtendimento> findByTenantOrderByDataCheckinDesc(Tenant tenant, Pageable pageable);
}

