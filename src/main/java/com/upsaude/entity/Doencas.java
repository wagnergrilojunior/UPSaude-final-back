package com.upsaude.entity;

import com.upsaude.entity.embeddable.ClassificacaoDoenca;
import com.upsaude.entity.embeddable.EpidemiologiaDoenca;
import com.upsaude.entity.embeddable.SintomasDoenca;
import com.upsaude.entity.embeddable.TratamentoPadraoDoenca;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um catálogo de doenças.
 * Armazena informações completas sobre doenças para sistemas de gestão de saúde.
 * Baseado em padrões da OMS, CID-10 e sistemas de saúde pública.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "doencas", schema = "public",
       indexes = {
           @Index(name = "idx_doenca_nome", columnList = "nome"),
           @Index(name = "idx_doenca_codigo_cid", columnList = "codigo_cid_principal"),
           @Index(name = "idx_doenca_cronica", columnList = "cronica")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Doencas extends BaseEntityWithoutTenant {

    public Doencas() {
        this.classificacao = new ClassificacaoDoenca();
        this.sintomas = new SintomasDoenca();
        this.tratamentoPadrao = new TratamentoPadraoDoenca();
        this.epidemiologia = new EpidemiologiaDoenca();
    }

    // ========== IDENTIFICAÇÃO BÁSICA ==========

    @NotBlank(message = "Nome da doença é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    @Column(name = "nome_cientifico", length = 255)
    private String nomeCientifico; // Nome científico da doença

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno; // Código interno do sistema

    // ========== CLASSIFICAÇÃO ==========

    @Embedded
    private ClassificacaoDoenca classificacao;

    @Column(name = "cronica", nullable = false)
    private Boolean cronica = false; // Indica se a doença é crônica

    // ========== RELACIONAMENTO COM CID ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_principal_id")
    private CidDoencas cidPrincipal; // CID principal relacionado

    // ========== SINTOMAS ==========

    @Embedded
    private SintomasDoenca sintomas;

    // ========== TRATAMENTO PADRÃO ==========

    @Embedded
    private TratamentoPadraoDoenca tratamentoPadrao;

    // ========== EPIDEMIOLOGIA ==========

    @Embedded
    private EpidemiologiaDoenca epidemiologia;

    // ========== DESCRIÇÃO E OBSERVAÇÕES ==========

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao; // Descrição geral da doença

    @Column(name = "causas", columnDefinition = "TEXT")
    private String causas; // Causas conhecidas da doença

    @Column(name = "fisiopatologia", columnDefinition = "TEXT")
    private String fisiopatologia; // Fisiopatologia da doença

    @Column(name = "prognostico", columnDefinition = "TEXT")
    private String prognostico; // Prognóstico geral

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (classificacao == null) {
            classificacao = new ClassificacaoDoenca();
        }
        if (sintomas == null) {
            sintomas = new SintomasDoenca();
        }
        if (tratamentoPadrao == null) {
            tratamentoPadrao = new TratamentoPadraoDoenca();
        }
        if (epidemiologia == null) {
            epidemiologia = new EpidemiologiaDoenca();
        }
    }
}

