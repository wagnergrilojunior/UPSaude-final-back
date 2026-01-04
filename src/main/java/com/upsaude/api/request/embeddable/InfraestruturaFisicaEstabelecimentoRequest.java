package com.upsaude.api.request.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Schema(description = "Dados de infraestrutura física do estabelecimento")
public class InfraestruturaFisicaEstabelecimentoRequest {

    @Min(value = 0, message = "Quantidade de leitos não pode ser negativa")
    @Max(value = 10000, message = "Quantidade de leitos não pode ser maior que 10000")
    private Integer quantidadeLeitos;

    @Min(value = 0, message = "Quantidade de consultórios não pode ser negativa")
    @Max(value = 1000, message = "Quantidade de consultórios não pode ser maior que 1000")
    private Integer quantidadeConsultorios;

    @Min(value = 0, message = "Quantidade de salas de cirurgia não pode ser negativa")
    @Max(value = 500, message = "Quantidade de salas de cirurgia não pode ser maior que 500")
    private Integer quantidadeSalasCirurgia;

    @Min(value = 0, message = "Quantidade de ambulatórios não pode ser negativa")
    @Max(value = 500, message = "Quantidade de ambulatórios não pode ser maior que 500")
    private Integer quantidadeAmbulatorios;

    private Double areaConstruidaMetrosQuadrados;

    private Double areaTotalMetrosQuadrados;
}

