package com.upsaude.dto.embeddable;

import java.time.LocalDate;
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
public class AtestadoConsultaDTO {
    private Boolean atestadoEmitido;
    private String tipoAtestado;
    private Integer diasAfastamento;
    private LocalDate dataInicioAfastamento;
    private LocalDate dataFimAfastamento;
    private String motivoAtestado;
    private String cidAtestado;
}
