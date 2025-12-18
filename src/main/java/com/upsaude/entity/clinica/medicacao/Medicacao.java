package com.upsaude.entity.clinica.medicacao;
import com.upsaude.entity.BaseEntityWithoutTenant;

import com.upsaude.entity.embeddable.ClassificacaoMedicamento;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.entity.embeddable.ContraindicacoesPrecaucoesMedicamento;
import com.upsaude.entity.embeddable.DosagemAdministracaoMedicamento;
import com.upsaude.entity.embeddable.IdentificacaoMedicamento;
import com.upsaude.entity.embeddable.RegistroControleMedicamento;
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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "medicacoes", schema = "public",
       indexes = {
           @Index(name = "idx_medicacao_principio_ativo", columnList = "principio_ativo"),
           @Index(name = "idx_medicacao_nome_comercial", columnList = "nome_comercial"),
           @Index(name = "idx_medicacao_catmat", columnList = "catmat_codigo"),
           @Index(name = "idx_medicacao_anvisa", columnList = "codigo_anvisa"),
           @Index(name = "idx_medicacao_classe_terapeutica", columnList = "classe_terapeutica"),
           @Index(name = "idx_medicacao_controlado", columnList = "controlado")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Medicacao extends BaseEntityWithoutTenant {

    public Medicacao() {
        this.identificacao = new IdentificacaoMedicamento();
        this.dosagemAdministracao = new DosagemAdministracaoMedicamento();
        this.classificacao = new ClassificacaoMedicamento();
        this.registroControle = new RegistroControleMedicamento();
        this.contraindicacoesPrecaucoes = new ContraindicacoesPrecaucoesMedicamento();
        this.conservacaoArmazenamento = new ConservacaoArmazenamentoMedicamento();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesMedicamento fabricanteEntity;

    @Embedded
    private IdentificacaoMedicamento identificacao;

    @Embedded
    private DosagemAdministracaoMedicamento dosagemAdministracao;

    @Embedded
    private ClassificacaoMedicamento classificacao;

    @Embedded
    private RegistroControleMedicamento registroControle;

    @Embedded
    private ContraindicacoesPrecaucoesMedicamento contraindicacoesPrecaucoes;

    @Embedded
    private ConservacaoArmazenamentoMedicamento conservacaoArmazenamento;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "indicacoes", columnDefinition = "TEXT")
    private String indicacoes;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (identificacao == null) {
            identificacao = new IdentificacaoMedicamento();
        }
        if (dosagemAdministracao == null) {
            dosagemAdministracao = new DosagemAdministracaoMedicamento();
        }
        if (classificacao == null) {
            classificacao = new ClassificacaoMedicamento();
        }
        if (registroControle == null) {
            registroControle = new RegistroControleMedicamento();
        }
        if (contraindicacoesPrecaucoes == null) {
            contraindicacoesPrecaucoes = new ContraindicacoesPrecaucoesMedicamento();
        }
        if (conservacaoArmazenamento == null) {
            conservacaoArmazenamento = new ConservacaoArmazenamentoMedicamento();
        }
    }
}
