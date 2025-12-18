package com.upsaude.entity.estabelecimento.estoque;
import com.upsaude.entity.BaseEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stock_movements", schema = "public")
@Data
public class MovimentacoesEstoque extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_stock_id", nullable = false)
    private EstoquesVacina estoqueVacina;

    @Column(name = "movement_type", nullable = false, length = 50)
    private String tipoMovimento;

    @Column(name = "quantity", nullable = false)
    private Integer quantidade;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "responsible", nullable = false)
    private UUID responsavel;

    @Column(name = "movement_date")
    private OffsetDateTime dataMovimento;
}
