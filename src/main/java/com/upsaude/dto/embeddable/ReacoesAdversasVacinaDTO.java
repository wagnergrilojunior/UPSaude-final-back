package com.upsaude.dto.embeddable;

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
public class ReacoesAdversasVacinaDTO {
    private String reacoesAdversasComuns;
    private String reacoesAdversasRaras;
    private String reacoesAdversasGraves;
}
