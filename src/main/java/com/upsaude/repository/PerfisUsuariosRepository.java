package com.upsaude.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.entity.Tenant;

public interface PerfisUsuariosRepository extends JpaRepository<PerfisUsuarios, UUID> {

    Page<PerfisUsuarios> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<PerfisUsuarios> findByTenant(Tenant tenant, Pageable pageable);

    Page<PerfisUsuarios> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM PerfisUsuarios p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<PerfisUsuarios> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PerfisUsuarios p WHERE p.tenant.id = :tenantId")
    Page<PerfisUsuarios> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<PerfisUsuarios> findByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<PerfisUsuarios> findByUsuarioIdAndTenantId(UUID usuarioId, UUID tenantId, Pageable pageable);

    boolean existsByUsuarioIdAndTenantId(UUID usuarioId, UUID tenantId);

    boolean existsByUsuarioIdAndTenantIdAndIdNot(UUID usuarioId, UUID tenantId, UUID id);
}
