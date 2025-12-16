package com.upsaude.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.LogsAuditoria;

public interface LogsAuditoriaRepository extends JpaRepository<LogsAuditoria, UUID> {

    @Query("SELECT l FROM LogsAuditoria l WHERE l.id = :id AND l.tenant.id = :tenantId")
    Optional<LogsAuditoria> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LogsAuditoria l WHERE l.tenant.id = :tenantId")
    Page<LogsAuditoria> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LogsAuditoria l WHERE l.estabelecimento.id = :estabelecimentoId AND l.tenant.id = :tenantId ORDER BY l.createdAt DESC")
    Page<LogsAuditoria> findByEstabelecimentoIdAndTenantIdOrderByCreatedAtDesc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);
}
