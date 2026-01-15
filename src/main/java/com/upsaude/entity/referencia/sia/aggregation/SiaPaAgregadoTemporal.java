package com.upsaude.entity.referencia.sia.aggregation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "sia_pa_agregado_temporal", schema = "public")
@Data
public class SiaPaAgregadoTemporal {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia;

    @Column(name = "quantidade_produzida_total")
    private Long quantidadeProduzidaTotal;

    @Column(name = "valor_produzido_total", precision = 18, scale = 2)
    private BigDecimal valorProduzidoTotal;

    @Column(name = "valor_aprovado_total", precision = 18, scale = 2)
    private BigDecimal valorAprovadoTotal;

    @Column(name = "quantidade_produzida_prev")
    private Long quantidadeProduzidaPrev;

    @Column(name = "valor_aprovado_prev", precision = 18, scale = 2)
    private BigDecimal valorAprovadoPrev;

    @Column(name = "delta_valor_aprovado", precision = 18, scale = 2)
    private BigDecimal deltaValorAprovado;

    @Column(name = "crescimento_valor_aprovado_pct", precision = 18, scale = 6)
    private BigDecimal crescimentoValorAprovadoPct;
}

