package com.upsaude.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "procedimentos_odontologicos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_proc_odontologicos_codigo", columnNames = {"codigo", "tenant_id"})
       })
@Data
public class ProcedimentosOdontologicos extends BaseEntity {

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", length = 255)
    private String nome;

    @Size(max = 50, message = "Código TUSS deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "custo_sugerido")
    private BigDecimal custoSugerido;
}
