package com.upsaude.api.response.paciente;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.api.response.deficiencia.DeficienciasPacienteResponse;
import com.upsaude.api.response.embeddable.ContatoPacienteResponse;
import com.upsaude.api.response.embeddable.DadosDemograficosPacienteResponse;
import com.upsaude.api.response.embeddable.DadosPessoaisBasicosPacienteResponse;
import com.upsaude.api.response.embeddable.DocumentosBasicosPacienteResponse;
import com.upsaude.api.response.embeddable.IntegracaoGovPacienteResponse;
import com.upsaude.api.response.embeddable.ResponsavelLegalPacienteResponse;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.api.response.sistema.lgpd.LGPDConsentimentoResponse;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.util.converter.StatusPacienteEnumDeserializer;
import com.upsaude.util.converter.StatusPacienteEnumSerializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumDeserializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumSerializer;

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
    
    private DadosPessoaisBasicosPacienteResponse dadosPessoaisBasicos;
    private DocumentosBasicosPacienteResponse documentosBasicos;
    private DadosDemograficosPacienteResponse dadosDemograficos;
    private ContatoPacienteResponse contato;
    
    @JsonSerialize(using = StatusPacienteEnumSerializer.class)
    @JsonDeserialize(using = StatusPacienteEnumDeserializer.class)
    private StatusPacienteEnum statusPaciente;

    @JsonSerialize(using = TipoAtendimentoPreferencialEnumSerializer.class)
    @JsonDeserialize(using = TipoAtendimentoPreferencialEnumDeserializer.class)
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;

    private ConvenioResponse convenio;
    private com.upsaude.api.response.embeddable.InformacoesConvenioPacienteResponse informacoesConvenio;

    @Builder.Default
    private List<EnderecoResponse> enderecos = new ArrayList<>();

    private DadosClinicosBasicosResponse dadosClinicosBasicos;
    private ResponsavelLegalPacienteResponse responsavelLegal;
    private PacienteDadosPessoaisComplementaresResponse dadosPessoaisComplementares;
    private PacienteObitoResponse obito;
    private LGPDConsentimentoResponse lgpdConsentimento;
    private IntegracaoGovPacienteResponse integracaoGov;

    @Builder.Default
    private List<DeficienciasPacienteResponse> deficiencias = new ArrayList<>();

    @Builder.Default
    private List<PacienteVinculoTerritorialResponse> vinculosTerritoriais = new ArrayList<>();
    
    private String observacoes;
}
