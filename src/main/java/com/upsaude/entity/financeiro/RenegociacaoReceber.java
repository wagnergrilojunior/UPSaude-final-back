package com.upsaude.entity.financeiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(
    name = "renegociacao_receber",
    schema = "public",
    indexes = {
        @Index(name = "idx_renegociacao_receber_data", columnList = "tenant_id, data")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class RenegociacaoReceber extends BaseEntityFinanceiro {

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "motivo", length = 255)
    private String motivo;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

}
