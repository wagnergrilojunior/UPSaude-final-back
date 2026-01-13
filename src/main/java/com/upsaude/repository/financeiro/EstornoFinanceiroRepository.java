package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.EstornoFinanceiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstornoFinanceiroRepository extends JpaRepository<EstornoFinanceiro, UUID> {

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EstornoFinanceiro> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.tenant.id = :tenantId")
    Page<EstornoFinanceiro> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.competencia.id = :competenciaId AND e.tenant.id = :tenantId ORDER BY e.dataEstorno DESC")
    Page<EstornoFinanceiro> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.motivo = :motivo AND e.tenant.id = :tenantId ORDER BY e.dataEstorno DESC")
    Page<EstornoFinanceiro> findByMotivo(@Param("motivo") String motivo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.paciente.id = :pacienteId AND e.tenant.id = :tenantId ORDER BY e.dataEstorno DESC")
    Page<EstornoFinanceiro> findByPaciente(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.atendimento.id = :atendimentoId AND e.tenant.id = :tenantId")
    Page<EstornoFinanceiro> findByAtendimento(@Param("atendimentoId") UUID atendimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.agendamento.id = :agendamentoId AND e.tenant.id = :tenantId")
    Page<EstornoFinanceiro> findByAgendamento(@Param("agendamentoId") UUID agendamentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.dataEstorno BETWEEN :inicio AND :fim AND e.tenant.id = :tenantId ORDER BY e.dataEstorno DESC")
    Page<EstornoFinanceiro> findByDataEstornoBetween(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.competencia.id = :competenciaId AND e.motivo = :motivo AND e.tenant.id = :tenantId ORDER BY e.dataEstorno DESC")
    Page<EstornoFinanceiro> findByCompetenciaAndMotivo(
            @Param("competenciaId") UUID competenciaId,
            @Param("motivo") String motivo,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT e FROM EstornoFinanceiro e WHERE e.paciente.id = :pacienteId AND e.dataEstorno BETWEEN :inicio AND :fim AND e.tenant.id = :tenantId ORDER BY e.dataEstorno DESC")
    Page<EstornoFinanceiro> findByPacienteAndDataEstornoBetween(
            @Param("pacienteId") UUID pacienteId,
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
