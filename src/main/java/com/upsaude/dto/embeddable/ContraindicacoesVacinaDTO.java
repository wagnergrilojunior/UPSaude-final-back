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
public class ContraindicacoesVacinaDTO {
    private String contraindicacoes;
    private String precaucoes;
    private Boolean gestantePode;
    private Boolean lactantePode;
    private Boolean imunocomprometidoPode;
}
