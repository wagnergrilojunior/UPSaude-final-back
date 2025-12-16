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
@Schema(description = "Dados de idade aplicacao vacina")
public class IdadeAplicacaoVacinaRequest {
    private Integer idadeMinimaDias;
    private Integer idadeMaximaDias;
    private Integer idadeMinimaMeses;
    private Integer idadeMaximaMeses;
    private Integer idadeMinimaAnos;
    private Integer idadeMaximaAnos;
}
