package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.entity.Tenant;

public interface ProcedimentosOdontologicosRepository extends JpaRepository<ProcedimentosOdontologicos, UUID> {

    Page<ProcedimentosOdontologicos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<ProcedimentosOdontologicos> findByTenant(Tenant tenant, Pageable pageable);

    Page<ProcedimentosOdontologicos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM ProcedimentosOdontologicos p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ProcedimentosOdontologicos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ProcedimentosOdontologicos p WHERE p.tenant.id = :tenantId")
    Page<ProcedimentosOdontologicos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ProcedimentosOdontologicos> findByCodigoContainingIgnoreCaseAndTenantId(String codigo, UUID tenantId, Pageable pageable);

    Page<ProcedimentosOdontologicos> findByNomeContainingIgnoreCaseAndTenantId(String nome, UUID tenantId, Pageable pageable);

    boolean existsByCodigoAndTenantId(String codigo, UUID tenantId);

    boolean existsByCodigoAndTenantIdAndIdNot(String codigo, UUID tenantId, UUID id);
}
