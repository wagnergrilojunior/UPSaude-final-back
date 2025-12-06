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
public class AcompanhamentoDoencaPacienteDTO {
    private LocalDate dataUltimaConsulta;
    private LocalDate dataProximaConsulta;
    private String frequenciaAcompanhamento;
    private String especialistaResponsavel;
    private String estabelecimentoAcompanhamento;
    private String evolucaoClinica;
    private String complicacoes;
}
