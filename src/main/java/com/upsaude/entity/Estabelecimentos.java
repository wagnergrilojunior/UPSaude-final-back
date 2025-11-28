package com.upsaude.entity;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    // Endereços secundários (para casos de múltiplas unidades no mesmo estabelecimento)
    @OneToMany(fetch = FetchType.LAZY)
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
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
    private List<ServicosEstabelecimento> servicos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
    private List<EquipamentosEstabelecimento> equipamentos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
    private List<InfraestruturaEstabelecimento> infraestrutura = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
    private List<EquipeSaude> equipes = new ArrayList<>();

    // ========== GEOLOCALIZAÇÃO ==========
    
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // ========== OBSERVAÇÕES E DADOS COMPLEMENTARES ==========
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "dados_complementares", columnDefinition = "jsonb")
    private String dadosComplementares;
}
