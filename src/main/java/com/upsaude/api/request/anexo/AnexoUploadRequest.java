package com.upsaude.api.request.anexo;

import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para upload de anexo")
public class AnexoUploadRequest {

    @NotNull(message = "targetType é obrigatório")
    @Schema(description = "Tipo do recurso alvo (PACIENTE, AGENDAMENTO, ATENDIMENTO, CONSULTA, etc)", required = true, example = "PACIENTE")
    private TargetTypeAnexoEnum targetType;

    @NotNull(message = "targetId é obrigatório")
    @Schema(description = "ID do recurso alvo (UUID)", required = true)
    private UUID targetId;

    @Schema(description = "Categoria do anexo (LAUDO, EXAME, DOCUMENTO, IMAGEM, etc)", example = "DOCUMENTO")
    private CategoriaAnexoEnum categoria;

    @Schema(description = "Indica se o anexo é visível para o paciente", example = "false")
    private Boolean visivelParaPaciente = false;

    @Schema(description = "Descrição opcional do anexo", example = "Documento de identidade")
    private String descricao;

    @Schema(description = "Tags separadas por vírgula", example = "identidade,cpf")
    private String tags;
}
