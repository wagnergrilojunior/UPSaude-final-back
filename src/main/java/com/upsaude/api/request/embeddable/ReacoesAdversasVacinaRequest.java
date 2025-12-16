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
@Schema(description = "Dados de reacoes adversas vacina")
public class ReacoesAdversasVacinaRequest {
    private String reacoesAdversasComuns;
    private String reacoesAdversasRaras;
    private String reacoesAdversasGraves;
}
