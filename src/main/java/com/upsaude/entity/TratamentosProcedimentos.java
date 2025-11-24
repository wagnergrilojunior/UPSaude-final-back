package com.upsaude.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tratamentos_procedimentos", schema = "public")
@Data
public class TratamentosProcedimentos extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratamento_id", nullable = false)
    private TratamentosOdontologicos tratamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private ProcedimentosOdontologicos procedimento;

    @Pattern(regexp = "^\\d{1,3}$", message = "Número do dente deve ser de 1 a 3 dígitos")
    @Column(name = "dente", length = 3)
    private String dente;

    @Size(max = 20, message = "Faces devem ter no máximo 20 caracteres")
    @Column(name = "faces", length = 20)
    private String faces;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "custo")
    private BigDecimal custo;

    @Column(name = "data_execucao")
    private OffsetDateTime dataExecucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
