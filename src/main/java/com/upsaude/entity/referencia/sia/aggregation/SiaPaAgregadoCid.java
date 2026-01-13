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
@Table(name = "sia_pa_agregado_cid", schema = "public")
@Data
public class SiaPaAgregadoCid {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia;

    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @Column(name = "cid_principal_codigo", length = 10)
    private String cidPrincipalCodigo;

    @Column(name = "total_registros")
    private Long totalRegistros;

    @Column(name = "quantidade_produzida_total")
    private Long quantidadeProduzidaTotal;

    @Column(name = "valor_aprovado_total", precision = 18, scale = 2)
    private BigDecimal valorAprovadoTotal;

    @Column(name = "procedimentos_unicos")
    private Long procedimentosUnicos;

    @Column(name = "municipios_unicos")
    private Long municipiosUnicos;

    @Column(name = "top_procedimento_codigo", length = 10)
    private String topProcedimentoCodigo;

    @Column(name = "top_procedimento_total")
    private Long topProcedimentoTotal;

    @Column(name = "top_municipio_ufmun_codigo", length = 6)
    private String topMunicipioUfmunCodigo;

    @Column(name = "top_municipio_total")
    private Long topMunicipioTotal;
}

