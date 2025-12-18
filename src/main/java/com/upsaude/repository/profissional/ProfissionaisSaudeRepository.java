package com.upsaude.repository.profissional;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;

public interface ProfissionaisSaudeRepository extends JpaRepository<ProfissionaisSaude, UUID> {

    Page<ProfissionaisSaude> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @EntityGraph(attributePaths = {
        "conselho",
        "enderecoProfissional",
        "enderecoProfissional.estado",
        "enderecoProfissional.cidade",
        "especialidades"
    })
    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.tenant.id = :tenantId")
    Page<ProfissionaisSaude> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    boolean existsByCpf(String cpf);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.cpf = :cpf AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByCpfAndTenant(@Param("cpf") String cpf, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.cpf = :cpf AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByCpfAndTenantId(@Param("cpf") String cpf, @Param("tenantId") UUID tenantId);

    boolean existsByRegistroProfissionalAndConselhoIdAndUfRegistro(String registroProfissional, UUID conselhoId, String ufRegistro);

    Optional<ProfissionaisSaude> findByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.email = :email AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByEmailAndTenant(@Param("email") String email, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.email = :email AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByEmailAndTenantId(@Param("email") String email, @Param("tenantId") UUID tenantId);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByRg(String rg);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.rg = :rg AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByRgAndTenant(@Param("rg") String rg, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.rg = :rg AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByRgAndTenantId(@Param("rg") String rg, @Param("tenantId") UUID tenantId);

    boolean existsByRgAndIdNot(String rg, UUID id);

    boolean existsByCns(String cns);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.cns = :cns AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByCnsAndTenant(@Param("cns") String cns, @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.cns = :cns AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByCnsAndTenantId(@Param("cns") String cns, @Param("tenantId") UUID tenantId);

    boolean existsByCnsAndIdNot(String cns, UUID id);

    boolean existsByCpfAndIdNot(String cpf, UUID id);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.registroProfissional = :registroProfissional AND p.conselho.id = :conselhoId AND p.ufRegistro = :ufRegistro AND p.tenant = :tenant")
    Optional<ProfissionaisSaude> findByRegistroProfissionalAndConselhoIdAndUfRegistroAndTenant(
            @Param("registroProfissional") String registroProfissional,
            @Param("conselhoId") UUID conselhoId,
            @Param("ufRegistro") String ufRegistro,
            @Param("tenant") Tenant tenant);

    @Query("SELECT p FROM ProfissionaisSaude p WHERE p.registroProfissional = :registroProfissional AND p.conselho.id = :conselhoId AND p.ufRegistro = :ufRegistro AND p.tenant.id = :tenantId")
    Optional<ProfissionaisSaude> findByRegistroProfissionalAndConselhoIdAndUfRegistroAndTenantId(
            @Param("registroProfissional") String registroProfissional,
            @Param("conselhoId") UUID conselhoId,
            @Param("ufRegistro") String ufRegistro,
            @Param("tenantId") UUID tenantId);

    Optional<ProfissionaisSaude> findByRegistroProfissionalAndConselhoIdAndUfRegistro(
            String registroProfissional, UUID conselhoId, String ufRegistro);
}
