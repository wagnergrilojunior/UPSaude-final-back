package com.upsaude.repository;

import com.upsaude.entity.IntegracaoGov;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IntegracaoGovRepository extends JpaRepository<IntegracaoGov, UUID> {

    @Query("SELECT i FROM IntegracaoGov i WHERE i.id = :id AND i.tenant.id = :tenantId")
    Optional<IntegracaoGov> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM IntegracaoGov i WHERE i.tenant.id = :tenantId")
    Page<IntegracaoGov> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM IntegracaoGov i WHERE i.paciente.id = :pacienteId AND i.tenant.id = :tenantId")
    Optional<IntegracaoGov> findByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM IntegracaoGov i WHERE i.uuidRnds = :uuidRnds AND i.tenant.id = :tenantId")
    Optional<IntegracaoGov> findByUuidRndsAndTenantId(@Param("uuidRnds") UUID uuidRnds, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM IntegracaoGov i WHERE i.estabelecimento.id = :estabelecimentoId AND i.tenant.id = :tenantId")
    Page<IntegracaoGov> findByEstabelecimentoIdAndTenantId(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);
}
