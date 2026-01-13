package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(
    name = "competencia_financeira",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_competencia_financeira_codigo", columnNames = {"codigo"})
    },
    indexes = {
        @Index(name = "idx_competencia_financeira_data", columnList = "data_inicio, data_fim")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CompetenciaFinanceira extends BaseEntityWithoutTenant {

    @Column(name = "codigo", nullable = false, length = 20, unique = true)
    private String codigo; // AAAAMM ou código custom

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo; // MENSAL | CUSTOM

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "descricao", length = 500)
    private String descricao;
}
