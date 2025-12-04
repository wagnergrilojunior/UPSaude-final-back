package com.upsaude.entity;

import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.enums.TipoCnsEnum;
import com.upsaude.util.converter.EscolaridadeEnumConverter;
import com.upsaude.util.converter.EstadoCivilEnumConverter;
import com.upsaude.util.converter.IdentidadeGeneroEnumConverter;
import com.upsaude.util.converter.NacionalidadeEnumConverter;
import com.upsaude.util.converter.OrientacaoSexualEnumConverter;
import com.upsaude.util.converter.RacaCorEnumConverter;
import com.upsaude.util.converter.SexoEnumConverter;
import com.upsaude.util.converter.StatusPacienteEnumConverter;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumConverter;
import com.upsaude.util.converter.TipoCnsEnumConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_pacientes_cpf", columnNames = {"cpf"}),
           @UniqueConstraint(name = "uk_pacientes_cns", columnNames = {"cns"}),
           @UniqueConstraint(name = "uk_pacientes_email", columnNames = {"email"})
       },
       indexes = {
           @Index(name = "idx_pacientes_cpf", columnList = "cpf")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Paciente extends BaseEntityWithoutTenant {

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

    @Pattern(regexp = "^\\d{15}$", message = "CNS deve ter 15 dígitos")
    @Column(name = "cns", length = 15)
    private String cns;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Convert(converter = SexoEnumConverter.class)
    @Column(name = "sexo")
    private SexoEnum sexo;

    @Convert(converter = EstadoCivilEnumConverter.class)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "nome_mae", length = 100)
    private String nomeMae;

    @Column(name = "nome_pai", length = 100)
    private String nomePai;

    @Column(name = "responsavel_nome", length = 255)
    private String responsavelNome;

    @Pattern(regexp = "^\\d{11}$", message = "CPF do responsável deve ter 11 dígitos")
    @Column(name = "responsavel_cpf", length = 11)
    private String responsavelCpf;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone do responsável deve ter 10 ou 11 dígitos")
    @Column(name = "responsavel_telefone", length = 20)
    private String responsavelTelefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @Column(name = "numero_carteirinha", length = 50)
    private String numeroCarteirinha;

    @Column(name = "data_validade_carteirinha")
    private LocalDate dataValidadeCarteirinha;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    /**
     * Endereços do paciente.
     * Relacionamento OneToMany usando JoinTable.
     * Usa cascade PERSIST e MERGE para gerenciar endereços associados,
     * mas não remove endereços que podem ser compartilhados.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "pacientes_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "paciente_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();
    
    /** Raça/Cor conforme classificação IBGE */
    @Convert(converter = RacaCorEnumConverter.class)
    @Column(name = "raca_cor")
    private RacaCorEnum racaCor;

    /** Nacionalidade do paciente */
    @Convert(converter = NacionalidadeEnumConverter.class)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    /** País de nascimento */
    @Size(max = 100, message = "País de nascimento deve ter no máximo 100 caracteres")
    @Column(name = "pais_nascimento", length = 100)
    private String paisNascimento;

    /** Naturalidade (cidade de nascimento) */
    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    @Column(name = "naturalidade", length = 100)
    private String naturalidade;

    /** Código IBGE do município de nascimento */
    @Size(max = 7, message = "Código IBGE do município deve ter no máximo 7 caracteres")
    @Column(name = "municipio_nascimento_ibge", length = 7)
    private String municipioNascimentoIbge;

    /** Escolaridade do paciente */
    @Convert(converter = EscolaridadeEnumConverter.class)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    /** Ocupação/Profissão do paciente */
    @Size(max = 150, message = "Ocupação/Profissão deve ter no máximo 150 caracteres")
    @Column(name = "ocupacao_profissao", length = 150)
    private String ocupacaoProfissao;

    /** Indica se paciente está em situação de rua */
    @Column(name = "situacao_rua", nullable = false)
    private Boolean situacaoRua = false;

    /** Status do paciente no sistema de saúde */
    @Convert(converter = StatusPacienteEnumConverter.class)
    @Column(name = "status_paciente", nullable = false)
    private StatusPacienteEnum statusPaciente = StatusPacienteEnum.ATIVO;

    /** Data do óbito (quando statusPaciente = OBITO) */
    @Column(name = "data_obito")
    private LocalDate dataObito;

    /** CID-10 da causa do óbito */
    @Size(max = 10, message = "CID-10 da causa do óbito deve ter no máximo 10 caracteres")
    @Column(name = "causa_obito_cid10", length = 10)
    private String causaObitoCid10;

    /** Indica se CNS está ativo */
    @Column(name = "cartao_sus_ativo", nullable = false)
    private Boolean cartaoSusAtivo = true;

    /** Data da última atualização do CNS */
    @Column(name = "data_atualizacao_cns")
    private LocalDate dataAtualizacaoCns;

    /** Tipo de atendimento preferencial */
    @Convert(converter = TipoAtendimentoPreferencialEnumConverter.class)
    @Column(name = "tipo_atendimento_preferencial")
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;

    /** Origem do cadastro (e-SUS, SISAB, etc.) */
    @Size(max = 30, message = "Origem do cadastro deve ter no máximo 30 caracteres")
    @Column(name = "origem_cadastro", length = 30)
    private String origemCadastro;

    @Size(max = 255, message = "Nome social deve ter no máximo 255 caracteres")
    @Column(name = "nome_social", length = 255)
    private String nomeSocial;

    @Convert(converter = IdentidadeGeneroEnumConverter.class)
    @Column(name = "identidade_genero")
    private IdentidadeGeneroEnum identidadeGenero;

    @Convert(converter = OrientacaoSexualEnumConverter.class)
    @Column(name = "orientacao_sexual")
    private OrientacaoSexualEnum orientacaoSexual;

    @Column(name = "possui_deficiencia", nullable = false)
    private Boolean possuiDeficiencia = false;

    @Size(max = 255, message = "Tipo de deficiência deve ter no máximo 255 caracteres")
    @Column(name = "tipo_deficiencia", length = 255)
    private String tipoDeficiencia;

    @Column(name = "cns_validado", nullable = false)
    private Boolean cnsValidado = false;

    @Convert(converter = TipoCnsEnumConverter.class)
    @Column(name = "tipo_cns")
    private TipoCnsEnum tipoCns;

    @Column(name = "acompanhado_por_equipe_esf", nullable = false)
    private Boolean acompanhadoPorEquipeEsf = false;

    /** 
     * Dados sociodemográficos do paciente.
     * Relacionamento OneToOne bidirecional com cascade completo e remoção de órfãos.
     * O JPA gerencia automaticamente a persistência e sincronização.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DadosSociodemograficos dadosSociodemograficos;

    /** 
     * Dados clínicos básicos do paciente.
     * Relacionamento OneToOne bidirecional com cascade completo e remoção de órfãos.
     * O JPA gerencia automaticamente a persistência e sincronização.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DadosClinicosBasicos dadosClinicosBasicos;

    /** 
     * Responsável legal do paciente.
     * Relacionamento OneToOne bidirecional com cascade completo e remoção de órfãos.
     * O JPA gerencia automaticamente a persistência e sincronização.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ResponsavelLegal responsavelLegal;

    /** 
     * Consentimentos LGPD do paciente.
     * Relacionamento OneToOne bidirecional com cascade completo e remoção de órfãos.
     * O JPA gerencia automaticamente a persistência e sincronização.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private LGPDConsentimento lgpdConsentimento;

    /** 
     * Informações de integração com sistemas governamentais.
     * Relacionamento OneToOne bidirecional com cascade completo e remoção de órfãos.
     * O JPA gerencia automaticamente a persistência e sincronização.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private IntegracaoGov integracaoGov;

    /** 
     * Doenças do paciente.
     * Relacionamento OneToMany bidirecional com cascade completo e remoção de órfãos.
     * Permite registrar múltiplas doenças/comorbidades com informações de diagnóstico, 
     * acompanhamento e tratamento.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DoencasPaciente> doencas = new ArrayList<>();

    /** 
     * Alergias do paciente.
     * Relacionamento OneToMany bidirecional com cascade completo e remoção de órfãos.
     * Permite registrar múltiplas alergias com informações de diagnóstico e histórico de reações.
     * Alertas médicos importantes são exibidos no prontuário.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AlergiasPaciente> alergias = new ArrayList<>();

    /** 
     * Deficiências do paciente.
     * Relacionamento OneToMany bidirecional com cascade completo e remoção de órfãos.
     * Permite registrar múltiplas deficiências com informações de laudo e diagnóstico.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DeficienciasPaciente> deficiencias = new ArrayList<>();

    /** 
     * Medicações contínuas do paciente.
     * Relacionamento OneToMany bidirecional com cascade completo e remoção de órfãos.
     * Permite registrar múltiplas medicações em uso contínuo com informações de dose, 
     * frequência, via de administração e período de uso.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MedicacaoPaciente> medicacoes = new ArrayList<>();

}
