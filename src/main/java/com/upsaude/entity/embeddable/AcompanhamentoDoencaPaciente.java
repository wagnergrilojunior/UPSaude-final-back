package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Classe embeddable para informações de acompanhamento da doença no paciente.
 *
 * @author UPSaúde
 */
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
    private LocalDate dataUltimaConsulta; // Data da última consulta relacionada à doença

    @Column(name = "data_proxima_consulta")
    private LocalDate dataProximaConsulta; // Data da próxima consulta agendada

    @Column(name = "frequencia_acompanhamento", length = 50)
    private String frequenciaAcompanhamento; // Ex: Mensal, Trimestral, Semestral, Anual

    @Column(name = "especialista_responsavel", length = 255)
    private String especialistaResponsavel; // Nome do especialista responsável pelo acompanhamento

    @Column(name = "estabelecimento_acompanhamento", length = 255)
    private String estabelecimentoAcompanhamento; // Estabelecimento onde é feito o acompanhamento

    @Column(name = "evolucao_clinica", columnDefinition = "TEXT")
    private String evolucaoClinica; // Evolução clínica da doença

    @Column(name = "complicacoes", columnDefinition = "TEXT")
    private String complicacoes; // Complicações apresentadas
}

