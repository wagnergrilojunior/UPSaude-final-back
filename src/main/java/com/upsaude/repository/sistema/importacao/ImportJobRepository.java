package com.upsaude.repository.sistema.importacao;

import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImportJobRepository extends JpaRepository<ImportJob, UUID>, JpaSpecificationExecutor<ImportJob> {
    
    Optional<ImportJob> findByIdAndTenant_Id(UUID id, UUID tenantId);
    
    Page<ImportJob> findByTenant_IdOrderByCreatedAtDesc(UUID tenantId, Pageable pageable);
    
    Page<ImportJob> findByTenant_IdAndStatus(UUID tenantId, ImportJobStatusEnum status, Pageable pageable);
    
    Page<ImportJob> findByTenant_IdAndTipo(UUID tenantId, ImportJobTipoEnum tipo, Pageable pageable);
    
    List<ImportJob> findByTenant_IdAndStatus(UUID tenantId, ImportJobStatusEnum status);
    
    @Query(value = """
        SELECT j.* FROM import_job j
        WHERE j.status = :status
        AND (j.next_run_at IS NULL OR j.next_run_at <= :now)
        ORDER BY j.priority DESC, j.next_run_at ASC NULLS FIRST, j.criado_em ASC
        LIMIT :limit
        FOR UPDATE SKIP LOCKED
        """, nativeQuery = true)
    List<ImportJob> findPendingJobsForProcessing(
        @Param("status") String status,
        @Param("now") OffsetDateTime now,
        @Param("limit") int limit
    );

    @Query(value = "SELECT pg_try_advisory_xact_lock(:lockKey)", nativeQuery = true)
    Boolean tryAdvisoryXactLock(@Param("lockKey") long lockKey);
    @Query(value = """
        WITH candidate AS (
            SELECT j.id
            FROM import_job j
            WHERE j.status = :queuedStatus
              AND (j.next_run_at IS NULL OR j.next_run_at <= :now)
              AND (SELECT COUNT(1) FROM import_job p WHERE p.status = :processingStatus) < :maxGlobal
              AND (
                    j.tenant_id IS NULL
                 OR (SELECT COUNT(1) FROM import_job p WHERE p.status = :processingStatus AND p.tenant_id = j.tenant_id) < :maxPerTenant
              )
            ORDER BY j.priority DESC, j.next_run_at ASC NULLS FIRST, j.criado_em ASC
            LIMIT 1
            FOR UPDATE SKIP LOCKED
        )
        UPDATE import_job j
        SET status = :processingStatus,
            locked_at = :now,
            locked_by = :lockedBy,
            started_at = COALESCE(j.started_at, :now),
            heartbeat_at = :now
        FROM candidate c
        WHERE j.id = c.id
        RETURNING j.id
        """, nativeQuery = true)
    UUID claimNextJobForProcessing(@Param("queuedStatus") String queuedStatus,
                                  @Param("processingStatus") String processingStatus,
                                  @Param("now") OffsetDateTime now,
                                  @Param("lockedBy") String lockedBy,
                                  @Param("maxGlobal") int maxGlobal,
                                  @Param("maxPerTenant") int maxPerTenant);
    
    long countByStatus(ImportJobStatusEnum status);
    
    long countByTenant_IdAndStatus(UUID tenantId, ImportJobStatusEnum status);

    long countByTenant_IdAndStatusIn(UUID tenantId, List<ImportJobStatusEnum> statuses);
    
    @Query("SELECT j.tenant.id, COUNT(j) FROM ImportJob j WHERE j.status = :status GROUP BY j.tenant.id")
    List<Object[]> countByStatusGroupedByTenant(@Param("status") ImportJobStatusEnum status);
    @Query("""
        SELECT j FROM ImportJob j
        WHERE j.status = :status
        AND j.heartbeatAt < :expiredBefore
        """)
    List<ImportJob> findStuckJobs(
        @Param("status") ImportJobStatusEnum status,
        @Param("expiredBefore") OffsetDateTime expiredBefore
    );
    
    List<ImportJob> findByTenant_IdAndTipoAndCompetenciaAnoAndCompetenciaMesAndUf(
        UUID tenantId,
        ImportJobTipoEnum tipo,
        String competenciaAno,
        String competenciaMes,
        String uf
    );
    
    @Query("""
        SELECT COUNT(j) FROM ImportJob j
        WHERE j.tenant.id = :tenantId
        AND j.status = :status
        """)
    long countJobsByTenantAndStatus(@Param("tenantId") UUID tenantId, @Param("status") ImportJobStatusEnum status);
}

