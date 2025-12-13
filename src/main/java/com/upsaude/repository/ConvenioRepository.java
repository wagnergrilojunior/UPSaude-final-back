package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Convenio;
import com.upsaude.entity.Tenant;

public interface ConvenioRepository extends JpaRepository<Convenio, UUID> {

    Page<Convenio> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Convenio> findByTenant(Tenant tenant, Pageable pageable);

    Page<Convenio> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    boolean existsByCnpjAndTenant(String cnpj, Tenant tenant);

    boolean existsByCnpjAndTenantAndIdNot(String cnpj, Tenant tenant, UUID id);

    boolean existsByInscricaoEstadualAndTenant(String inscricaoEstadual, Tenant tenant);

    boolean existsByInscricaoEstadualAndTenantAndIdNot(String inscricaoEstadual, Tenant tenant, UUID id);

    boolean existsByCodigoAndTenant(String codigo, Tenant tenant);

    boolean existsByCodigoAndTenantAndIdNot(String codigo, Tenant tenant, UUID id);

    @Query("SELECT c FROM Convenio c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<Convenio> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM Convenio c WHERE c.tenant.id = :tenantId")
    Page<Convenio> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Convenio c WHERE c.cnpj = :cnpj AND c.tenant.id = :tenantId")
    boolean existsByCnpjAndTenantId(@Param("cnpj") String cnpj, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Convenio c WHERE c.cnpj = :cnpj AND c.tenant.id = :tenantId AND c.id <> :id")
    boolean existsByCnpjAndTenantIdAndIdNot(@Param("cnpj") String cnpj, @Param("tenantId") UUID tenantId, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Convenio c WHERE c.inscricaoEstadual = :inscricaoEstadual AND c.tenant.id = :tenantId")
    boolean existsByInscricaoEstadualAndTenantId(@Param("inscricaoEstadual") String inscricaoEstadual, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Convenio c WHERE c.inscricaoEstadual = :inscricaoEstadual AND c.tenant.id = :tenantId AND c.id <> :id")
    boolean existsByInscricaoEstadualAndTenantIdAndIdNot(@Param("inscricaoEstadual") String inscricaoEstadual, @Param("tenantId") UUID tenantId, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Convenio c WHERE c.codigo = :codigo AND c.tenant.id = :tenantId")
    boolean existsByCodigoAndTenantId(@Param("codigo") String codigo, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Convenio c WHERE c.codigo = :codigo AND c.tenant.id = :tenantId AND c.id <> :id")
    boolean existsByCodigoAndTenantIdAndIdNot(@Param("codigo") String codigo, @Param("tenantId") UUID tenantId, @Param("id") UUID id);
}
