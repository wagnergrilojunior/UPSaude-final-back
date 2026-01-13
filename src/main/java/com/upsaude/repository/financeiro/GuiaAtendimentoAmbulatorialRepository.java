package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuiaAtendimentoAmbulatorialRepository extends JpaRepository<GuiaAtendimentoAmbulatorial, UUID> {

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.id = :id AND g.tenant.id = :tenantId")
    Optional<GuiaAtendimentoAmbulatorial> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.tenant.id = :tenantId")
    Page<GuiaAtendimentoAmbulatorial> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.competencia.id = :competenciaId AND g.tenant.id = :tenantId")
    Page<GuiaAtendimentoAmbulatorial> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.agendamento.id = :agendamentoId AND g.tenant.id = :tenantId")
    Optional<GuiaAtendimentoAmbulatorial> findByAgendamento(@Param("agendamentoId") UUID agendamentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.atendimento.id = :atendimentoId AND g.tenant.id = :tenantId")
    Optional<GuiaAtendimentoAmbulatorial> findByAtendimento(@Param("atendimentoId") UUID atendimentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.paciente.id = :pacienteId AND g.tenant.id = :tenantId ORDER BY g.emitidaEm DESC")
    Page<GuiaAtendimentoAmbulatorial> findByPaciente(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.status = :status AND g.tenant.id = :tenantId ORDER BY g.emitidaEm DESC")
    Page<GuiaAtendimentoAmbulatorial> findByStatusAndTenant(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM GuiaAtendimentoAmbulatorial g WHERE g.numero = :numero AND g.tenant.id = :tenantId AND g.competencia.id = :competenciaId")
    Optional<GuiaAtendimentoAmbulatorial> findByNumeroAndTenantAndCompetencia(
            @Param("numero") String numero,
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId);
}
