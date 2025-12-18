package com.upsaude.repository.estabelecimento.departamento;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.estabelecimento.departamento.Departamentos;
import com.upsaude.entity.sistema.Tenant;

public interface DepartamentosRepository extends JpaRepository<Departamentos, UUID> {

    Page<Departamentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Departamentos> findByTenant(Tenant tenant, Pageable pageable);

    Page<Departamentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT d FROM Departamentos d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<Departamentos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM Departamentos d WHERE d.tenant.id = :tenantId")
    Page<Departamentos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM Departamentos d WHERE d.estabelecimento.id = :estabelecimentoId AND d.tenant.id = :tenantId")
    Page<Departamentos> findByEstabelecimentoIdAndTenantId(@Param("estabelecimentoId") UUID estabelecimentoId,
                                                           @Param("tenantId") UUID tenantId,
                                                           Pageable pageable);
}
