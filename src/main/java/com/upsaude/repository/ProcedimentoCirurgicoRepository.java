package com.upsaude.repository;

import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcedimentoCirurgicoRepository extends JpaRepository<ProcedimentoCirurgico, UUID> {

    List<ProcedimentoCirurgico> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId);

    Page<ProcedimentoCirurgico> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId, Pageable pageable);

    Page<ProcedimentoCirurgico> findByCodigoProcedimentoOrderByCreatedAtDesc(String codigoProcedimento, Pageable pageable);

    Page<ProcedimentoCirurgico> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM ProcedimentoCirurgico p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ProcedimentoCirurgico> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ProcedimentoCirurgico p WHERE p.tenant.id = :tenantId")
    Page<ProcedimentoCirurgico> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ProcedimentoCirurgico> findByCirurgiaIdAndTenantIdOrderByCreatedAtAsc(UUID cirurgiaId, UUID tenantId, Pageable pageable);

    Page<ProcedimentoCirurgico> findByCodigoProcedimentoAndTenantIdOrderByCreatedAtDesc(String codigoProcedimento, UUID tenantId, Pageable pageable);
}
