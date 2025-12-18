package com.upsaude.entity.estabelecimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "infraestrutura_estabelecimento", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class InfraestruturaEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    @NotNull(message = "Estabelecimento é obrigatório")
    private Estabelecimentos estabelecimento;

    @Size(max = 255, message = "Tipo de infraestrutura deve ter no máximo 255 caracteres")
    @Column(name = "tipo", nullable = false, length = 255)
    private String tipo;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Size(max = 10, message = "Código CNES deve ter no máximo 10 caracteres")
    @Column(name = "codigo_cnes", length = 10)
    private String codigoCnes;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "capacidade")
    private Integer capacidade;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
}
