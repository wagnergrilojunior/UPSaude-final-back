package com.upsaude.repository.clinica.exame;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.exame.Exames;
import com.upsaude.entity.sistema.Tenant;

public interface ExamesRepository extends JpaRepository<Exames, UUID> {

    Page<Exames> findByEstabelecimentoIdOrderByDataExameDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Exames> findByTenantOrderByDataExameDesc(Tenant tenant, Pageable pageable);

    Page<Exames> findByEstabelecimentoIdAndTenantOrderByDataExameDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT e FROM Exames e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<Exames> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Exames e WHERE e.tenant.id = :tenantId")
    Page<Exames> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Exames> findByEstabelecimentoIdAndTenantIdOrderByDataExameDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Exames> findByPacienteIdAndTenantIdOrderByDataExameDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<Exames> findByDataExameBetweenAndTenantIdOrderByDataExameDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);

    Page<Exames> findByPacienteIdAndDataExameBetweenAndTenantIdOrderByDataExameDesc(UUID pacienteId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);
}
