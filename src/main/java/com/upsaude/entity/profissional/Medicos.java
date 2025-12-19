package com.upsaude.entity.profissional;
import com.upsaude.entity.BaseEntityWithoutEstabelecimento;

import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;

import com.upsaude.entity.paciente.Endereco;

import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class Medicos extends BaseEntityWithoutEstabelecimento {

    public Medicos() {
        this.dadosPessoais = new DadosPessoaisMedico();
        this.registroProfissional = new RegistroProfissionalMedico();
        this.formacao = new FormacaoMedico();
        this.contato = new ContatoMedico();
        this.enderecos = new ArrayList<>();
        this.medicosEstabelecimentos = new ArrayList<>();
    }

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Embedded
    private DadosPessoaisMedico dadosPessoais;

    @Embedded
    private RegistroProfissionalMedico registroProfissional;

    @Embedded
    private FormacaoMedico formacao;

    @Embedded
    private ContatoMedico contato;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "medicos_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MedicoEstabelecimento> medicosEstabelecimentos = new ArrayList<>();

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddablesAndCollections() {

        if (dadosPessoais == null) {
            dadosPessoais = new DadosPessoaisMedico();
        }
        if (registroProfissional == null) {
            registroProfissional = new RegistroProfissionalMedico();
        }
        if (formacao == null) {
            formacao = new FormacaoMedico();
        }
        if (contato == null) {
            contato = new ContatoMedico();
        }

        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }
        if (medicosEstabelecimentos == null) {
            medicosEstabelecimentos = new ArrayList<>();
        }
    }

    public void addMedicoEstabelecimento(MedicoEstabelecimento medicoEstabelecimento) {
        if (medicoEstabelecimento == null) {
            return;
        }
        if (medicosEstabelecimentos == null) {
            medicosEstabelecimentos = new ArrayList<>();
        }
        if (!medicosEstabelecimentos.contains(medicoEstabelecimento)) {
            medicosEstabelecimentos.add(medicoEstabelecimento);
            medicoEstabelecimento.setMedico(this);
        }
    }

    public void removeMedicoEstabelecimento(MedicoEstabelecimento medicoEstabelecimento) {
        if (medicoEstabelecimento == null || medicosEstabelecimentos == null) {
            return;
        }
        if (medicosEstabelecimentos.remove(medicoEstabelecimento)) {
            medicoEstabelecimento.setMedico(null);
        }
    }
}
