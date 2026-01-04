package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaFisicaEstabelecimentoResponse {

    private Integer quantidadeLeitos;

    private Integer quantidadeConsultorios;

    private Integer quantidadeSalasCirurgia;

    private Integer quantidadeAmbulatorios;

    private Double areaConstruidaMetrosQuadrados;

    private Double areaTotalMetrosQuadrados;
}

