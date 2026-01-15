package com.upsaude.entity.anexo;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import com.upsaude.util.converter.CategoriaAnexoEnumConverter;
import com.upsaude.util.converter.StatusAnexoEnumConverter;
import com.upsaude.util.converter.TargetTypeAnexoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Table(name = "anexos", schema = "public", indexes = {
        @Index(name = "idx_anexo_tenant_target", columnList = "tenant_id, target_type, target_id"),
        @Index(name = "idx_anexo_target", columnList = "target_type, target_id"),
        @Index(name = "idx_anexo_status", columnList = "status"),
        @Index(name = "idx_anexo_criado_em", columnList = "criado_em"),
        @Index(name = "idx_anexo_checksum", columnList = "checksum")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class Anexo extends BaseEntity {

    @Convert(converter = TargetTypeAnexoEnumConverter.class)
    @Column(name = "target_type", nullable = false, length = 50)
    private TargetTypeAnexoEnum targetType;

    @Column(name = "target_id", nullable = false)
    private UUID targetId;

    @Column(name = "storage_bucket", nullable = false, length = 100)
    private String storageBucket;

    @Column(name = "storage_object_path", nullable = false, length = 500)
    private String storageObjectPath;

    @Column(name = "file_name_original", nullable = false, length = 255)
    private String fileNameOriginal;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "checksum", length = 64)
    private String checksum;

    @Convert(converter = CategoriaAnexoEnumConverter.class)
    @Column(name = "categoria")
    private CategoriaAnexoEnum categoria;

    @Column(name = "visivel_para_paciente", nullable = false)
    private Boolean visivelParaPaciente = false;

    @Convert(converter = StatusAnexoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusAnexoEnum status = StatusAnexoEnum.PENDENTE;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "criado_por")
    private UUID criadoPor;

    @Column(name = "excluido_por")
    private UUID excluidoPor;
}
