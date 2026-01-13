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
@Table(name = "sia_pa_agregado_procedimento", schema = "public")
@Data
public class SiaPaAgregadoProcedimento {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia;

    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @Column(name = "procedimento_codigo", length = 10)
    private String procedimentoCodigo;

    @Column(name = "total_registros")
    private Long totalRegistros;

    @Column(name = "estabelecimentos_unicos")
    private Long estabelecimentosUnicos;

    @Column(name = "municipios_unicos")
    private Long municipiosUnicos;

    @Column(name = "quantidade_produzida_total")
    private Long quantidadeProduzidaTotal;

    @Column(name = "quantidade_aprovada_total")
    private Long quantidadeAprovadaTotal;

    @Column(name = "valor_aprovado_total", precision = 18, scale = 2)
    private BigDecimal valorAprovadoTotal;

    @Column(name = "valor_aprovado_medio", precision = 18, scale = 2)
    private BigDecimal valorAprovadoMedio;
}

