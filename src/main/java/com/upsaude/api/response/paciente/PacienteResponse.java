package com.upsaude.api.response.paciente;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.api.response.AlergiaPacienteResponse;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.api.response.deficiencia.DeficienciasPacienteResponse;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.api.response.sistema.lgpd.LGPDConsentimentoResponse;
import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.enums.TipoCnsEnum;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.EscolaridadeEnumSerializer;
import com.upsaude.util.converter.EstadoCivilEnumDeserializer;
import com.upsaude.util.converter.EstadoCivilEnumSerializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumSerializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumSerializer;
import com.upsaude.util.converter.OrientacaoSexualEnumDeserializer;
import com.upsaude.util.converter.OrientacaoSexualEnumSerializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumSerializer;
import com.upsaude.util.converter.SexoEnumDeserializer;
import com.upsaude.util.converter.SexoEnumSerializer;
import com.upsaude.util.converter.StatusPacienteEnumDeserializer;
import com.upsaude.util.converter.StatusPacienteEnumSerializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumDeserializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumSerializer;
import com.upsaude.util.converter.TipoCnsEnumDeserializer;
import com.upsaude.util.converter.TipoCnsEnumSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nomeCompleto;
    private String cpf;
    private String rg;
    private String cns;
    private LocalDate dataNascimento;
    private Integer idade;

    @JsonSerialize(using = SexoEnumSerializer.class)
    @JsonDeserialize(using = SexoEnumDeserializer.class)
    private SexoEnum sexo;

    @JsonSerialize(using = EstadoCivilEnumSerializer.class)
    @JsonDeserialize(using = EstadoCivilEnumDeserializer.class)
    private EstadoCivilEnum estadoCivil;
    private String telefone;
    private String email;
    private String nomeMae;
    private String nomePai;
    private String responsavelNome;
    private String responsavelCpf;
    private String responsavelTelefone;
    private ConvenioResponse convenio;
    private String numeroCarteirinha;
    private LocalDate dataValidadeCarteirinha;
    private String observacoes;

    @JsonSerialize(using = RacaCorEnumSerializer.class)
    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;

    @JsonSerialize(using = NacionalidadeEnumSerializer.class)
    @JsonDeserialize(using = NacionalidadeEnumDeserializer.class)
    private NacionalidadeEnum nacionalidade;
    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;

    @JsonSerialize(using = EscolaridadeEnumSerializer.class)
    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;

    private String ocupacaoProfissao;
    private Boolean situacaoRua;

    @JsonSerialize(using = StatusPacienteEnumSerializer.class)
    @JsonDeserialize(using = StatusPacienteEnumDeserializer.class)
    private StatusPacienteEnum statusPaciente;
    private LocalDate dataObito;
    private String causaObitoCid10;
    private Boolean cartaoSusAtivo;
    private LocalDate dataAtualizacaoCns;

    @JsonSerialize(using = TipoAtendimentoPreferencialEnumSerializer.class)
    @JsonDeserialize(using = TipoAtendimentoPreferencialEnumDeserializer.class)
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;

    private String origemCadastro;
    private String nomeSocial;

    @JsonSerialize(using = IdentidadeGeneroEnumSerializer.class)
    @JsonDeserialize(using = IdentidadeGeneroEnumDeserializer.class)
    private IdentidadeGeneroEnum identidadeGenero;

    @JsonSerialize(using = OrientacaoSexualEnumSerializer.class)
    @JsonDeserialize(using = OrientacaoSexualEnumDeserializer.class)
    private OrientacaoSexualEnum orientacaoSexual;
    private Boolean possuiDeficiencia;
    private String tipoDeficiencia;
    private Boolean cnsValidado;

    @JsonSerialize(using = TipoCnsEnumSerializer.class)
    @JsonDeserialize(using = TipoCnsEnumDeserializer.class)
    private TipoCnsEnum tipoCns;

    private Boolean acompanhadoPorEquipeEsf;

    @Builder.Default
    private List<EnderecoResponse> enderecos = new ArrayList<>();

    private DadosSociodemograficosResponse dadosSociodemograficos;
    private DadosClinicosBasicosResponse dadosClinicosBasicos;
    private ResponsavelLegalResponse responsavelLegal;
    private LGPDConsentimentoResponse lgpdConsentimento;
    private IntegracaoGovResponse integracaoGov;

    // DoencasPacienteResponse removido - Doencas foi deletada

    @Builder.Default
    private List<AlergiaPacienteResponse> alergias = new ArrayList<>();

    @Builder.Default
    private List<DeficienciasPacienteResponse> deficiencias = new ArrayList<>();

    // MedicacaoPacienteResponse removido - Medicacao foi deletada
}
