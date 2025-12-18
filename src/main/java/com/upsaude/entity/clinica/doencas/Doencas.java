package com.upsaude.entity.clinica.doencas;
import com.upsaude.entity.BaseEntityWithoutTenant;

import com.upsaude.entity.clinica.doencas.Doencas;

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

@Entity
@Table(name = "doencas", schema = "public",
       indexes = {
           @Index(name = "idx_doenca_nome", columnList = "nome"),
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

    @NotBlank(message = "Nome da doença é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    @Column(name = "nome_cientifico", length = 255)
    private String nomeCientifico;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Embedded
    private ClassificacaoDoenca classificacao;

    @Column(name = "cronica", nullable = false)
    private Boolean cronica = false;

    @Embedded
    private SintomasDoenca sintomas;

    @Embedded
    private TratamentoPadraoDoenca tratamentoPadrao;

    @Embedded
    private EpidemiologiaDoenca epidemiologia;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "causas", columnDefinition = "TEXT")
    private String causas;

    @Column(name = "fisiopatologia", columnDefinition = "TEXT")
    private String fisiopatologia;

    @Column(name = "prognostico", columnDefinition = "TEXT")
    private String prognostico;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

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
