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
public class SintomasDoencaDTO {
    private String sintomasComuns;
    private String sintomasGraves;
    private String sinaisClinicos;
    private String manifestacoesExtras;
}
