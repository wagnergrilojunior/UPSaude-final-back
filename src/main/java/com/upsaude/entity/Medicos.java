package com.upsaude.entity;

import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
           @Index(name = "idx_medicos_especialidade", columnList = "especialidade_id"),
           @Index(name = "idx_medicos_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Medicos extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medicos_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais sobre o médico
}
