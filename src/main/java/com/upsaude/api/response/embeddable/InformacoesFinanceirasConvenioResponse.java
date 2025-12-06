package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesFinanceirasConvenioResponse {
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
    private LocalDate dataRenovacaoContrato;
    private Integer prazoContratoMeses;
    private BigDecimal valorContratoMensal;
    private BigDecimal valorContratoAnual;
    private BigDecimal percentualDesconto;
    private BigDecimal percentualCoparticipacao;
    private BigDecimal franquiaAnual;
    private Integer carenciaGeralDias;
    private Integer carenciaPartosDias;
    private Integer carenciaCirurgiasDias;
    private Integer carenciaExamesDias;
    private Integer limiteAnualConsultas;
    private Integer limiteAnualExames;
    private Integer limiteAnualInternacoes;
    private String formaPagamento;
    private String diaVencimento;
    private String observacoesFinanceiras;
}
