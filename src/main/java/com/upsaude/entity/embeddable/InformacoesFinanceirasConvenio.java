package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class InformacoesFinanceirasConvenio {

    public InformacoesFinanceirasConvenio() {
        this.formaPagamento = "";
        this.diaVencimento = "";
        this.observacoesFinanceiras = "";
    }

    @Column(name = "data_inicio_contrato")
    private LocalDate dataInicioContrato;

    @Column(name = "data_fim_contrato")
    private LocalDate dataFimContrato;

    @Column(name = "data_renovacao_contrato")
    private LocalDate dataRenovacaoContrato;

    @Column(name = "prazo_contrato_meses")
    private Integer prazoContratoMeses;

    @Column(name = "valor_contrato_mensal", precision = 12, scale = 2)
    private BigDecimal valorContratoMensal;

    @Column(name = "valor_contrato_anual", precision = 12, scale = 2)
    private BigDecimal valorContratoAnual;

    @Column(name = "percentual_desconto", precision = 5, scale = 2)
    private BigDecimal percentualDesconto;

    @Column(name = "percentual_coparticipacao", precision = 5, scale = 2)
    private BigDecimal percentualCoparticipacao;

    @Column(name = "franquia_anual", precision = 12, scale = 2)
    private BigDecimal franquiaAnual;

    @Column(name = "carencia_geral_dias")
    private Integer carenciaGeralDias;

    @Column(name = "carencia_partos_dias")
    private Integer carenciaPartosDias;

    @Column(name = "carencia_cirurgias_dias")
    private Integer carenciaCirurgiasDias;

    @Column(name = "carencia_exames_dias")
    private Integer carenciaExamesDias;

    @Column(name = "limite_anual_consultas")
    private Integer limiteAnualConsultas;

    @Column(name = "limite_anual_exames")
    private Integer limiteAnualExames;

    @Column(name = "limite_anual_internacoes")
    private Integer limiteAnualInternacoes;

    @Column(name = "forma_pagamento", length = 50)
    private String formaPagamento;

    @Column(name = "dia_vencimento", length = 50)
    private String diaVencimento;

    @Column(name = "observacoes_financeiras", length = 255)
    private String observacoesFinanceiras;
}
