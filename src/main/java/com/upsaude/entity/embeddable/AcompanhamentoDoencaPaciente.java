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
public class AcompanhamentoDoencaPaciente {

    public AcompanhamentoDoencaPaciente() {
        this.frequenciaAcompanhamento = "";
        this.especialistaResponsavel = "";
        this.estabelecimentoAcompanhamento = "";
        this.evolucaoClinica = "";
        this.complicacoes = "";
    }

    @Column(name = "data_ultima_consulta")
    private LocalDate dataUltimaConsulta;

    @Column(name = "data_proxima_consulta")
    private LocalDate dataProximaConsulta;

    @Column(name = "frequencia_acompanhamento", length = 50)
    private String frequenciaAcompanhamento;

    @Column(name = "especialista_responsavel", length = 255)
    private String especialistaResponsavel;

    @Column(name = "estabelecimento_acompanhamento", length = 255)
    private String estabelecimentoAcompanhamento;

    @Column(name = "evolucao_clinica", columnDefinition = "TEXT")
    private String evolucaoClinica;

    @Column(name = "complicacoes", columnDefinition = "TEXT")
    private String complicacoes;
}
