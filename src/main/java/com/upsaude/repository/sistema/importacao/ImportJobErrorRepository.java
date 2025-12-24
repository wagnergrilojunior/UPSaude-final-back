package com.upsaude.repository.sistema.importacao;

import com.upsaude.entity.sistema.importacao.ImportJobError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Repository para erros detalhados de processamento de jobs de importação.
 */
public interface ImportJobErrorRepository extends JpaRepository<ImportJobError, UUID>, JpaSpecificationExecutor<ImportJobError> {

    Page<ImportJobError> findByJob_IdOrderByLinhaAsc(UUID jobId, Pageable pageable);
    
    Page<ImportJobError> findByJob_IdAndTenant_IdOrderByLinhaAsc(UUID jobId, UUID tenantId, Pageable pageable);
    
    List<ImportJobError> findByJob_IdOrderByLinhaAsc(UUID jobId);
    
    long countByJob_Id(UUID jobId);
    
    @Query("SELECT COUNT(e) FROM ImportJobError e WHERE e.job.id = :jobId AND e.codigoErro = :codigoErro")
    long countByJobIdAndCodigoErro(@Param("jobId") UUID jobId, @Param("codigoErro") String codigoErro);
    
    @Query("""
        SELECT e.codigoErro, COUNT(e) as total
        FROM ImportJobError e
        WHERE e.job.id = :jobId
        GROUP BY e.codigoErro
        ORDER BY total DESC
        """)
    List<Object[]> countErrorsByCodigoGrouped(@Param("jobId") UUID jobId);
}

