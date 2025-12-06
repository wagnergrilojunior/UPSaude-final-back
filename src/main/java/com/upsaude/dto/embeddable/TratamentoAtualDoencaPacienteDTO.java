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
public class TratamentoAtualDoencaPacienteDTO {
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
