package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntityWithoutTenant;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.embeddable.InformacoesConvenioPaciente;
import com.upsaude.entity.paciente.deficiencia.DeficienciasPaciente;
import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;

import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.util.converter.SexoEnumConverter;
import com.upsaude.util.converter.StatusPacienteEnumConverter;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes", schema = "public", indexes = {
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
        @Index(name = "idx_pacientes_ativo_data_nascimento", columnList = "ativo, data_nascimento")
})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Paciente.basic", attributeNodes = {
                @NamedAttributeNode("convenio")
        }),
        @NamedEntityGraph(name = "Paciente.listagem", attributeNodes = {
                @NamedAttributeNode("convenio")
        }),
        @NamedEntityGraph(name = "Paciente.cadastro", attributeNodes = {
                @NamedAttributeNode("convenio"),
                @NamedAttributeNode("dadosSociodemograficos"),
                @NamedAttributeNode("dadosClinicosBasicos"),
                @NamedAttributeNode("responsavelLegal"),
                @NamedAttributeNode("lgpdConsentimento"),
                @NamedAttributeNode("dadosPessoaisComplementares"),
                @NamedAttributeNode("obito")
        }),
        @NamedEntityGraph(name = "Paciente.prontuarioResumo", attributeNodes = {
                @NamedAttributeNode("convenio"),
                @NamedAttributeNode("dadosSociodemograficos"),
                @NamedAttributeNode("dadosClinicosBasicos"),
                @NamedAttributeNode("deficiencias")
        }),
        @NamedEntityGraph(name = "Paciente.integracao", attributeNodes = {
                @NamedAttributeNode("convenio"),
                @NamedAttributeNode("identificadores"),
                @NamedAttributeNode("contatos"),
                @NamedAttributeNode(value = "integracoesGov", subgraph = "integracoesGovSubgraph"),
                @NamedAttributeNode("vinculosTerritoriais")
        }, subgraphs = {
                @NamedSubgraph(name = "integracoesGovSubgraph", type = IntegracaoGov.class, attributeNodes = {})
        }),
        @NamedEntityGraph(name = "Paciente.enderecos", attributeNodes = {
                @NamedAttributeNode(value = "enderecos", subgraph = "enderecosSubgraph")
        }, subgraphs = {
                @NamedSubgraph(name = "enderecosSubgraph", type = PacienteEndereco.class, attributeNodes = {
                        @NamedAttributeNode("endereco") })
        }),
        @NamedEntityGraph(name = "Paciente.prontuarioCompleto", attributeNodes = {
                @NamedAttributeNode("convenio"),
                @NamedAttributeNode(value = "enderecos", subgraph = "enderecosSubgraph"),
                @NamedAttributeNode("deficiencias"),
                @NamedAttributeNode("dadosSociodemograficos"),
                @NamedAttributeNode("dadosClinicosBasicos"),
                @NamedAttributeNode("responsavelLegal"),
                @NamedAttributeNode("lgpdConsentimento"),
                @NamedAttributeNode("dadosPessoaisComplementares"),
                @NamedAttributeNode("obito"),
                @NamedAttributeNode(value = "integracoesGov", subgraph = "integracoesGovSubgraph"),
                @NamedAttributeNode("identificadores"),
                @NamedAttributeNode("contatos"),
                @NamedAttributeNode("vinculosTerritoriais")
        }, subgraphs = {
                @NamedSubgraph(name = "integracoesGovSubgraph", type = IntegracaoGov.class, attributeNodes = {}),
                @NamedSubgraph(name = "enderecosSubgraph", type = PacienteEndereco.class, attributeNodes = {
                        @NamedAttributeNode("endereco") })
        })
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Paciente extends BaseEntityWithoutTenant {

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Column(name = "nome_social", length = 255)
    private String nomeSocial;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Convert(converter = SexoEnumConverter.class)
    @Column(name = "sexo")
    private SexoEnum sexo;

    @Convert(converter = StatusPacienteEnumConverter.class)
    @Column(name = "status_paciente", nullable = false)
    private StatusPacienteEnum statusPaciente = StatusPacienteEnum.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @Embedded
    private InformacoesConvenioPaciente informacoesConvenio;

    @Convert(converter = TipoAtendimentoPreferencialEnumConverter.class)
    @Column(name = "tipo_atendimento_preferencial")
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<PacienteEndereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<PacienteIdentificador> identificadores = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<PacienteContato> contatos = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<PacienteVinculoTerritorial> vinculosTerritoriais = new ArrayList<>();

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DadosSociodemograficos dadosSociodemograficos;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DadosClinicosBasicos dadosClinicosBasicos;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ResponsavelLegal responsavelLegal;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private LGPDConsentimento lgpdConsentimento;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private PacienteDadosPessoaisComplementares dadosPessoaisComplementares;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private PacienteObito obito;

    @OneToMany(mappedBy = "paciente", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<IntegracaoGov> integracoesGov = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<DeficienciasPaciente> deficiencias = new ArrayList<>();

    //=============================================================================================================

    public Paciente() {
        this.enderecos = new ArrayList<>();
        this.deficiencias = new ArrayList<>();
        this.identificadores = new ArrayList<>();
        this.contatos = new ArrayList<>();
        this.integracoesGov = new ArrayList<>();
        this.vinculosTerritoriais = new ArrayList<>();
    }

    public void addEndereco(PacienteEndereco pacienteEndereco) {
        if (pacienteEndereco == null) {
            return;
        }
        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }
        enderecos.add(pacienteEndereco);
        pacienteEndereco.setPaciente(this);
    }

    public void removeEndereco(PacienteEndereco pacienteEndereco) {
        if (pacienteEndereco == null || enderecos == null) {
            return;
        }
        if (enderecos.remove(pacienteEndereco)) {
            pacienteEndereco.setPaciente(null);
        }
    }

    @PrePersist
    @PreUpdate
    public void validateCollectionsAndConvenioInfo() {
        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }
        if (deficiencias == null) {
            deficiencias = new ArrayList<>();
        }
        if (identificadores == null) {
            identificadores = new ArrayList<>();
        }
        if (contatos == null) {
            contatos = new ArrayList<>();
        }
        if (integracoesGov == null) {
            integracoesGov = new ArrayList<>();
        }
        if (vinculosTerritoriais == null) {
            vinculosTerritoriais = new ArrayList<>();
        }
        
        // Se não há convênio, limpar informações do convênio
        if (convenio == null) {
            this.informacoesConvenio = null;
        }
    }

    public void addIdentificador(PacienteIdentificador identificador) {
        if (identificador == null) {
            return;
        }
        if (identificadores == null) {
            identificadores = new ArrayList<>();
        }
        identificadores.add(identificador);
        identificador.setPaciente(this);
    }

    public void removeIdentificador(PacienteIdentificador identificador) {
        if (identificador == null || identificadores == null) {
            return;
        }
        if (identificadores.remove(identificador)) {
            identificador.setPaciente(null);
        }
    }

    public void addContato(PacienteContato contato) {
        if (contato == null) {
            return;
        }
        if (contatos == null) {
            contatos = new ArrayList<>();
        }
        contatos.add(contato);
        contato.setPaciente(this);
    }

    public void removeContato(PacienteContato contato) {
        if (contato == null || contatos == null) {
            return;
        }
        if (contatos.remove(contato)) {
            contato.setPaciente(null);
        }
    }

    public void addVinculoTerritorial(PacienteVinculoTerritorial vinculo) {
        if (vinculo == null) {
            return;
        }
        if (vinculosTerritoriais == null) {
            vinculosTerritoriais = new ArrayList<>();
        }
        vinculosTerritoriais.add(vinculo);
        vinculo.setPaciente(this);
    }

    public void removeVinculoTerritorial(PacienteVinculoTerritorial vinculo) {
        if (vinculo == null || vinculosTerritoriais == null) {
            return;
        }
        if (vinculosTerritoriais.remove(vinculo)) {
            vinculo.setPaciente(null);
        }
    }

    public void addIntegracaoGov(IntegracaoGov integracao) {
        if (integracao == null) {
            return;
        }
        if (integracoesGov == null) {
            integracoesGov = new ArrayList<>();
        }
        integracoesGov.add(integracao);
        integracao.setPaciente(this);
    }

    public void removeIntegracaoGov(IntegracaoGov integracao) {
        if (integracao == null || integracoesGov == null) {
            return;
        }
        if (integracoesGov.remove(integracao)) {
            integracao.setPaciente(null);
        }
    }

    public void addDeficiencia(DeficienciasPaciente deficiencia) {
        if (deficiencia == null) {
            return;
        }
        if (deficiencias == null) {
            deficiencias = new ArrayList<>();
        }
        deficiencias.add(deficiencia);
        deficiencia.setPaciente(this);
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
