package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.LogsAuditoria;
import com.upsaude.entity.Tenant;

public interface LogsAuditoriaRepository extends JpaRepository<LogsAuditoria, UUID> {
    
    /**
     * Busca todos os logs de auditoria de um estabelecimento, ordenados por data de criação decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de logs do estabelecimento
     */
    Page<LogsAuditoria> findByEstabelecimentoIdOrderByCreatedAtDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os logs de auditoria de um tenant, ordenados por data de criação decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de logs do tenant
     */
    Page<LogsAuditoria> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os logs de auditoria de um estabelecimento e tenant, ordenados por data de criação decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de logs
     */
    Page<LogsAuditoria> findByEstabelecimentoIdAndTenantOrderByCreatedAtDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
