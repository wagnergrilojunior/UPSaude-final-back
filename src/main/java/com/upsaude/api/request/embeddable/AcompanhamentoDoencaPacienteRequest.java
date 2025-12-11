package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.Size;
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
public class AcompanhamentoDoencaPacienteRequest {
    private LocalDate dataUltimaConsulta;
    private LocalDate dataProximaConsulta;

    @Size(max = 50, message = "Frequência acompanhamento deve ter no máximo 50 caracteres")
    private String frequenciaAcompanhamento;

    @Size(max = 255, message = "Especialista responsável deve ter no máximo 255 caracteres")
    private String especialistaResponsavel;

    @Size(max = 255, message = "Estabelecimento acompanhamento deve ter no máximo 255 caracteres")
    private String estabelecimentoAcompanhamento;

    private String evolucaoClinica;
    private String complicacoes;
}
