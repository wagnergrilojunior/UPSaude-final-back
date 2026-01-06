package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecificacoesTecnicasEquipamentoResponse {

    private String modelo;

    private String versao;

    private BigDecimal potencia;

    private String unidadePotencia;

    private BigDecimal peso;

    private BigDecimal altura;

    private BigDecimal largura;

    private BigDecimal profundidade;

    private String tensaoEletrica;

    private String frequencia;

    private String corrente;

    private String tipoAlimentacao;
}
