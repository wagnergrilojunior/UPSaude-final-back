package com.upsaude.entity;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um estabelecimento de saúde.
 * Armazena dados completos para integração com CNES e gestão de unidades de saúde.
 *
 * @author UPSaúde
 */
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
@Data
@EqualsAndHashCode(callSuper = true)
public class Estabelecimentos extends BaseEntityWithoutEstabelecimento {

    /**
     * Construtor padrão que inicializa as coleções para evitar NullPointerException.
     */
    public Estabelecimentos() {
        this.enderecosSecundarios = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.equipamentos = new ArrayList<>();
        this.infraestrutura = new ArrayList<>();
        this.equipes = new ArrayList<>();
    }

    // ========== IDENTIFICAÇÃO BÁSICA ==========
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 100)
    private TipoEstabelecimentoEnum tipo;

    // ========== IDENTIFICAÇÃO OFICIAL (CNES) ==========
    
    @Size(max = 7, message = "Código CNES deve ter no máximo 7 caracteres")
    @Column(name = "codigo_cnes", length = 7, unique = false)
    private String codigoCnes;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", length = 14, unique = false)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "natureza_juridica", length = 50)
    private NaturezaJuridicaEnum naturezaJuridica;

    @Size(max = 50, message = "Registro oficial deve ter no máximo 50 caracteres")
    @Column(name = "registro_oficial", length = 50)
    private String registroOficial;

    // ========== ENDEREÇO PRINCIPAL ==========
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_principal_id")
    private Endereco enderecoPrincipal;

    /**
     * Endereços secundários (para casos de múltiplas unidades no mesmo estabelecimento).
     * Cascade PERSIST/MERGE para gerenciar associações.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @jakarta.persistence.JoinTable(
        name = "estabelecimentos_enderecos_secundarios",
        schema = "public",
        joinColumns = @JoinColumn(name = "estabelecimento_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecosSecundarios = new ArrayList<>();

    // ========== CONTATO INSTITUCIONAL ==========
    
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone secundário deve ter 10 ou 11 dígitos")
    @Column(name = "telefone_secundario", length = 20)
    private String telefoneSecundario;

    @Pattern(regexp = "^\\d{10,11}$", message = "Fax deve ter 10 ou 11 dígitos")
    @Column(name = "fax", length = 20)
    private String fax;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 500, message = "Site deve ter no máximo 500 caracteres")
    @Column(name = "site", length = 500)
    private String site;

    // ========== RESPONSÁVEIS ==========
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_tecnico_id")
    private ProfissionaisSaude responsavelTecnico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_administrativo_id")
    private ProfissionaisSaude responsavelAdministrativo;

    @Size(max = 255, message = "Nome do responsável legal deve ter no máximo 255 caracteres")
    @Column(name = "responsavel_legal_nome", length = 255)
    private String responsavelLegalNome;

    @Pattern(regexp = "^\\d{11}$", message = "CPF do responsável legal deve ter 11 dígitos")
    @Column(name = "responsavel_legal_cpf", length = 11)
    private String responsavelLegalCpf;

    // ========== STATUS E LICENCIAMENTO ==========
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status_funcionamento", length = 50)
    private StatusFuncionamentoEnum statusFuncionamento;

    @Column(name = "data_abertura")
    private OffsetDateTime dataAbertura;

    @Column(name = "data_licenciamento")
    private OffsetDateTime dataLicenciamento;

    @Column(name = "data_validade_licenca")
    private OffsetDateTime dataValidadeLicenca;

    @Size(max = 100, message = "Número do alvará deve ter no máximo 100 caracteres")
    @Column(name = "numero_alvara", length = 100)
    private String numeroAlvara;

    @Size(max = 100, message = "Número da licença sanitária deve ter no máximo 100 caracteres")
    @Column(name = "numero_licenca_sanitaria", length = 100)
    private String numeroLicencaSanitaria;

    @Column(name = "data_validade_licenca_sanitaria")
    private OffsetDateTime dataValidadeLicencaSanitaria;

    // ========== CAPACIDADE E INFRAESTRUTURA ==========
    
    @Column(name = "quantidade_leitos")
    private Integer quantidadeLeitos;

    @Column(name = "quantidade_consultorios")
    private Integer quantidadeConsultorios;

    @Column(name = "quantidade_salas_cirurgia")
    private Integer quantidadeSalasCirurgia;

    @Column(name = "quantidade_ambulatorios")
    private Integer quantidadeAmbulatorios;

    @Column(name = "area_construida_metros_quadrados")
    private Double areaConstruidaMetrosQuadrados;

    @Column(name = "area_total_metros_quadrados")
    private Double areaTotalMetrosQuadrados;

    // ========== ESPECIALIDADES E SERVIÇOS ==========
    
    /**
     * Serviços oferecidos pelo estabelecimento.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicosEstabelecimento> servicos = new ArrayList<>();

    /**
     * Equipamentos do estabelecimento.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipamentosEstabelecimento> equipamentos = new ArrayList<>();

    /**
     * Infraestrutura do estabelecimento.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InfraestruturaEstabelecimento> infraestrutura = new ArrayList<>();

    /**
     * Equipes de saúde do estabelecimento.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipeSaude> equipes = new ArrayList<>();

    // ========== GEOLOCALIZAÇÃO ==========
    
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // ========== OBSERVAÇÕES E DADOS COMPLEMENTARES ==========
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // ========== MÉTODOS DE CICLO DE VIDA ==========

    /**
     * Garante que as coleções não sejam nulas antes de persistir ou atualizar.
     * Recria as listas se estiverem nulas.
     */
    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (enderecosSecundarios == null) {
            enderecosSecundarios = new ArrayList<>();
        }
        if (servicos == null) {
            servicos = new ArrayList<>();
        }
        if (equipamentos == null) {
            equipamentos = new ArrayList<>();
        }
        if (infraestrutura == null) {
            infraestrutura = new ArrayList<>();
        }
        if (equipes == null) {
            equipes = new ArrayList<>();
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - SERVIÇOS ==========

    /**
     * Adiciona um serviço ao estabelecimento com sincronização bidirecional.
     * Garante que o serviço também referencia este estabelecimento.
     *
     * @param servico O serviço a ser adicionado
     */
    public void addServico(ServicosEstabelecimento servico) {
        if (servico == null) {
            return;
        }
        if (servicos == null) {
            servicos = new ArrayList<>();
        }
        if (!servicos.contains(servico)) {
            servicos.add(servico);
            servico.setEstabelecimento(this);
        }
    }

    /**
     * Remove um serviço do estabelecimento com sincronização bidirecional.
     * Remove a referência do serviço para este estabelecimento.
     *
     * @param servico O serviço a ser removido
     */
    public void removeServico(ServicosEstabelecimento servico) {
        if (servico == null || servicos == null) {
            return;
        }
        if (servicos.remove(servico)) {
            servico.setEstabelecimento(null);
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - EQUIPAMENTOS ==========

    /**
     * Adiciona um equipamento ao estabelecimento com sincronização bidirecional.
     * Garante que o equipamento também referencia este estabelecimento.
     *
     * @param equipamento O equipamento a ser adicionado
     */
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

    /**
     * Remove um equipamento do estabelecimento com sincronização bidirecional.
     * Remove a referência do equipamento para este estabelecimento.
     *
     * @param equipamento O equipamento a ser removido
     */
    public void removeEquipamento(EquipamentosEstabelecimento equipamento) {
        if (equipamento == null || equipamentos == null) {
            return;
        }
        if (equipamentos.remove(equipamento)) {
            equipamento.setEstabelecimento(null);
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - INFRAESTRUTURA ==========

    /**
     * Adiciona uma infraestrutura ao estabelecimento com sincronização bidirecional.
     * Garante que a infraestrutura também referencia este estabelecimento.
     *
     * @param infraestruturaItem A infraestrutura a ser adicionada
     */
    public void addInfraestrutura(InfraestruturaEstabelecimento infraestruturaItem) {
        if (infraestruturaItem == null) {
            return;
        }
        if (infraestrutura == null) {
            infraestrutura = new ArrayList<>();
        }
        if (!infraestrutura.contains(infraestruturaItem)) {
            infraestrutura.add(infraestruturaItem);
            infraestruturaItem.setEstabelecimento(this);
        }
    }

    /**
     * Remove uma infraestrutura do estabelecimento com sincronização bidirecional.
     * Remove a referência da infraestrutura para este estabelecimento.
     *
     * @param infraestruturaItem A infraestrutura a ser removida
     */
    public void removeInfraestrutura(InfraestruturaEstabelecimento infraestruturaItem) {
        if (infraestruturaItem == null || infraestrutura == null) {
            return;
        }
        if (infraestrutura.remove(infraestruturaItem)) {
            infraestruturaItem.setEstabelecimento(null);
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - EQUIPES ==========

    /**
     * Adiciona uma equipe ao estabelecimento com sincronização bidirecional.
     * Garante que a equipe também referencia este estabelecimento.
     *
     * @param equipe A equipe a ser adicionada
     */
    public void addEquipe(EquipeSaude equipe) {
        if (equipe == null) {
            return;
        }
        if (equipes == null) {
            equipes = new ArrayList<>();
        }
        if (!equipes.contains(equipe)) {
            equipes.add(equipe);
            equipe.setEstabelecimento(this);
        }
    }

    /**
     * Remove uma equipe do estabelecimento com sincronização bidirecional.
     * Remove a referência da equipe para este estabelecimento.
     *
     * @param equipe A equipe a ser removida
     */
    public void removeEquipe(EquipeSaude equipe) {
        if (equipe == null || equipes == null) {
            return;
        }
        if (equipes.remove(equipe)) {
            equipe.setEstabelecimento(null);
        }
    }
}
