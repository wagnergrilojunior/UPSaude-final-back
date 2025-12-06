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

/**
 * Entidade que representa uma especialidade médica.
 * Armazena informações completas sobre especialidades médicas para sistemas de gestão de saúde.
 * Baseado em padrões do Conselho Federal de Medicina (CFM).
 *
 * @author UPSaúde
 */
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

    // ========== IDENTIFICAÇÃO BÁSICA ==========

    @NotBlank(message = "Nome da especialidade é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo; // Código interno ou código CFM

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    @Column(name = "nome_cientifico", length = 255)
    private String nomeCientifico; // Nome científico da especialidade

    // ========== CLASSIFICAÇÃO ==========

    @Embedded
    private ClassificacaoEspecialidadeMedica classificacao;

    // ========== DESCRIÇÃO E OBSERVAÇÕES ==========

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao; // Descrição geral da especialidade

    @Column(name = "area_atuacao_descricao", columnDefinition = "TEXT")
    private String areaAtuacaoDescricao; // Descrição detalhada da área de atuação

    @Column(name = "requisitos_formacao", columnDefinition = "TEXT")
    private String requisitosFormacao; // Requisitos de formação para a especialidade

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (classificacao == null) {
            classificacao = new ClassificacaoEspecialidadeMedica();
        }
    }
}
