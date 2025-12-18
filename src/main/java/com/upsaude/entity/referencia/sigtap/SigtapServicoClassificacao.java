package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classifica??o de Servi?o do SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_servico_classificacao",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_servico_class_serv_cod", columnNames = {"servico_id", "codigo_classificacao", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_servico_class_nome", columnList = "nome"),
                @Index(name = "idx_sigtap_servico_class_servico_id", columnList = "servico_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapServicoClassificacao extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private SigtapServico servico;

    @Column(name = "codigo_classificacao", nullable = false, length = 3)
    private String codigoClassificacao;

    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
