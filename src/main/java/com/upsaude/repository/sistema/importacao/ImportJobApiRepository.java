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
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository para operações de API HTTP relacionadas a ImportJob
 * 
 * USO EXCLUSIVO API - NÃO USAR EM JOB
 * 
 * Métodos de leitura e upload (criação de job via HTTP)
 */
@Repository
public interface ImportJobApiRepository extends JpaRepository<ImportJob, UUID>, JpaSpecificationExecutor<ImportJob> {

    // ========== CONSULTAS BÁSICAS (API) ==========
    
    Optional<ImportJob> findByIdAndTenant_Id(UUID id, UUID tenantId);
    
    Page<ImportJob> findByTenant_IdOrderByCreatedAtDesc(UUID tenantId, Pageable pageable);
    
    Page<ImportJob> findByTenant_IdAndStatus(UUID tenantId, ImportJobStatusEnum status, Pageable pageable);
    
    Page<ImportJob> findByTenant_IdAndTipo(UUID tenantId, ImportJobTipoEnum tipo, Pageable pageable);
    
    List<ImportJob> findByTenant_IdAndStatus(UUID tenantId, ImportJobStatusEnum status);
    
    // Método necessário para validação de limite de jobs pendentes
    long countByTenant_IdAndStatusIn(UUID tenantId, java.util.List<ImportJobStatusEnum> statuses);
    
    // ========== CONSULTAS POR COMPETÊNCIA (API) ==========
    
    List<ImportJob> findByTenant_IdAndTipoAndCompetenciaAnoAndCompetenciaMesAndUf(
        UUID tenantId,
        ImportJobTipoEnum tipo,
        String competenciaAno,
        String competenciaMes,
        String uf
    );
    
    // ========== ESTATÍSTICAS (API) ==========
    
    @Query("""
        SELECT COUNT(j) FROM ImportJob j
        WHERE j.tenant.id = :tenantId
        AND j.status = :status
        """)
    long countJobsByTenantAndStatus(@Param("tenantId") UUID tenantId, @Param("status") ImportJobStatusEnum status);
    
    // ========== VALIDAÇÃO DE DUPLICATAS (API) ==========
    
    /**
     * Verifica se já existe um job com as mesmas características (nome, tamanho, tipo, competência, UF, tenant).
     * Usado para evitar uploads duplicados.
     * 
     * @param tenantId ID do tenant
     * @param tipo Tipo do job
     * @param originalFilename Nome original do arquivo
     * @param sizeBytes Tamanho do arquivo em bytes
     * @param competenciaAno Ano da competência
     * @param competenciaMes Mês da competência
     * @param uf UF (pode ser null)
     * @return Lista de jobs duplicados encontrados
     */
    @Query("""
        SELECT j FROM ImportJob j
        WHERE j.tenant.id = :tenantId
        AND j.tipo = :tipo
        AND j.originalFilename = :originalFilename
        AND j.sizeBytes = :sizeBytes
        AND j.competenciaAno = :competenciaAno
        AND j.competenciaMes = :competenciaMes
        AND ((:uf IS NULL AND j.uf IS NULL) OR (:uf IS NOT NULL AND j.uf = :uf))
        AND j.status IN :statuses
        ORDER BY j.createdAt DESC
        """)
    List<ImportJob> findDuplicados(
        @Param("tenantId") UUID tenantId,
        @Param("tipo") ImportJobTipoEnum tipo,
        @Param("originalFilename") String originalFilename,
        @Param("sizeBytes") Long sizeBytes,
        @Param("competenciaAno") String competenciaAno,
        @Param("competenciaMes") String competenciaMes,
        @Param("uf") String uf,
        @Param("statuses") List<ImportJobStatusEnum> statuses
    );
}

