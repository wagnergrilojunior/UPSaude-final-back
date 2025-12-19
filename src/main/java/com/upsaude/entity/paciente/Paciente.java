package com.upsaude.entity.paciente;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.BaseEntityWithoutTenant;

import com.upsaude.entity.paciente.alergia.AlergiasPaciente;
import com.upsaude.entity.paciente.deficiencia.DeficienciasPaciente;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.LGPDConsentimento;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;

import com.upsaude.entity.paciente.Endereco;

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

           @Index(name = "idx_pacientes_cpf", columnList = "cpf"),
           @Index(name = "idx_pacientes_email", columnList = "email"),
           @Index(name = "idx_pacientes_cns", columnList = "cns"),
           @Index(name = "idx_pacientes_rg", columnList = "rg"),
           @Index(name = "idx_pacientes_nome_completo", columnList = "nome_completo"),
           @Index(name = "idx_pacientes_data_nascimento", columnList = "data_nascimento"),
           @Index(name = "idx_pacientes_status_paciente", columnList = "status_paciente"),
           @Index(name = "idx_pacientes_ativo", columnList = "ativo"),
           @Index(name = "idx_pacientes_convenio", columnList = "convenio_id"),
           @Index(name = "idx_pacientes_criado_em", columnList = "criado_em"),
           @Index(name = "idx_pacientes_atualizado_em", columnList = "atualizado_em"),

           @Index(name = "idx_pacientes_ativo_nome", columnList = "ativo, nome_completo"),
           @Index(name = "idx_pacientes_status_ativo", columnList = "status_paciente, ativo"),
           @Index(name = "idx_pacientes_ativo_criado_em", columnList = "ativo, criado_em"),
           @Index(name = "idx_pacientes_ativo_data_nascimento", columnList = "ativo, data_nascimento"),

           @Index(name = "idx_pacientes_situacao_rua", columnList = "situacao_rua"),
           @Index(name = "idx_pacientes_cartao_sus_ativo", columnList = "cartao_sus_ativo"),
           @Index(name = "idx_pacientes_possui_deficiencia", columnList = "possui_deficiencia"),
           @Index(name = "idx_pacientes_cns_validado", columnList = "cns_validado"),
           @Index(name = "idx_pacientes_acompanhado_esf", columnList = "acompanhado_por_equipe_esf")
       })
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "Paciente.basic",
        attributeNodes = {
            @NamedAttributeNode("convenio")
        }
    ),
    @NamedEntityGraph(
        name = "Paciente.enderecos", 
        attributeNodes = {
            @NamedAttributeNode("enderecos")
        }
    ),
    @NamedEntityGraph(
        name = "Paciente.infoClinica",
        attributeNodes = {
            @NamedAttributeNode("dadosSociodemograficos"),
            @NamedAttributeNode("dadosClinicosBasicos"),
            @NamedAttributeNode("responsavelLegal"),
            @NamedAttributeNode("lgpdConsentimento"),
            @NamedAttributeNode("integracaoGov")
        }
    ),
    @NamedEntityGraph(
        name = "Paciente.comAlergias",
        attributeNodes = {
            @NamedAttributeNode(value = "alergias", subgraph = "alergiasSubgraph")
        },
        subgraphs = {
            @NamedSubgraph(name = "alergiasSubgraph", type = AlergiasPaciente.class, attributeNodes = {
                @NamedAttributeNode("alergia")
            })
        }
    ),
    @NamedEntityGraph(
        name = "Paciente.completo",
        attributeNodes = {
            @NamedAttributeNode("convenio"),
            @NamedAttributeNode("enderecos"),
            @NamedAttributeNode(value = "alergias", subgraph = "alergiasSubgraph"),
            @NamedAttributeNode("deficiencias"),
            @NamedAttributeNode("dadosSociodemograficos"),
            @NamedAttributeNode("dadosClinicosBasicos"),
            @NamedAttributeNode("responsavelLegal"),
            @NamedAttributeNode("lgpdConsentimento"),
            @NamedAttributeNode("integracaoGov")
        },
        subgraphs = {
            @NamedSubgraph(name = "alergiasSubgraph", type = AlergiasPaciente.class, attributeNodes = {
                @NamedAttributeNode("alergia")
            })
        }
    ),
    @NamedEntityGraph(
        name = "Paciente.listagemCompleta",
        attributeNodes = {
            @NamedAttributeNode("convenio"),
            @NamedAttributeNode("enderecos"),
            @NamedAttributeNode("alergias"),
            @NamedAttributeNode("deficiencias")
        }
    )
})
@Data
@EqualsAndHashCode(callSuper = true)
public class Paciente extends BaseEntityWithoutTenant {

    public Paciente() {
        this.enderecos = new ArrayList<>();
        this.alergias = new ArrayList<>();
        this.deficiencias = new ArrayList<>();
    }

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

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

    @Column(name = "responsavel_cpf", length = 11)
    private String responsavelCpf;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "pacientes_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "paciente_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<Endereco> enderecos = new ArrayList<>();

    @Convert(converter = RacaCorEnumConverter.class)
    @Column(name = "raca_cor")
    private RacaCorEnum racaCor;

    @Convert(converter = NacionalidadeEnumConverter.class)
    @Column(name = "nacionalidade")
    private NacionalidadeEnum nacionalidade;

    @Column(name = "pais_nascimento", length = 100)
    private String paisNascimento;

    @Column(name = "naturalidade", length = 100)
    private String naturalidade;

    @Column(name = "municipio_nascimento_ibge", length = 7)
    private String municipioNascimentoIbge;

    @Convert(converter = EscolaridadeEnumConverter.class)
    @Column(name = "escolaridade")
    private EscolaridadeEnum escolaridade;

    @Column(name = "ocupacao_profissao", length = 150)
    private String ocupacaoProfissao;

    @Column(name = "situacao_rua", nullable = false)
    private Boolean situacaoRua = false;

    @Convert(converter = StatusPacienteEnumConverter.class)
    @Column(name = "status_paciente", nullable = false)
    private StatusPacienteEnum statusPaciente = StatusPacienteEnum.ATIVO;

    @Column(name = "data_obito")
    private LocalDate dataObito;

    @Column(name = "causa_obito_cid10", length = 10)
    private String causaObitoCid10;

    @Column(name = "cartao_sus_ativo", nullable = false)
    private Boolean cartaoSusAtivo = true;

    @Column(name = "data_atualizacao_cns")
    private LocalDate dataAtualizacaoCns;

    @Convert(converter = TipoAtendimentoPreferencialEnumConverter.class)
    @Column(name = "tipo_atendimento_preferencial")
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;

    @Column(name = "origem_cadastro", length = 30)
    private String origemCadastro;

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

    @Column(name = "tipo_deficiencia", length = 255)
    private String tipoDeficiencia;

    @Column(name = "cns_validado", nullable = false)
    private Boolean cnsValidado = false;

    @Convert(converter = TipoCnsEnumConverter.class)
    @Column(name = "tipo_cns")
    private TipoCnsEnum tipoCns;

    @Column(name = "acompanhado_por_equipe_esf", nullable = false)
    private Boolean acompanhadoPorEquipeEsf = false;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DadosSociodemograficos dadosSociodemograficos;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DadosClinicosBasicos dadosClinicosBasicos;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ResponsavelLegal responsavelLegal;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private LGPDConsentimento lgpdConsentimento;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private IntegracaoGov integracaoGov;

    // DoencasPaciente removido - Doencas foi deletada

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<AlergiasPaciente> alergias = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<DeficienciasPaciente> deficiencias = new ArrayList<>();

    // MedicacaoPaciente removido - Medicacao foi deletada

    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }
        if (alergias == null) {
            alergias = new ArrayList<>();
        }
        if (deficiencias == null) {
            deficiencias = new ArrayList<>();
        }
    }

    public void addAlergia(AlergiasPaciente alergia) {
        if (alergia == null) {
            return;
        }
        if (alergias == null) {
            alergias = new ArrayList<>();
        }
        if (!alergias.contains(alergia)) {
            alergias.add(alergia);
            alergia.setPaciente(this);
        }
    }

    public void removeAlergia(AlergiasPaciente alergia) {
        if (alergia == null || alergias == null) {
            return;
        }
        if (alergias.remove(alergia)) {
            alergia.setPaciente(null);
        }
    }

    public void addDeficiencia(DeficienciasPaciente deficiencia) {
        if (deficiencia == null) {
            return;
        }
        if (deficiencias == null) {
            deficiencias = new ArrayList<>();
        }
        if (!deficiencias.contains(deficiencia)) {
            deficiencias.add(deficiencia);
            deficiencia.setPaciente(this);
        }
    }

    public void removeDeficiencia(DeficienciasPaciente deficiencia) {
        if (deficiencia == null || deficiencias == null) {
            return;
        }
        if (deficiencias.remove(deficiencia)) {
            deficiencia.setPaciente(null);
        }
    }
}
