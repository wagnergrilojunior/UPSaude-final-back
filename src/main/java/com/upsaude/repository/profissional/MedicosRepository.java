package com.upsaude.repository.profissional;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface MedicosRepository extends JpaRepository<Medicos, UUID>, JpaSpecificationExecutor<Medicos> {

    Page<Medicos> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT m FROM Medicos m WHERE m.registroProfissional.crm = :crm " +
           "AND m.registroProfissional.crmUf = :crmUf AND m.tenant = :tenant")
    Optional<Medicos> findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenant(
            @Param("crm") String crm,
            @Param("crmUf") String crmUf,
            @Param("tenant") Tenant tenant);

    @Query("SELECT m FROM Medicos m WHERE m.registroProfissional.crm = :crm " +
           "AND m.registroProfissional.crmUf = :crmUf AND m.tenant.id = :tenantId")
    Optional<Medicos> findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenantId(
            @Param("crm") String crm,
            @Param("crmUf") String crmUf,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT m FROM Medicos m WHERE m.documentosBasicos.cpf = :cpf AND m.tenant = :tenant")
    Optional<Medicos> findByDocumentosBasicosCpfAndTenant(
            @Param("cpf") String cpf,
            @Param("tenant") Tenant tenant);

    @Query("SELECT m FROM Medicos m WHERE m.documentosBasicos.cpf = :cpf AND m.tenant.id = :tenantId")
    Optional<Medicos> findByDocumentosBasicosCpfAndTenantId(
            @Param("cpf") String cpf,
            @Param("tenantId") UUID tenantId);

    @Override
    @EntityGraph(attributePaths = {
        "especialidades"
    })
    @NonNull
    Page<Medicos> findAll(@NonNull Pageable pageable);

    @Query("SELECT m FROM Medicos m WHERE m.id = :id AND m.tenant.id = :tenantId")
    Optional<Medicos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @EntityGraph(attributePaths = {
        "especialidades",
        "medicosEstabelecimentos",
        "medicosEstabelecimentos.estabelecimento",
        "enderecoMedico"
    })
    @Query("SELECT m FROM Medicos m WHERE m.id = :id AND m.tenant.id = :tenantId")
    Optional<Medicos> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT m FROM Medicos m WHERE m.tenant.id = :tenantId")
    Page<Medicos> findAllByTenantId(@Param("tenantId") UUID tenantId, Pageable pageable);
}
