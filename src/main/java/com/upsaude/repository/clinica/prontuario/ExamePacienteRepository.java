package com.upsaude.repository.clinica.prontuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.prontuario.ExamePaciente;

public interface ExamePacienteRepository extends JpaRepository<ExamePaciente, UUID> {

    @Query("SELECT e FROM ExamePaciente e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<ExamePaciente> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM ExamePaciente e WHERE e.tenant.id = :tenantId")
    Page<ExamePaciente> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM ExamePaciente e WHERE e.prontuario.id = :prontuarioId AND e.tenant.id = :tenantId ORDER BY e.dataExame DESC")
    Page<ExamePaciente> findByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT e FROM ExamePaciente e WHERE e.prontuario.id = :prontuarioId AND e.tenant.id = :tenantId")
    List<ExamePaciente> findAllByProntuarioIdAndTenantId(
        @Param("prontuarioId") UUID prontuarioId,
        @Param("tenantId") UUID tenantId);
}

