package com.upsaude.repository.clinica.prontuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.prontuario.AlergiaPaciente;

public interface AlergiaPacienteRepository extends JpaRepository<AlergiaPaciente, UUID> {

    @Query("SELECT a FROM AlergiaPaciente a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<AlergiaPaciente> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM AlergiaPaciente a WHERE a.tenant.id = :tenantId")
    Page<AlergiaPaciente> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM AlergiaPaciente a WHERE a.prontuario.id = :prontuarioId AND a.tenant.id = :tenantId ORDER BY a.dataDiagnostico DESC")
    Page<AlergiaPaciente> findByProntuarioIdAndTenantId(
            @Param("prontuarioId") UUID prontuarioId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM AlergiaPaciente a WHERE a.prontuario.id = (SELECT p.id FROM Prontuario p WHERE p.paciente.id = :pacienteId) AND a.tenant.id = :tenantId AND a.clinicalStatus = 'active' AND a.active = true")
    List<AlergiaPaciente> findActiveByPacienteId(@Param("pacienteId") UUID pacienteId,
            @Param("tenantId") UUID tenantId);
}
