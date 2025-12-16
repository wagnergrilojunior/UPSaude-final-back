package com.upsaude.repository;

import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAgendamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    Page<Agendamento> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    Page<Agendamento> findByProfissionalIdOrderByDataHoraAsc(UUID profissionalId, Pageable pageable);

    Page<Agendamento> findByMedicoIdOrderByDataHoraAsc(UUID medicoId, Pageable pageable);

    Page<Agendamento> findByEstabelecimentoIdOrderByDataHoraAsc(UUID estabelecimentoId, Pageable pageable);

    Page<Agendamento> findByStatusOrderByDataHoraAsc(StatusAgendamentoEnum status, Pageable pageable);

    Page<Agendamento> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Agendamento> findByEstabelecimentoIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Agendamento> findByProfissionalIdAndStatusOrderByDataHoraAsc(
            UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable);

    Page<Agendamento> findByDataHoraBetweenOrderByDataHoraAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    List<Agendamento> findByProfissionalIdAndDataHoraBetween(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    List<Agendamento> findByEstabelecimentoIdAndDataHoraBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    List<Agendamento> findByAgendamentoOriginalIdOrderByDataHoraAsc(UUID agendamentoOriginalId);

    Page<Agendamento> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<Agendamento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @EntityGraph(attributePaths = {
        "paciente",
        "profissional",
        "medico",
        "especialidade",
        "convenio",
        "atendimento",
        "agendamentoOriginal",
        "estabelecimento"
    })
    @Query("SELECT a FROM Agendamento a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<Agendamento> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM Agendamento a WHERE a.tenant.id = :tenantId")
    Page<Agendamento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.paciente.id = :pacienteId AND a.tenant.id = :tenantId ORDER BY a.dataHora DESC")
    Page<Agendamento> findByPacienteIdAndTenantIdOrderByDataHoraDesc(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByProfissionalIdAndTenantIdOrderByDataHoraAsc(@Param("profissionalId") UUID profissionalId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.medico.id = :medicoId AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByMedicoIdAndTenantIdOrderByDataHoraAsc(@Param("medicoId") UUID medicoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.estabelecimento.id = :estabelecimentoId AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByEstabelecimentoIdAndTenantIdOrderByDataHoraAsc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.status = :status AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByStatusAndTenantIdOrderByDataHoraAsc(@Param("status") StatusAgendamentoEnum status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId AND a.dataHora BETWEEN :dataInicio AND :dataFim AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByProfissionalIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
            @Param("profissionalId") UUID profissionalId,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.estabelecimento.id = :estabelecimentoId AND a.dataHora BETWEEN :dataInicio AND :dataFim AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId AND a.status = :status AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    Page<Agendamento> findByProfissionalIdAndStatusAndTenantIdOrderByDataHoraAsc(
            @Param("profissionalId") UUID profissionalId,
            @Param("status") StatusAgendamentoEnum status,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId AND a.dataHora BETWEEN :dataInicio AND :dataFim AND a.tenant.id = :tenantId")
    List<Agendamento> findByProfissionalIdAndDataHoraBetweenAndTenantId(
            @Param("profissionalId") UUID profissionalId,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM Agendamento a WHERE a.estabelecimento.id = :estabelecimentoId AND a.dataHora BETWEEN :dataInicio AND :dataFim AND a.tenant.id = :tenantId")
    List<Agendamento> findByEstabelecimentoIdAndDataHoraBetweenAndTenantId(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM Agendamento a WHERE a.agendamentoOriginal.id = :agendamentoOriginalId AND a.tenant.id = :tenantId ORDER BY a.dataHora ASC")
    List<Agendamento> findByAgendamentoOriginalIdAndTenantIdOrderByDataHoraAsc(
            @Param("agendamentoOriginalId") UUID agendamentoOriginalId,
            @Param("tenantId") UUID tenantId);

    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.profissional.id = :profissionalId
          AND a.tenant.id = :tenantId
          AND a.active = true
          AND a.status <> :statusCancelado
          AND a.dataHora < :fim
          AND (a.dataHoraFim IS NULL OR a.dataHoraFim > :inicio)
        """)
    List<Agendamento> findConflitosHorario(
            @Param("profissionalId") UUID profissionalId,
            @Param("tenantId") UUID tenantId,
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("statusCancelado") StatusAgendamentoEnum statusCancelado);
}
