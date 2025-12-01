package com.upsaude.entity;

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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um catálogo de medicamentos conforme padrão SUS/SIGTAP.
 * Armazena informações completas sobre medicamentos para sistemas de prontuário eletrônico.
 * Baseado em padrões da ANVISA, SUS e sistemas de saúde.
 * Não possui relação com Tenant por ser uma entidade de referência global.
 *
 * @author UPSaúde
 */
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

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesMedicamento fabricanteEntity; // Relacionamento com entidade FabricantesMedicamento

    // ========== IDENTIFICAÇÃO ==========

    @Embedded
    private IdentificacaoMedicamento identificacao;

    // ========== DOSAGEM E ADMINISTRAÇÃO ==========

    @Embedded
    private DosagemAdministracaoMedicamento dosagemAdministracao;

    // ========== CLASSIFICAÇÃO ==========

    @Embedded
    private ClassificacaoMedicamento classificacao;

    // ========== REGISTRO E CONTROLE ==========

    @Embedded
    private RegistroControleMedicamento registroControle;

    // ========== CONTRAINDICAÇÕES E PRECAUÇÕES ==========

    @Embedded
    private ContraindicacoesPrecaucoesMedicamento contraindicacoesPrecaucoes;

    // ========== CONSERVAÇÃO E ARMAZENAMENTO ==========

    @Embedded
    private ConservacaoArmazenamentoMedicamento conservacaoArmazenamento;

    // ========== DESCRIÇÃO E OBSERVAÇÕES ==========

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao; // Descrição geral do medicamento

    @Column(name = "indicacoes", columnDefinition = "TEXT")
    private String indicacoes; // Indicações de uso

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais
}
