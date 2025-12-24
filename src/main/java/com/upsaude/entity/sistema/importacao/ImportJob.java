package com.upsaude.entity.sistema.importacao;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

/**
 * Entidade para rastrear jobs de importação de arquivos grandes (SIA-SUS, SIGTAP, CID10).
 * Funciona como fila de processamento com controle de concorrência e checkpoint.
 */
@Entity
@Table(
    name = "import_job",
    schema = "public",
    indexes = {
        @Index(name = "idx_import_job_status", columnList = "status"),
        @Index(name = "idx_import_job_tipo", columnList = "tipo"),
        @Index(name = "idx_import_job_next_run_at", columnList = "next_run_at"),
        @Index(name = "idx_import_job_tenant_status", columnList = "tenant_id,status"),
        @Index(name = "idx_import_job_status_next_run", columnList = "status,next_run_at,priority"),
        @Index(name = "idx_import_job_heartbeat", columnList = "heartbeat_at"),
        @Index(name = "idx_import_job_competencia", columnList = "competencia_ano,competencia_mes,uf")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ImportJob extends BaseEntity {

    // ========== IDENTIFICAÇÃO ==========
    
    @Column(name = "tipo", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportJobTipoEnum tipo;

    @Column(name = "competencia_ano", length = 4)
    private String competenciaAno;

    @Column(name = "competencia_mes", length = 2)
    private String competenciaMes;

    @Column(name = "uf", length = 2)
    private String uf;

    // ========== ARQUIVO NO STORAGE ==========
    
    @Column(name = "storage_bucket", length = 100, nullable = false)
    private String storageBucket;

    @Column(name = "storage_path", length = 500, nullable = false)
    private String storagePath;

    @Column(name = "original_filename", length = 255, nullable = false)
    private String originalFilename;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "checksum", length = 64)
    private String checksum;

    // ========== PAYLOAD (METADADOS DO JOB) ==========
    // Usado para armazenar informações específicas do tipo do job sem criar novas colunas por tipo.
    // Ex: SIGTAP pode armazenar {"layoutPath":"..."}.
    @Column(name = "payload_json", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadJson;

    // ========== STATUS E EXECUÇÃO ==========
    
    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportJobStatusEnum status;

    @Column(name = "priority")
    private Integer priority = 0; // Maior número = maior prioridade

    @Column(name = "attempts")
    private Integer attempts = 0;

    @Column(name = "max_attempts")
    private Integer maxAttempts = 3;

    @Column(name = "next_run_at")
    private OffsetDateTime nextRunAt;

    @Column(name = "locked_at")
    private OffsetDateTime lockedAt;

    @Column(name = "locked_by", length = 100)
    private String lockedBy; // Identificador da instância/thread que está processando

    @Column(name = "heartbeat_at")
    private OffsetDateTime heartbeatAt; // Última atualização de heartbeat (para detectar jobs travados)

    // ========== PROGRESSO ==========
    
    @Column(name = "linhas_lidas")
    private Long linhasLidas = 0L;

    @Column(name = "linhas_processadas")
    private Long linhasProcessadas = 0L;

    @Column(name = "linhas_inseridas")
    private Long linhasInseridas = 0L;

    @Column(name = "linhas_erro")
    private Long linhasErro = 0L;

    @Column(name = "percentual_estimado")
    private Double percentualEstimado;

    @Column(name = "started_at")
    private OffsetDateTime startedAt;

    @Column(name = "finished_at")
    private OffsetDateTime finishedAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    // ========== CHECKPOINT ==========
    
    @Column(name = "checkpoint_linha")
    private Long checkpointLinha = 0L;

    @Column(name = "checkpoint_byte_offset")
    private Long checkpointByteOffset;

    @Column(name = "storage_object_version", length = 100)
    private String storageObjectVersion; // ETag/versão do objeto no storage

    // ========== ERROS ==========
    
    @Column(name = "error_summary", columnDefinition = "TEXT")
    private String errorSummary;

    @Column(name = "error_sample_json", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String errorSampleJson; // Amostra limitada de erros em JSON

    // ========== AUDITORIA ==========
    
    @Column(name = "created_by_user_id")
    private java.util.UUID createdByUserId;
}

