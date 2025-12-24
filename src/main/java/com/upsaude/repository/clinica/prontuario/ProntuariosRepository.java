package com.upsaude.repository.clinica.prontuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface ProntuariosRepository extends JpaRepository<Prontuarios, UUID> {

    Page<Prontuarios> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Prontuarios> findByTenant(Tenant tenant, Pageable pageable);

    Page<Prontuarios> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM Prontuarios p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<Prontuarios> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM Prontuarios p WHERE p.tenant.id = :tenantId")
    Page<Prontuarios> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Prontuarios> findByPacienteIdAndTenantId(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<Prontuarios> findByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Prontuarios> findByTipoRegistroContainingIgnoreCaseAndTenantId(String tipoRegistro, UUID tenantId, Pageable pageable);

    Page<Prontuarios> findByCriadoPorAndTenantId(UUID criadoPor, UUID tenantId, Pageable pageable);
}
