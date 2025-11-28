package com.upsaude.entity;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.enums.TipoProfissionalEnum;
import com.upsaude.util.converter.EscolaridadeEnumConverter;
import com.upsaude.util.converter.EstadoCivilEnumConverter;
import com.upsaude.util.converter.IdentidadeGeneroEnumConverter;
import com.upsaude.util.converter.NacionalidadeEnumConverter;
import com.upsaude.util.converter.RacaCorEnumConverter;
import com.upsaude.util.converter.SexoEnumConverter;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import com.upsaude.util.converter.TipoDeficienciaEnumConverter;
import com.upsaude.util.converter.TipoProfissionalEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entidade que representa um profissional de saúde.
 * Armazena dados completos para registro profissional e compatibilidade com regulamentação de saúde pública.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "profissionais_saude", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_profissional_cpf", columnNames = {"cpf"}),
           @UniqueConstraint(name = "uk_profissional_registro_conselho", columnNames = {"registro_profissional", "conselho_id", "uf_registro"})
       },
       indexes = {
           @Index(name = "idx_profissional_cpf", columnList = "cpf"),
           @Index(name = "idx_profissional_registro", columnList = "registro_profissional"),
           @Index(name = "idx_profissional_conselho", columnList = "conselho_id"),
           @Index(name = "idx_profissional_status_registro", columnList = "status_registro"),
           @Index(name = "idx_profissional_cbo", columnList = "codigo_cbo")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ProfissionaisSaude extends BaseEntity {

    // ========== DADOS PESSOAIS ==========

    @Column(name = "nome_completo", nullable = false, length = 255)
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;

    @Column(name = "cpf", length = 11, unique = true)
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Convert(converter = SexoEnumConverter.class)
    @Column(name = "sexo")
    private SexoEnum sexo;

    @Convert(converter = EstadoCivilEnumConverter.class)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @Convert(converter = EscolaridadeEnumConverter.class)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    @Convert(converter = IdentidadeGeneroEnumConverter.class)
    @Column(name = "identidade_genero")
    private IdentidadeGeneroEnum identidadeGenero;

    @Convert(converter = RacaCorEnumConverter.class)
    @Column(name = "raca_cor")
    private RacaCorEnum racaCor;

    @Column(name = "tem_deficiencia", nullable = false)
    private Boolean temDeficiencia = false;

    @Convert(converter = TipoDeficienciaEnumConverter.class)
    @Column(name = "tipo_deficiencia")
    private TipoDeficienciaEnum tipoDeficiencia;

    // ========== DOCUMENTO DE IDENTIDADE ADICIONAL ==========

    @Column(name = "rg", length = 20)
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @Column(name = "orgao_emissor_rg", length = 50)
    @Size(max = 50, message = "Órgão emissor do RG deve ter no máximo 50 caracteres")
    private String orgaoEmissorRg;

    @Column(name = "uf_emissao_rg", length = 2)
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF de emissão do RG deve ter 2 letras maiúsculas")
    private String ufEmissaoRg;

    @Column(name = "data_emissao_rg")
    private LocalDate dataEmissaoRg;

    // ========== DADOS COMPLEMENTARES ==========

    @Convert(converter = NacionalidadeEnumConverter.class)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    @Column(name = "naturalidade", length = 100)
    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    private String naturalidade; // Cidade de nascimento

    // ========== REGISTRO PROFISSIONAL ==========

    @Column(name = "registro_profissional", nullable = false, length = 20)
    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro deve ter no máximo 20 caracteres")
    private String registroProfissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conselho_id", nullable = false)
    @NotNull(message = "Conselho profissional é obrigatório")
    private ConselhosProfissionais conselho;

    @Column(name = "uf_registro", length = 2)
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF do registro deve ter 2 letras maiúsculas")
    private String ufRegistro;

    @Column(name = "data_emissao_registro")
    private OffsetDateTime dataEmissaoRegistro;

    @Column(name = "data_validade_registro")
    private OffsetDateTime dataValidadeRegistro;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status_registro")
    private StatusAtivoEnum statusRegistro;

    // ========== TIPO PROFISSIONAL ==========

    @Convert(converter = TipoProfissionalEnumConverter.class)
    @Column(name = "tipo_profissional")
    private TipoProfissionalEnum tipoProfissional;

    // ========== ESPECIALIDADES ==========

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "profissionais_saude_especialidades",
        schema = "public",
        joinColumns = @JoinColumn(name = "profissional_saude_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private List<EspecialidadesMedicas> especialidades = new ArrayList<>();

    // ========== IDENTIFICADORES NACIONAIS ==========

    @Column(name = "cns", length = 15)
    @Size(max = 15, message = "CNS deve ter no máximo 15 caracteres")
    private String cns; // Cartão Nacional de Saúde

    // ========== CLASSIFICAÇÃO BRASILEIRA DE OCUPAÇÕES (CBO) ==========

    @Column(name = "codigo_cbo", length = 10)
    @Size(max = 10, message = "Código CBO deve ter no máximo 10 caracteres")
    private String codigoCbo; // Código segundo a Classificação Brasileira de Ocupações

    @Column(name = "descricao_cbo", length = 255)
    @Size(max = 255, message = "Descrição CBO deve ter no máximo 255 caracteres")
    private String descricaoCbo; // Descrição da ocupação segundo CBO

    // ========== CÓDIGO OCUPACIONAL / FUNÇÃO ATUAL ==========

    @Column(name = "codigo_ocupacional", length = 50)
    @Size(max = 50, message = "Código ocupacional deve ter no máximo 50 caracteres")
    private String codigoOcupacional; // Código ocupacional/função atual dentro da unidade (se diferente da especialidade)

    // ========== CONTATO PESSOAL ==========

    @Column(name = "telefone", length = 20)
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Column(name = "email", length = 255)
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    // ========== CONTATO INSTITUCIONAL ==========

    @Column(name = "telefone_institucional", length = 20)
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone institucional deve ter 10 ou 11 dígitos")
    private String telefoneInstitucional;

    @Column(name = "email_institucional", length = 255)
    @Email(message = "Email institucional inválido")
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    private String emailInstitucional;

    // ========== ENDEREÇO PROFISSIONAL ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_profissional_id")
    private Endereco enderecoProfissional;

    // ========== VÍNCULOS COM ESTABELECIMENTOS ==========

    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY)
    private Set<ProfissionalEstabelecimento> vinculosEstabelecimentos = new HashSet<>();

    // ========== VÍNCULOS COM EQUIPES ==========

    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY)
    private Set<VinculoProfissionalEquipe> vinculosEquipes = new HashSet<>();

    // ========== HISTÓRICO DE HABILITAÇÃO ==========

    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY)
    private Set<HistoricoHabilitacaoProfissional> historicoHabilitacao = new HashSet<>();

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
