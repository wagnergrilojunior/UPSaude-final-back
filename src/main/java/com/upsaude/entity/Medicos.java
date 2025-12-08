package com.upsaude.entity;

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
import jakarta.persistence.ManyToOne;
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

/**
 * Entidade que representa um médico.
 * Armazena informações completas sobre médicos para sistemas de gestão de saúde.
 * Baseado em padrões do Conselho Federal de Medicina (CFM).
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "medicos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_medicos_crm_uf_tenant", columnNames = {"crm", "crm_uf", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_medicos_crm", columnList = "crm"),
           @Index(name = "idx_medicos_crm_uf", columnList = "crm_uf"),
           @Index(name = "idx_medicos_nome", columnList = "nome_completo"),
           @Index(name = "idx_medicos_especialidade", columnList = "especialidade_id")
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

    // ========== RELACIONAMENTOS ==========

    /**
     * Especialidade médica principal do médico.
     * Relacionamento ManyToOne - não usa cascade pois especialidades são entidades independentes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    // ========== IDENTIFICAÇÃO BÁSICA ==========

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    // ========== DADOS PESSOAIS ==========

    @Embedded
    private DadosPessoaisMedico dadosPessoais;

    // ========== REGISTRO PROFISSIONAL (CRM) ==========

    @Embedded
    private RegistroProfissionalMedico registroProfissional;

    // ========== FORMAÇÃO ==========

    @Embedded
    private FormacaoMedico formacao;

    // ========== CONTATO ==========

    @Embedded
    private ContatoMedico contato;

    // ========== ENDEREÇOS ==========

    /**
     * Endereços do médico (consultório, residência, etc).
     * Relacionamento OneToMany usando JoinTable.
     * Usa cascade PERSIST e MERGE para gerenciar endereços associados,
     * mas não remove endereços que podem ser compartilhados.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "medicos_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

    /**
     * Vínculos do médico com estabelecimentos.
     * Relacionamento OneToMany bidirecional com cascade completo e remoção de órfãos.
     * Permite gerenciar múltiplos vínculos do médico com diferentes estabelecimentos,
     * incluindo tipo de vínculo, carga horária, salário e período de trabalho.
     */
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MedicoEstabelecimento> medicosEstabelecimentos = new ArrayList<>();

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais sobre o médico

    @PrePersist
    @PreUpdate
    public void validateEmbeddablesAndCollections() {
        // Valida embeddables
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
        
        // Valida coleções
        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }
        if (medicosEstabelecimentos == null) {
            medicosEstabelecimentos = new ArrayList<>();
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - MEDICOS ESTABELECIMENTOS ==========

    /**
     * Adiciona um vínculo médico-estabelecimento com sincronização bidirecional.
     * Garante que o vínculo também referencia este médico.
     *
     * @param medicoEstabelecimento O vínculo a ser adicionado
     */
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

    /**
     * Remove um vínculo médico-estabelecimento com sincronização bidirecional.
     * Remove a referência do vínculo para este médico.
     *
     * @param medicoEstabelecimento O vínculo a ser removido
     */
    public void removeMedicoEstabelecimento(MedicoEstabelecimento medicoEstabelecimento) {
        if (medicoEstabelecimento == null || medicosEstabelecimentos == null) {
            return;
        }
        if (medicosEstabelecimentos.remove(medicoEstabelecimento)) {
            medicoEstabelecimento.setMedico(null);
        }
    }
}
