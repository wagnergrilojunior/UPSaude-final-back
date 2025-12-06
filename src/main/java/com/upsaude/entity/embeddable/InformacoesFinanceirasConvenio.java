package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe embeddable para informações financeiras e contratuais do convênio.
 *
 * @author UPSaúde
 */
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
    private LocalDate dataInicioContrato; // Data de início do contrato com o estabelecimento

    @Column(name = "data_fim_contrato")
    private LocalDate dataFimContrato; // Data de fim do contrato (se houver)

    @Column(name = "data_renovacao_contrato")
    private LocalDate dataRenovacaoContrato; // Data de renovação do contrato

    @Column(name = "prazo_contrato_meses")
    private Integer prazoContratoMeses; // Prazo do contrato em meses

    @Column(name = "valor_contrato_mensal", precision = 12, scale = 2)
    private BigDecimal valorContratoMensal; // Valor mensal do contrato

    @Column(name = "valor_contrato_anual", precision = 12, scale = 2)
    private BigDecimal valorContratoAnual; // Valor anual do contrato

    @Column(name = "percentual_desconto", precision = 5, scale = 2)
    private BigDecimal percentualDesconto; // Percentual de desconto oferecido

    @Column(name = "percentual_coparticipacao", precision = 5, scale = 2)
    private BigDecimal percentualCoparticipacao; // Percentual de coparticipação do paciente

    @Column(name = "franquia_anual", precision = 12, scale = 2)
    private BigDecimal franquiaAnual; // Franquia anual (se aplicável)

    @Column(name = "carencia_geral_dias")
    private Integer carenciaGeralDias; // Carência geral em dias

    @Column(name = "carencia_partos_dias")
    private Integer carenciaPartosDias; // Carência para partos em dias

    @Column(name = "carencia_cirurgias_dias")
    private Integer carenciaCirurgiasDias; // Carência para cirurgias em dias

    @Column(name = "carencia_exames_dias")
    private Integer carenciaExamesDias; // Carência para exames em dias

    @Column(name = "limite_anual_consultas")
    private Integer limiteAnualConsultas; // Limite anual de consultas

    @Column(name = "limite_anual_exames")
    private Integer limiteAnualExames; // Limite anual de exames

    @Column(name = "limite_anual_internacoes")
    private Integer limiteAnualInternacoes; // Limite anual de internações

    @Size(max = 50, message = "Forma de pagamento deve ter no máximo 50 caracteres")
    @Column(name = "forma_pagamento", length = 50)
    private String formaPagamento; // Ex: Boleto, Débito automático, Transferência

    @Size(max = 50, message = "Dia de vencimento deve ter no máximo 50 caracteres")
    @Column(name = "dia_vencimento", length = 50)
    private String diaVencimento; // Dia de vencimento do pagamento

    @Size(max = 255, message = "Observações financeiras deve ter no máximo 255 caracteres")
    @Column(name = "observacoes_financeiras", length = 255)
    private String observacoesFinanceiras; // Observações sobre aspectos financeiros
}

