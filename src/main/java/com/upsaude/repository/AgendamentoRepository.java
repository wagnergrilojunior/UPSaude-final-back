package com.upsaude.repository;

import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAgendamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Agendamento.
 *
 * @author UPSaúde
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    /**
     * Busca todos os agendamentos de um paciente, ordenados por data/hora decrescente.
     */
    Page<Agendamento> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todos os agendamentos de um profissional, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByProfissionalIdOrderByDataHoraAsc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todos os agendamentos de um médico, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByMedicoIdOrderByDataHoraAsc(UUID medicoId, Pageable pageable);

    /**
     * Busca todos os agendamentos de um estabelecimento, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByEstabelecimentoIdOrderByDataHoraAsc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os agendamentos por status, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByStatusOrderByDataHoraAsc(StatusAgendamentoEnum status, Pageable pageable);

    /**
     * Busca todos os agendamentos de um profissional em um período, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todos os agendamentos de um estabelecimento em um período, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByEstabelecimentoIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todos os agendamentos de um profissional com um status específico.
     */
    Page<Agendamento> findByProfissionalIdAndStatusOrderByDataHoraAsc(
            UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable);

    /**
     * Busca todos os agendamentos em um período, ordenados por data/hora crescente.
     */
    Page<Agendamento> findByDataHoraBetweenOrderByDataHoraAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca agendamentos de um profissional em uma data/hora específica (para verificar conflitos).
     */
    List<Agendamento> findByProfissionalIdAndDataHoraBetween(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    /**
     * Busca agendamentos de um estabelecimento em uma data/hora específica (para verificar conflitos).
     */
    List<Agendamento> findByEstabelecimentoIdAndDataHoraBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    /**
     * Busca reagendamentos de um agendamento original.
     */
    List<Agendamento> findByAgendamentoOriginalIdOrderByDataHoraAsc(UUID agendamentoOriginalId);

    /**
     * Busca todos os agendamentos de um tenant.
     */
    Page<Agendamento> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);
}

