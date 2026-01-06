package com.upsaude.repository.clinica.prontuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.prontuario.DoencaPaciente;

public interface DoencaPacienteRepository extends JpaRepository<DoencaPaciente, UUID> {

    @Query("SELECT d FROM DoencaPaciente d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<DoencaPaciente> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DoencaPaciente d WHERE d.tenant.id = :tenantId")
    Page<DoencaPaciente> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DoencaPaciente d WHERE d.prontuario.id = :prontuarioId AND d.tenant.id = :tenantId ORDER BY d.dataDiagnostico DESC")
    Page<DoencaPaciente> findByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT d FROM DoencaPaciente d WHERE d.prontuario.id = :prontuarioId AND d.tenant.id = :tenantId")
    List<DoencaPaciente> findAllByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DoencaPaciente d WHERE d.prontuario.id = :prontuarioId AND d.ativa = true AND d.tenant.id = :tenantId")
    List<DoencaPaciente> findAtivasByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId);
}

