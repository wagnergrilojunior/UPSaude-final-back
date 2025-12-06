package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Classe embeddable para informações de tratamento atual da doença no paciente.
 *
 * @author UPSaúde
 */
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
    private String medicacaoAtual; // Medicamentos em uso atual

    @Column(name = "dose_medicacao", columnDefinition = "TEXT")
    private String doseMedicacao; // Dosagem dos medicamentos

    @Column(name = "frequencia_medicacao", length = 100)
    private String frequenciaMedicacao; // Frequência de uso dos medicamentos

    @Column(name = "data_inicio_tratamento")
    private LocalDate dataInicioTratamento; // Data de início do tratamento atual

    @Column(name = "data_fim_tratamento")
    private LocalDate dataFimTratamento; // Data prevista de fim do tratamento

    @Column(name = "procedimentos_em_andamento", columnDefinition = "TEXT")
    private String procedimentosEmAndamento; // Procedimentos em andamento

    @Column(name = "adherencia_tratamento", length = 50)
    private String adherenciaTratamento; // Ex: Boa, Regular, Ruim

    @Column(name = "efeitos_colaterais", columnDefinition = "TEXT")
    private String efeitosColaterais; // Efeitos colaterais apresentados

    @Column(name = "contraindicoes", columnDefinition = "TEXT")
    private String contraindicacoes; // Contraindicações ao tratamento
}

