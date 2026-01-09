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

    @Column(name = "tipo", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportJobTipoEnum tipo;

    @Column(name = "competencia_ano", length = 4)
    private String competenciaAno;

    @Column(name = "competencia_mes", length = 2)
    private String competenciaMes;

    @Column(name = "uf", length = 2)
    private String uf;

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

    @Column(name = "payload_json", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payloadJson;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportJobStatusEnum status;

    @Column(name = "priority")
    private Integer priority = 0; 

    @Column(name = "attempts")
    private Integer attempts = 0;

    @Column(name = "max_attempts")
    private Integer maxAttempts = 3;

    @Column(name = "next_run_at")
    private OffsetDateTime nextRunAt;

    @Column(name = "locked_at")
    private OffsetDateTime lockedAt;

    @Column(name = "locked_by", length = 100)
    private String lockedBy; 

    @Column(name = "heartbeat_at")
    private OffsetDateTime heartbeatAt; 

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

    @Column(name = "checkpoint_linha")
    private Long checkpointLinha = 0L;

    @Column(name = "checkpoint_byte_offset")
    private Long checkpointByteOffset;

    @Column(name = "storage_object_version", length = 100)
    private String storageObjectVersion; 

    @Column(name = "error_summary", columnDefinition = "TEXT")
    private String errorSummary;

    @Column(name = "error_sample_json", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String errorSampleJson; 

    @Column(name = "created_by_user_id")
    private java.util.UUID createdByUserId;
}
