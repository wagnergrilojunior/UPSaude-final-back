package com.upsaude.api.request.embeddable;

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
@Schema(description = "Dados de sintomas doenca")
public class SintomasDoencaRequest {
    private String sintomasComuns;
    private String sintomasGraves;
    private String sinaisClinicos;
    private String manifestacoesExtras;
}
