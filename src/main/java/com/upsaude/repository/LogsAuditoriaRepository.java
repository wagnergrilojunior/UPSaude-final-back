package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.LogsAuditoria;
import com.upsaude.entity.Tenant;

public interface LogsAuditoriaRepository extends JpaRepository<LogsAuditoria, UUID> {

    Page<LogsAuditoria> findByEstabelecimentoIdOrderByCreatedAtDesc(UUID estabelecimentoId, Pageable pageable);

    Page<LogsAuditoria> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);

    Page<LogsAuditoria> findByEstabelecimentoIdAndTenantOrderByCreatedAtDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
