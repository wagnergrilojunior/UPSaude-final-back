package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaFisicaEstabelecimento {

    @Column(name = "quantidade_leitos")
    private Integer quantidadeLeitos;

    @Column(name = "quantidade_consultorios")
    private Integer quantidadeConsultorios;

    @Column(name = "quantidade_salas_cirurgia")
    private Integer quantidadeSalasCirurgia;

    @Column(name = "quantidade_ambulatorios")
    private Integer quantidadeAmbulatorios;

    @Column(name = "area_construida_metros_quadrados")
    private Double areaConstruidaMetrosQuadrados;

    @Column(name = "area_total_metros_quadrados")
    private Double areaTotalMetrosQuadrados;
}

