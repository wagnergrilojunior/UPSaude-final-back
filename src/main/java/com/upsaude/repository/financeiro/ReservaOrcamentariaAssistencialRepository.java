package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservaOrcamentariaAssistencialRepository extends JpaRepository<ReservaOrcamentariaAssistencial, UUID> {

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<ReservaOrcamentariaAssistencial> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.tenant.id = :tenantId")
    Page<ReservaOrcamentariaAssistencial> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.competencia.id = :competenciaId AND r.tenant.id = :tenantId")
    Page<ReservaOrcamentariaAssistencial> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.agendamento.id = :agendamentoId AND r.tenant.id = :tenantId")
    Optional<ReservaOrcamentariaAssistencial> findByAgendamento(@Param("agendamentoId") UUID agendamentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.guiaAmbulatorial.id = :guiaId AND r.tenant.id = :tenantId")
    Optional<ReservaOrcamentariaAssistencial> findByGuiaAmbulatorial(@Param("guiaId") UUID guiaId, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.status = :status AND r.tenant.id = :tenantId")
    Page<ReservaOrcamentariaAssistencial> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.prestadorTipo = :prestadorTipo AND r.prestadorId = :prestadorId AND r.tenant.id = :tenantId")
    Page<ReservaOrcamentariaAssistencial> findByPrestador(
            @Param("prestadorTipo") String prestadorTipo,
            @Param("prestadorId") UUID prestadorId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.competencia.id = :competenciaId AND r.status = :status AND r.tenant.id = :tenantId")
    Page<ReservaOrcamentariaAssistencial> findByCompetenciaAndStatus(
            @Param("competenciaId") UUID competenciaId,
            @Param("status") String status,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT r FROM ReservaOrcamentariaAssistencial r WHERE r.grupoReserva = :grupoReserva AND r.tenant.id = :tenantId")
    List<ReservaOrcamentariaAssistencial> findByGrupoReserva(@Param("grupoReserva") UUID grupoReserva, @Param("tenantId") UUID tenantId);
}
