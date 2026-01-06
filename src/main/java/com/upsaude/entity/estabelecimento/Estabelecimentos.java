package com.upsaude.entity.estabelecimento;

import com.upsaude.entity.BaseEntityWithoutEstabelecimento;
import com.upsaude.entity.embeddable.ContatoEstabelecimento;
import com.upsaude.entity.embeddable.DadosIdentificacaoEstabelecimento;
import com.upsaude.entity.embeddable.InfraestruturaFisicaEstabelecimento;
import com.upsaude.entity.embeddable.LicenciamentoEstabelecimento;
import com.upsaude.entity.embeddable.LocalizacaoEstabelecimento;
import com.upsaude.entity.embeddable.ResponsaveisEstabelecimento;
import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.entity.paciente.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estabelecimentos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_estabelecimentos_cnes_tenant", columnNames = {"codigo_cnes", "tenant_id"}),
           @UniqueConstraint(name = "uk_estabelecimentos_cnpj_tenant", columnNames = {"cnpj", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_estabelecimentos_cnes", columnList = "codigo_cnes"),
           @Index(name = "idx_estabelecimentos_cnpj", columnList = "cnpj"),
           @Index(name = "idx_estabelecimentos_nome", columnList = "nome")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Estabelecimentos extends BaseEntityWithoutEstabelecimento {

    @Embedded
    private DadosIdentificacaoEstabelecimento dadosIdentificacao;

    @Embedded
    private ContatoEstabelecimento contato;

    @Embedded
    private ResponsaveisEstabelecimento responsaveis;

    @Embedded
    private LicenciamentoEstabelecimento licenciamento;

    @Embedded
    private InfraestruturaFisicaEstabelecimento infraestruturaFisica;

    @Embedded
    private LocalizacaoEstabelecimento localizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipamentosEstabelecimento> equipamentos = new ArrayList<>();

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddablesAndCollections() {
        if (dadosIdentificacao == null) {
            dadosIdentificacao = new DadosIdentificacaoEstabelecimento();
        }
        if (contato == null) {
            contato = new ContatoEstabelecimento();
        }
        if (responsaveis == null) {
            responsaveis = new ResponsaveisEstabelecimento();
        }
        if (licenciamento == null) {
            licenciamento = new LicenciamentoEstabelecimento();
        }
        if (infraestruturaFisica == null) {
            infraestruturaFisica = new InfraestruturaFisicaEstabelecimento();
        }
        if (localizacao == null) {
            localizacao = new LocalizacaoEstabelecimento();
        }

        if (equipamentos == null) {
            equipamentos = new ArrayList<>();
        }
    }

    public void addEquipamento(EquipamentosEstabelecimento equipamento) {
        if (equipamento == null) {
            return;
        }
        if (equipamentos == null) {
            equipamentos = new ArrayList<>();
        }
        if (!equipamentos.contains(equipamento)) {
            equipamentos.add(equipamento);
            equipamento.setEstabelecimento(this);
        }
    }

    public void removeEquipamento(EquipamentosEstabelecimento equipamento) {
        if (equipamento == null || equipamentos == null) {
            return;
        }
        if (equipamentos.remove(equipamento)) {
            equipamento.setEstabelecimento(null);
        }
    }
}
