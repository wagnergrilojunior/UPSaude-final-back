package com.upsaude.repository.clinica.prontuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface ProntuarioRepository extends JpaRepository<Prontuario, UUID> {

    Page<Prontuario> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Prontuario> findByTenant(Tenant tenant, Pageable pageable);

    Page<Prontuario> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM Prontuario p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<Prontuario> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM Prontuario p WHERE p.tenant.id = :tenantId")
    Page<Prontuario> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Prontuario p WHERE p.paciente.id = :pacienteId AND p.tenant.id = :tenantId")
    Page<Prontuario> findByPacienteIdAndTenantId(@Param("pacienteId") UUID pacienteId, @Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Prontuario> findByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Prontuario> findByCriadoPorAndTenantId(UUID criadoPor, UUID tenantId, Pageable pageable);
}

