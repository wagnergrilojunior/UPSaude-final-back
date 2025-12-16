package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de tratamento atual doenca paciente")
public class TratamentoAtualDoencaPacienteRequest {
    private String medicacaoAtual;
    private String doseMedicacao;

    @Size(max = 100, message = "Frequência medicação deve ter no máximo 100 caracteres")
    private String frequenciaMedicacao;

    private LocalDate dataInicioTratamento;
    private LocalDate dataFimTratamento;
    private String procedimentosEmAndamento;

    @Size(max = 50, message = "Aderência tratamento deve ter no máximo 50 caracteres")
    private String adherenciaTratamento;

    private String efeitosColaterais;
    private String contraindicacoes;
}
