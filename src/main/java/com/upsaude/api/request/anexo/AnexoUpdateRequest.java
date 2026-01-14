package com.upsaude.api.request.anexo;

import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para atualização de metadados do anexo")
public class AnexoUpdateRequest {

    @Schema(description = "Categoria do anexo", example = "DOCUMENTO")
    private CategoriaAnexoEnum categoria;

    @Schema(description = "Indica se o anexo é visível para o paciente", example = "true")
    private Boolean visivelParaPaciente;

    @Schema(description = "Status do anexo", example = "ATIVO")
    private StatusAnexoEnum status;

    @Schema(description = "Descrição do anexo", example = "Documento atualizado")
    private String descricao;

    @Schema(description = "Tags separadas por vírgula", example = "identidade,cpf,atualizado")
    private String tags;
}
