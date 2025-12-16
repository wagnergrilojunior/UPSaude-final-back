package com.upsaude.repository;

import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DadosClinicosBasicosRepository extends JpaRepository<DadosClinicosBasicos, UUID> {

    Optional<DadosClinicosBasicos> findByPacienteId(UUID pacienteId);

    Page<DadosClinicosBasicos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<DadosClinicosBasicos> findByTenant(Tenant tenant, Pageable pageable);

    Page<DadosClinicosBasicos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT d FROM DadosClinicosBasicos d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<DadosClinicosBasicos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DadosClinicosBasicos d WHERE d.tenant.id = :tenantId")
    Page<DadosClinicosBasicos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DadosClinicosBasicos d WHERE d.paciente.id = :pacienteId AND d.tenant.id = :tenantId")
    Optional<DadosClinicosBasicos> findByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DadosClinicosBasicos d WHERE d.paciente.id = :pacienteId AND d.tenant.id = :tenantId")
    boolean existsByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId);
}
