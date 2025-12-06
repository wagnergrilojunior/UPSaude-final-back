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
public class AcompanhamentoDoencaPacienteResponse {
    private LocalDate dataUltimaConsulta;
    private LocalDate dataProximaConsulta;
    private String frequenciaAcompanhamento;
    private String especialistaResponsavel;
    private String estabelecimentoAcompanhamento;
    private String evolucaoClinica;
    private String complicacoes;
}
