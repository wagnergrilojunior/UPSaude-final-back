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
public class TratamentoAtualDoencaPacienteResponse {
    private String medicacaoAtual;
    private String doseMedicacao;
    private String frequenciaMedicacao;
    private LocalDate dataInicioTratamento;
    private LocalDate dataFimTratamento;
    private String procedimentosEmAndamento;
    private String adherenciaTratamento;
    private String efeitosColaterais;
    private String contraindicacoes;
}
