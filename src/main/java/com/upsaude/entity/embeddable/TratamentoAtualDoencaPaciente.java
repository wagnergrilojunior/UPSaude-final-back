package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class TratamentoAtualDoencaPaciente {

    public TratamentoAtualDoencaPaciente() {
        this.medicacaoAtual = "";
        this.doseMedicacao = "";
        this.frequenciaMedicacao = "";
        this.procedimentosEmAndamento = "";
        this.adherenciaTratamento = "";
        this.efeitosColaterais = "";
        this.contraindicacoes = "";
    }

    @Column(name = "medicacao_atual", columnDefinition = "TEXT")
    private String medicacaoAtual;

    @Column(name = "dose_medicacao", columnDefinition = "TEXT")
    private String doseMedicacao;

    @Column(name = "frequencia_medicacao", length = 100)
    private String frequenciaMedicacao;

    @Column(name = "data_inicio_tratamento")
    private LocalDate dataInicioTratamento;

    @Column(name = "data_fim_tratamento")
    private LocalDate dataFimTratamento;

    @Column(name = "procedimentos_em_andamento", columnDefinition = "TEXT")
    private String procedimentosEmAndamento;

    @Column(name = "adherencia_tratamento", length = 50)
    private String adherenciaTratamento;

    @Column(name = "efeitos_colaterais", columnDefinition = "TEXT")
    private String efeitosColaterais;

    @Column(name = "contraindicoes", columnDefinition = "TEXT")
    private String contraindicacoes;
}
