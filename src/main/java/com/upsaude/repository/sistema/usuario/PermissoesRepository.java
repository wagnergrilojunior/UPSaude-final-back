package com.upsaude.repository.sistema.usuario;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.usuario.Permissoes;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface PermissoesRepository extends JpaRepository<Permissoes, UUID> {

    Page<Permissoes> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Permissoes> findByTenant(Tenant tenant, Pageable pageable);

    Page<Permissoes> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM Permissoes p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<Permissoes> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM Permissoes p WHERE p.tenant.id = :tenantId")
    Page<Permissoes> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Permissoes> findByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Permissoes> findByModuloAndTenantId(String modulo, UUID tenantId, Pageable pageable);

    Page<Permissoes> findByNomeContainingIgnoreCaseAndTenantId(String nome, UUID tenantId, Pageable pageable);
}
