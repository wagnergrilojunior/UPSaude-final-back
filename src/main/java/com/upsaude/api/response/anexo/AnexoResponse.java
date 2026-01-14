package com.upsaude.api.response.anexo;

import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response de anexo")
public class AnexoResponse {

    @Schema(description = "ID do anexo")
    private UUID id;

    @Schema(description = "Data de criação")
    private OffsetDateTime createdAt;

    @Schema(description = "Data de atualização")
    private OffsetDateTime updatedAt;

    @Schema(description = "Tipo do recurso alvo")
    private TargetTypeAnexoEnum targetType;

    @Schema(description = "ID do recurso alvo")
    private UUID targetId;

    @Schema(description = "Nome original do arquivo")
    private String fileNameOriginal;

    @Schema(description = "Tipo MIME do arquivo")
    private String mimeType;

    @Schema(description = "Tamanho do arquivo em bytes")
    private Long sizeBytes;

    @Schema(description = "Categoria do anexo")
    private CategoriaAnexoEnum categoria;

    @Schema(description = "Indica se é visível para o paciente")
    private Boolean visivelParaPaciente;

    @Schema(description = "Status do anexo")
    private StatusAnexoEnum status;

    @Schema(description = "Descrição do anexo")
    private String descricao;

    @Schema(description = "Tags")
    private String tags;

    @Schema(description = "ID do usuário que criou")
    private UUID criadoPor;
}
