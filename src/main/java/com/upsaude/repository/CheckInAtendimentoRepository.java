package com.upsaude.repository;

import com.upsaude.entity.CheckInAtendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckInAtendimentoRepository extends JpaRepository<CheckInAtendimento, UUID> {

    @Query("SELECT c FROM CheckInAtendimento c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CheckInAtendimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CheckInAtendimento c WHERE c.tenant.id = :tenantId")
    Page<CheckInAtendimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CheckInAtendimento c WHERE c.agendamento.id = :agendamentoId AND c.tenant.id = :tenantId")
    Optional<CheckInAtendimento> findByAgendamentoIdAndTenantId(@Param("agendamentoId") UUID agendamentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CheckInAtendimento c WHERE c.atendimento.id = :atendimentoId AND c.tenant.id = :tenantId")
    Optional<CheckInAtendimento> findByAtendimentoIdAndTenantId(@Param("atendimentoId") UUID atendimentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CheckInAtendimento c WHERE c.paciente.id = :pacienteId AND c.tenant.id = :tenantId ORDER BY c.dataCheckin DESC")
    Page<CheckInAtendimento> findByPacienteIdAndTenantIdOrderByDataCheckinDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT c FROM CheckInAtendimento c WHERE c.estabelecimento.id = :estabelecimentoId AND c.tenant.id = :tenantId ORDER BY c.dataCheckin DESC")
    Page<CheckInAtendimento> findByEstabelecimentoIdAndTenantIdOrderByDataCheckinDesc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);
}
