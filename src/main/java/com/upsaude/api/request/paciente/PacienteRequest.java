package com.upsaude.api.request.paciente;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.api.request.deficiencia.DeficienciasPacienteRequest;
import com.upsaude.api.request.embeddable.ContatoPacienteRequest;
import com.upsaude.api.request.embeddable.DadosDemograficosPacienteRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisBasicosPacienteRequest;
import com.upsaude.api.request.embeddable.DocumentosBasicosPacienteRequest;
import com.upsaude.api.request.embeddable.InformacoesConvenioPacienteRequest;
import com.upsaude.api.request.embeddable.IntegracaoGovPacienteRequest;
import com.upsaude.api.request.embeddable.ResponsavelLegalPacienteRequest;
import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.api.request.sistema.lgpd.LGPDConsentimentoRequest;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.util.converter.StatusPacienteEnumDeserializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de paciente")
public class PacienteRequest {

    @Valid
    private DadosPessoaisBasicosPacienteRequest dadosPessoaisBasicos;

    @Valid
    private DocumentosBasicosPacienteRequest documentosBasicos;

    @Valid
    private DadosDemograficosPacienteRequest dadosDemograficos;

    @Valid
    private ContatoPacienteRequest contato;

    @JsonDeserialize(using = StatusPacienteEnumDeserializer.class)
    private StatusPacienteEnum statusPaciente;

    private UUID convenio;

    private InformacoesConvenioPacienteRequest informacoesConvenio;

    @JsonDeserialize(using = TipoAtendimentoPreferencialEnumDeserializer.class)
    private TipoAtendimentoPreferencialEnum tipoAtendimentoPreferencial;

    @Valid
    @Schema(description = "Endereço principal do paciente. Se fornecido, será criado/atualizado.")
    private EnderecoRequest enderecoPrincipal;

    @Valid
    @Builder.Default
    private List<PacienteEnderecoRequest> enderecos = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<PacienteIdentificadorRequest> identificadores = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<PacienteContatoRequest> contatos = new ArrayList<>();

    @Valid
    private DadosSociodemograficosRequest dadosSociodemograficos;

    @Valid
    private DadosClinicosBasicosRequest dadosClinicosBasicos;

    private ResponsavelLegalPacienteRequest responsavelLegal;

    @Valid
    private PacienteDadosPessoaisComplementaresRequest dadosPessoaisComplementares;

    @Valid
    private PacienteObitoRequest obito;

    @Valid
    private IntegracaoGovPacienteRequest integracaoGov;

    @Valid
    @Builder.Default
    private List<DeficienciasPacienteRequest> deficiencias = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<PacienteVinculoTerritorialRequest> vinculosTerritoriais = new ArrayList<>();

    @Valid
    private LGPDConsentimentoRequest lgpdConsentimento;

    @Schema(description = "Observações gerais sobre o paciente")
    private String observacoes;
}
