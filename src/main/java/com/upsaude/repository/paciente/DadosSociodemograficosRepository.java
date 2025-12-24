package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DadosSociodemograficosRepository extends JpaRepository<DadosSociodemograficos, UUID> {

    Optional<DadosSociodemograficos> findByPacienteId(UUID pacienteId);

    Page<DadosSociodemograficos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<DadosSociodemograficos> findByTenant(Tenant tenant, Pageable pageable);

    Page<DadosSociodemograficos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT d FROM DadosSociodemograficos d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<DadosSociodemograficos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DadosSociodemograficos d WHERE d.tenant.id = :tenantId")
    Page<DadosSociodemograficos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DadosSociodemograficos d WHERE d.paciente.id = :pacienteId AND d.tenant.id = :tenantId")
    Optional<DadosSociodemograficos> findByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DadosSociodemograficos d WHERE d.paciente.id = :pacienteId AND d.tenant.id = :tenantId")
    boolean existsByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId);
}
