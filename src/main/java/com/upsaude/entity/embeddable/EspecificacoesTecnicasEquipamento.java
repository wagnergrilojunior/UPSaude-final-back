package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecificacoesTecnicasEquipamento {

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "versao", length = 50)
    private String versao;

    @Column(name = "potencia", precision = 10, scale = 2)
    private BigDecimal potencia;

    @Column(name = "unidade_potencia", length = 50)
    private String unidadePotencia;

    @Column(name = "peso", precision = 10, scale = 2)
    private BigDecimal peso;

    @Column(name = "altura", precision = 10, scale = 2)
    private BigDecimal altura;

    @Column(name = "largura", precision = 10, scale = 2)
    private BigDecimal largura;

    @Column(name = "profundidade", precision = 10, scale = 2)
    private BigDecimal profundidade;

    @Column(name = "tensao_eletrica", length = 50)
    private String tensaoEletrica;

    @Column(name = "frequencia", length = 50)
    private String frequencia;

    @Column(name = "corrente", length = 50)
    private String corrente;

    @Column(name = "tipo_alimentacao", length = 100)
    private String tipoAlimentacao;
}
