package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de exames solicitados consulta")
public class ExamesSolicitadosConsultaRequest {
    private String examesSolicitados;
    private String examesLaboratoriais;
    private String examesImagem;
    private String examesOutros;

    @NotNull(message = "Urgência dos exames é obrigatório")
    @Builder.Default
    private Boolean urgenciaExames = false;
}
