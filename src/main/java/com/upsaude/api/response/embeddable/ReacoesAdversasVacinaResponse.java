package com.upsaude.api.response.embeddable;

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
public class ReacoesAdversasVacinaResponse {
    private String reacoesAdversasComuns;
    private String reacoesAdversasRaras;
    private String reacoesAdversasGraves;
}
