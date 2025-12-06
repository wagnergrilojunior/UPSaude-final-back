package com.upsaude.entity;

import com.upsaude.entity.embeddable.ClassificacaoAlergia;
import com.upsaude.entity.embeddable.PrevencaoTratamentoAlergia;
import com.upsaude.entity.embeddable.ReacoesAlergia;

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
 * Entidade que representa um catálogo de alergias.
 * Armazena informações completas sobre alergias para sistemas de gestão de saúde.
 * Baseado em padrões médicos e sistemas de saúde.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "alergias", schema = "public",
       indexes = {
           @Index(name = "idx_alergia_nome", columnList = "nome"),
           @Index(name = "idx_alergia_tipo", columnList = "tipo_alergia"),
           @Index(name = "idx_alergia_codigo_cid", columnList = "codigo_cid")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Alergias extends BaseEntityWithoutTenant {
	
	public Alergias() {
        this.classificacao = new ClassificacaoAlergia();
        this.reacoes = new ReacoesAlergia();
        this.prevencaoTratamento = new PrevencaoTratamentoAlergia();
    }

    // ========== IDENTIFICAÇÃO BÁSICA ==========

    @NotBlank(message = "Nome da alergia é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    @Column(name = "nome_cientifico", length = 255)
    private String nomeCientifico; // Nome científico da substância alergênica

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno; // Código interno do sistema

    // ========== CLASSIFICAÇÃO ==========

    @Embedded
    private ClassificacaoAlergia classificacao;

    // ========== REAÇÕES ==========

    @Embedded
    private ReacoesAlergia reacoes;

    // ========== PREVENÇÃO E TRATAMENTO ==========

    @Embedded
    private PrevencaoTratamentoAlergia prevencaoTratamento;

    // ========== DESCRIÇÃO E OBSERVAÇÕES ==========

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao; // Descrição geral da alergia

    @Column(name = "substancias_relacionadas", columnDefinition = "TEXT")
    private String substanciasRelacionadas; // Substâncias relacionadas que também podem causar alergia

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (classificacao == null) {
            classificacao = new ClassificacaoAlergia();
        }
        if (reacoes == null) {
            reacoes = new ReacoesAlergia();
        }
        if (prevencaoTratamento == null) {
            prevencaoTratamento = new PrevencaoTratamentoAlergia();
        }
    }
}
