package com.upsaude.entity.estabelecimento.equipamento;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.embeddable.DadosIdentificacaoEquipamento;
import com.upsaude.entity.embeddable.DescricoesEquipamento;
import com.upsaude.entity.embeddable.EspecificacoesTecnicasEquipamento;
import com.upsaude.entity.embeddable.ManutencaoCalibracaoEquipamento;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "equipamentos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_equipamento_codigo_cnes", columnNames = {"codigo_cnes"}),
           @UniqueConstraint(name = "uk_equipamento_registro_anvisa", columnNames = {"registro_anvisa"})
       },
       indexes = {
           @Index(name = "idx_equipamento_nome", columnList = "nome"),
           @Index(name = "idx_equipamento_tipo", columnList = "tipo"),
           @Index(name = "idx_equipamento_fabricante", columnList = "fabricante_id"),
           @Index(name = "idx_equipamento_codigo_cnes", columnList = "codigo_cnes"),
           @Index(name = "idx_equipamento_status", columnList = "status")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Equipamentos extends BaseEntity {

    @Embedded
    private DadosIdentificacaoEquipamento dadosIdentificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesEquipamento fabricante;

    @Embedded
    private EspecificacoesTecnicasEquipamento especificacoesTecnicas;

    @Embedded
    private ManutencaoCalibracaoEquipamento manutencaoCalibracao;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusAtivoEnum status;

    @Column(name = "disponivel_uso", nullable = false)
    private Boolean disponivelUso = true;

    @Embedded
    private DescricoesEquipamento descricoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (dadosIdentificacao == null) {
            dadosIdentificacao = new DadosIdentificacaoEquipamento();
        }
        if (especificacoesTecnicas == null) {
            especificacoesTecnicas = new EspecificacoesTecnicasEquipamento();
        }
        if (manutencaoCalibracao == null) {
            manutencaoCalibracao = new ManutencaoCalibracaoEquipamento();
        }
        if (descricoes == null) {
            descricoes = new DescricoesEquipamento();
        }
    }
}
