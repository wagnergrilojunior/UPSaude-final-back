package com.upsaude.entity;

import com.upsaude.entity.embeddable.ClassificacaoEspecialidadeMedica;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "especialidades_medicas", schema = "public",
       indexes = {
           @Index(name = "idx_especialidade_nome", columnList = "nome"),
           @Index(name = "idx_especialidade_codigo", columnList = "codigo"),
           @Index(name = "idx_especialidade_tipo", columnList = "tipo_especialidade")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EspecialidadesMedicas extends BaseEntityWithoutTenant {

    public EspecialidadesMedicas() {
        this.classificacao = new ClassificacaoEspecialidadeMedica();
    }

    @NotBlank(message = "Nome da especialidade é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    @Column(name = "nome_cientifico", length = 255)
    private String nomeCientifico;

    @Embedded
    private ClassificacaoEspecialidadeMedica classificacao;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "area_atuacao_descricao", columnDefinition = "TEXT")
    private String areaAtuacaoDescricao;

    @Column(name = "requisitos_formacao", columnDefinition = "TEXT")
    private String requisitosFormacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (classificacao == null) {
            classificacao = new ClassificacaoEspecialidadeMedica();
        }
    }
}
