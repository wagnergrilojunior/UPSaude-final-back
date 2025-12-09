package com.upsaude.api.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import com.upsaude.util.converter.EscolaridadeEnumSerializer;
import com.upsaude.util.converter.EstadoCivilEnumSerializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumSerializer;
import com.upsaude.util.converter.NacionalidadeEnumSerializer;
import com.upsaude.util.converter.OrientacaoSexualEnumSerializer;
import com.upsaude.util.converter.RacaCorEnumSerializer;
import com.upsaude.util.converter.SexoEnumSerializer;
import com.upsaude.util.converter.StatusPacienteEnumSerializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumSerializer;
import com.upsaude.util.converter.TipoCnsEnumSerializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private SexoEnum sexo;
    
    @JsonSerialize(using = EstadoCivilEnumSerializer.class)
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
    private RacaCorEnum racaCor;
    
    @JsonSerialize(using = NacionalidadeEnumSerializer.class)
    private NacionalidadeEnum nacionalidade;
    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;
    
    @JsonSerialize(using = EscolaridadeEnumSerializer.class)
    private EscolaridadeEnum escolaridade;
    
    private String ocupacaoProfissao;
    private Boolean situacaoRua;
    
    @JsonSerialize(using = StatusPacienteEnumSerializer.class)
    private StatusPacienteEnum statusPaciente;
    private LocalDate dataObito;
    private String causaObitoCid10;
    private Boolean cartaoSusAtivo;
    private LocalDate dataAtualizacaoCns;
    
    @JsonSerialize(using = TipoAtendimentoPreferencialEnumSerializer.class)
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;
    
    private String origemCadastro;
    private String nomeSocial;
    
    @JsonSerialize(using = IdentidadeGeneroEnumSerializer.class)
    private IdentidadeGeneroEnum identidadeGenero;
    
    @JsonSerialize(using = OrientacaoSexualEnumSerializer.class)
    private OrientacaoSexualEnum orientacaoSexual;
    private Boolean possuiDeficiencia;
    private String tipoDeficiencia;
    private Boolean cnsValidado;
    
    @JsonSerialize(using = TipoCnsEnumSerializer.class)
    private TipoCnsEnum tipoCns;
    
    private Boolean acompanhadoPorEquipeEsf;
    
    @Builder.Default
    private List<EnderecoResponse> enderecos = new ArrayList<>();
    
    private DadosSociodemograficosResponse dadosSociodemograficos;
    private DadosClinicosBasicosResponse dadosClinicosBasicos;
    private ResponsavelLegalResponse responsavelLegal;
    private LGPDConsentimentoResponse lgpdConsentimento;
    private IntegracaoGovResponse integracaoGov;
    
    @Builder.Default
    private List<DoencasPacienteResponse> doencas = new ArrayList<>();
    
    @Builder.Default
    private List<AlergiasPacienteResponse> alergias = new ArrayList<>();
    
    @Builder.Default
    private List<DeficienciasPacienteResponse> deficiencias = new ArrayList<>();
    
    @Builder.Default
    private List<MedicacaoPacienteResponse> medicacoes = new ArrayList<>();
}
