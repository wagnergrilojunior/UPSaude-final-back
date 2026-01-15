package com.upsaude.entity.financeiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Entity
@Table(
    name = "recorrencia_financeira",
    schema = "public",
    indexes = {
        @Index(name = "idx_recorrencia_financeira_tipo", columnList = "tenant_id, tipo")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class RecorrenciaFinanceira extends BaseEntityFinanceiro {

    @Column(name = "tipo", nullable = false, length = 20)
private String tipo;

    @Column(name = "periodicidade", nullable = false, length = 20)
private String periodicidade;

    @Column(name = "dia_mes")
    private Integer diaMes;

    @Column(name = "dia_semana")
private Integer diaSemana;

    @Column(name = "proxima_geracao_em")
    private OffsetDateTime proximaGeracaoEm;
}
