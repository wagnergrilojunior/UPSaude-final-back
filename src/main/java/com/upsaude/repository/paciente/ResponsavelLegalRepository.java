package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.sistema.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ResponsavelLegalRepository extends JpaRepository<ResponsavelLegal, UUID> {

    Optional<ResponsavelLegal> findByPacienteId(UUID pacienteId);

    Page<ResponsavelLegal> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<ResponsavelLegal> findByTenant(Tenant tenant, Pageable pageable);

    Page<ResponsavelLegal> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT r FROM ResponsavelLegal r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<ResponsavelLegal> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    Optional<ResponsavelLegal> findByPacienteIdAndTenantId(UUID pacienteId, UUID tenantId);

    @Query("SELECT r FROM ResponsavelLegal r WHERE r.tenant.id = :tenantId")
    Page<ResponsavelLegal> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ResponsavelLegal> findByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<ResponsavelLegal> findByCpfContainingIgnoreCaseAndTenantId(String cpf, UUID tenantId, Pageable pageable);

    Page<ResponsavelLegal> findByNomeContainingIgnoreCaseAndTenantId(String nome, UUID tenantId, Pageable pageable);
}
