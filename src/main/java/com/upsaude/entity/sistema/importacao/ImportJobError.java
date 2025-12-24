package com.upsaude.entity.sistema.importacao;

import com.upsaude.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade para registrar erros detalhados de processamento de jobs de importação.
 * Permite consulta paginada de erros sem "inchar" a tabela principal.
 */
@Entity
@Table(
    name = "import_job_error",
    schema = "public",
    indexes = {
        @Index(name = "idx_import_job_error_job_id", columnList = "job_id"),
        @Index(name = "idx_import_job_error_linha", columnList = "job_id,linha"),
        @Index(name = "idx_import_job_error_codigo", columnList = "codigo_erro")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ImportJobError extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private ImportJob job;

    @Column(name = "linha", nullable = false)
    private Long linha;

    @Column(name = "codigo_erro", length = 50)
    private String codigoErro;

    @Column(name = "mensagem", columnDefinition = "TEXT", nullable = false)
    private String mensagem;

    @Column(name = "raw_line_hash", length = 64)
    private String rawLineHash; // Hash da linha original para detectar duplicatas

    @Column(name = "raw_line_preview", length = 500)
    private String rawLinePreview; // Preview dos primeiros 500 caracteres da linha
}

