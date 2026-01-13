package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
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
public class RenegociacaoReceber extends BaseEntity {

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "motivo", length = 255)
    private String motivo;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    // Nota: Relacionamento N..N com títulos originais será feito via tabela associativa
    // e referência aos novos títulos gerados será feita via serviço
}
