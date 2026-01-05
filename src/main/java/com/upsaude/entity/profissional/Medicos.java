package com.upsaude.entity.profissional;

import com.upsaude.entity.BaseEntityWithoutEstabelecimento;
import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosDemograficosMedico;
import com.upsaude.entity.embeddable.DadosPessoaisBasicosMedico;
import com.upsaude.entity.embeddable.DocumentosBasicosMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "medicos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_medicos_crm_uf_tenant", columnNames = {"crm", "crm_uf", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_medicos_crm", columnList = "crm"),
           @Index(name = "idx_medicos_crm_uf", columnList = "crm_uf"),
           @Index(name = "idx_medicos_nome", columnList = "nome_completo")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Medicos extends BaseEntityWithoutEstabelecimento {

    @Embedded
    private DadosPessoaisBasicosMedico dadosPessoaisBasicos;

    @Embedded
    private DocumentosBasicosMedico documentosBasicos;

    @Embedded
    private DadosDemograficosMedico dadosDemograficos;

    @Embedded
    private RegistroProfissionalMedico registroProfissional;

    @Embedded
    private ContatoMedico contato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_medico_id")
    private Endereco enderecoMedico;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MedicoEstabelecimento> estabelecimentos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "medicos_especialidades",
        schema = "public",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidade_id"),
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_medico_especialidade", columnNames = {"medico_id", "especialidade_id"})
        },
        indexes = {
            @Index(name = "idx_medico_especialidade_medico", columnList = "medico_id"),
            @Index(name = "idx_medico_especialidade_ocupacao", columnList = "especialidade_id")
        }
    )
    private List<SigtapOcupacao> especialidades = new ArrayList<>();

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddablesAndCollections() {
        if (dadosPessoaisBasicos == null) {
            dadosPessoaisBasicos = new DadosPessoaisBasicosMedico();
        }
        if (documentosBasicos == null) {
            documentosBasicos = new DocumentosBasicosMedico();
        }
        if (dadosDemograficos == null) {
            dadosDemograficos = new DadosDemograficosMedico();
        }
        if (registroProfissional == null) {
            registroProfissional = new RegistroProfissionalMedico();
        }
        if (contato == null) {
            contato = new ContatoMedico();
        }

        if (estabelecimentos == null) {
            estabelecimentos = new ArrayList<>();
        }
        if (especialidades == null) {
            especialidades = new ArrayList<>();
        }
    }

    public void addMedicoEstabelecimento(MedicoEstabelecimento medicoEstabelecimento) {
        if (medicoEstabelecimento == null) {
            return;
        }
        if (estabelecimentos == null) {
            estabelecimentos = new ArrayList<>();
        }
        if (!estabelecimentos.contains(medicoEstabelecimento)) {
            estabelecimentos.add(medicoEstabelecimento);
            medicoEstabelecimento.setMedico(this);
        }
    }

    public void removeMedicoEstabelecimento(MedicoEstabelecimento medicoEstabelecimento) {
        if (medicoEstabelecimento == null || estabelecimentos == null) {
            return;
        }
        if (estabelecimentos.remove(medicoEstabelecimento)) {
            medicoEstabelecimento.setMedico(null);
        }
    }

    public void addEspecialidade(SigtapOcupacao especialidade) {
        if (especialidade == null) {
            return;
        }
        if (especialidades == null) {
            especialidades = new ArrayList<>();
        }
        if (!especialidades.contains(especialidade)) {
            especialidades.add(especialidade);
        }
    }

    public void removeEspecialidade(SigtapOcupacao especialidade) {
        if (especialidade == null || especialidades == null) {
            return;
        }
        especialidades.remove(especialidade);
    }
}
