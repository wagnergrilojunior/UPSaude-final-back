package com.upsaude.api.response.sia.kpi;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaKpiResponse {

    private String competencia;
    private String uf;

    private Long totalRegistros;
    private Long procedimentosUnicos;
    private Long estabelecimentosUnicos;

    private Long quantidadeProduzidaTotal;
    private Long quantidadeAprovadaTotal;

    private BigDecimal valorProduzidoTotal;
    private BigDecimal valorAprovadoTotal;
    private BigDecimal diferencaValorTotal;

    private Long registrosComErro;
    private BigDecimal taxaErroRegistros;

    private BigDecimal taxaAprovacaoValor;

    // Quando possível (ex: derivado do município do tenant)
    private BigDecimal producaoPerCapita;
    private Integer populacaoEstimada;
    private String municipioIbge;

    // Dados do estabelecimento (quando disponível)
    private EstabelecimentosResponse estabelecimento;
}

