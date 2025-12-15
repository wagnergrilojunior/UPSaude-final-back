package com.upsaude.repository;

import com.upsaude.entity.MedicaoClinica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicaoClinicaRepository extends JpaRepository<MedicaoClinica, UUID> {

    @Query("SELECT m FROM MedicaoClinica m WHERE m.id = :id AND m.tenant.id = :tenantId")
    Optional<MedicaoClinica> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT m FROM MedicaoClinica m WHERE m.tenant.id = :tenantId")
    Page<MedicaoClinica> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT m FROM MedicaoClinica m WHERE m.paciente.id = :pacienteId AND m.tenant.id = :tenantId ORDER BY m.dataHora DESC")
    Page<MedicaoClinica> findByPacienteIdAndTenantIdOrderByDataHoraDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT m FROM MedicaoClinica m WHERE m.estabelecimento.id = :estabelecimentoId AND m.tenant.id = :tenantId ORDER BY m.dataHora DESC")
    Page<MedicaoClinica> findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);
}
