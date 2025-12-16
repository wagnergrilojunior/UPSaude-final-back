package com.upsaude.entity;

import com.upsaude.enums.TipoProcedimentoEnum;
import com.upsaude.util.converter.TipoProcedimentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "catalogo_procedimentos", schema = "public",
       indexes = {
           @Index(name = "idx_catalogo_proc_tipo", columnList = "tipo_procedimento"),
           @Index(name = "idx_catalogo_proc_codigo", columnList = "codigo"),
           @Index(name = "idx_catalogo_proc_estabelecimento", columnList = "estabelecimento_id")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_catalogo_proc_codigo", columnNames = {"codigo", "tenant_id"})
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class CatalogoProcedimentos extends BaseEntity {

    @Convert(converter = TipoProcedimentoEnumConverter.class)
    @Column(name = "tipo_procedimento", nullable = false)
    @NotNull(message = "Tipo de procedimento é obrigatório")
    private TipoProcedimentoEnum tipoProcedimento;

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "custo_sugerido", precision = 10, scale = 2)
    private BigDecimal custoSugerido;

    @Column(name = "requer_profissional_especifico")
    private Boolean requerProfissionalEspecifico = false;

    @Size(max = 100, message = "Profissional requerido deve ter no máximo 100 caracteres")
    @Column(name = "profissional_requerido", length = 100)
    private String profissionalRequerido;

    @Column(name = "requer_preparacao")
    private Boolean requerPreparacao = false;

    @Column(name = "instrucoes_preparacao", columnDefinition = "TEXT")
    private String instrucoesPreparacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
