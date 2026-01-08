package com.upsaude.repository.profissional;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface ProfissionaisSaudeRepository extends JpaRepository<ProfissionaisSaude, UUID>, JpaSpecificationExecutor<ProfissionaisSaude> {

    Page<ProfissionaisSaude> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT DISTINCT p FROM ProfissionaisSaude p " +
           "LEFT JOIN FETCH p.enderecoProfissional e " +
           "LEFT JOIN FETCH e.estado " +
           "LEFT JOIN FETCH e.cidade " +
           "LEFT JOIN FETCH p.sigtapOcupacao " +
           "WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.tenant.id = :tenantId")
    Page<ProfissionaisSaude> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.documentosBasicos.cpf = :cpf")
    boolean existsByCpf(@Param("cpf") String cpf);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.cpf = :cpf AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByCpfAndTenant(@Param("cpf") String cpf, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.cpf = :cpf AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByCpfAndTenantId(@Param("cpf") String cpf, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.cpf = :cpf")
    Optional<ProfissionaisSaude> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.contato.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.contato.email = :email AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByEmailAndTenant(@Param("email") String email, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.contato.email = :email AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByEmailAndTenantId(@Param("email") String email, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.contato.email = :email AND p.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.documentosBasicos.rg = :rg")
    boolean existsByRg(@Param("rg") String rg);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.rg = :rg AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByRgAndTenant(@Param("rg") String rg, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.rg = :rg AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByRgAndTenantId(@Param("rg") String rg, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.documentosBasicos.rg = :rg AND p.id != :id")
    boolean existsByRgAndIdNot(@Param("rg") String rg, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.documentosBasicos.cns = :cns")
    boolean existsByCns(@Param("cns") String cns);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.cns = :cns AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByCnsAndTenant(@Param("cns") String cns, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.documentosBasicos.cns = :cns AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByCnsAndTenantId(@Param("cns") String cns, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.documentosBasicos.cns = :cns AND p.id != :id")
    boolean existsByCnsAndIdNot(@Param("cns") String cns, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProfissionaisSaude p WHERE p.documentosBasicos.cpf = :cpf AND p.id != :id")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") UUID id);
}
