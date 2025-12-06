package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtestadoConsultaResponse {
    private Boolean atestadoEmitido;
    private String tipoAtestado;
    private Integer diasAfastamento;
    private LocalDate dataInicioAfastamento;
    private LocalDate dataFimAfastamento;
    private String motivoAtestado;
    private String cidAtestado;
}
