package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.api.request.embeddable.CalendarioVacinalRequest;
import com.upsaude.api.request.embeddable.ComposicaoVacinaRequest;
import com.upsaude.api.request.embeddable.ComposicaoVacinaRequestDeserializer;
import com.upsaude.api.request.embeddable.ConservacaoVacinaRequest;
import com.upsaude.api.request.embeddable.ContraindicacoesVacinaRequest;
import com.upsaude.api.request.embeddable.EficaciaVacinaRequest;
import com.upsaude.api.request.embeddable.EficaciaVacinaRequestDeserializer;
import com.upsaude.api.request.embeddable.EsquemaVacinalRequest;
import com.upsaude.api.request.embeddable.ReacoesAdversasVacinaRequest;
import com.upsaude.api.request.embeddable.ReacoesAdversasVacinaRequestDeserializer;
import com.upsaude.api.request.embeddable.IdadeAplicacaoVacinaRequest;
import com.upsaude.api.request.embeddable.IntegracaoGovernamentalVacinaRequest;
import com.upsaude.api.request.embeddable.IntegracaoGovernamentalVacinaRequestDeserializer;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVacinaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoVacinaEnumDeserializer;
import com.upsaude.util.converter.UnidadeMedidaEnumDeserializer;
import com.upsaude.util.converter.ViaAdministracaoEnumDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de vacinas")
public class VacinasRequest {
    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(max = 255, message = "Nome da vacina deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    private String nomeComercial;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;

    @Size(max = 20, message = "Código PNI deve ter no máximo 20 caracteres")
    private String codigoPni;

    @Size(max = 20, message = "Código SUS deve ter no máximo 20 caracteres")
    private String codigoSus;

    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    private String registroAnvisa;

    private TipoVacinaEnum tipo;
    private String categoria;
    private String grupoAlvo;
    private UUID fabricante;
    private String lotePadrao;
    private ViaAdministracaoEnum viaAdministracao;
    private UnidadeMedidaEnum unidadeMedida;

    @Valid
    private EsquemaVacinalRequest esquemaVacinal;

    @Valid
    private IdadeAplicacaoVacinaRequest idadeAplicacao;

    @Valid
    private ContraindicacoesVacinaRequest contraindicacoes;

    @Valid
    private ConservacaoVacinaRequest conservacao;

    @Valid
    @JsonDeserialize(using = ComposicaoVacinaRequestDeserializer.class)
    private ComposicaoVacinaRequest composicao;

    @Valid
    @JsonDeserialize(using = EficaciaVacinaRequestDeserializer.class)
    private EficaciaVacinaRequest eficacia;

    @Valid
    @JsonDeserialize(using = ReacoesAdversasVacinaRequestDeserializer.class)
    private ReacoesAdversasVacinaRequest reacoesAdversas;

    @Valid
    private CalendarioVacinalRequest calendario;

    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum status;

    @Size(max = 5000, message = "Bula deve ter no máximo 5000 caracteres")
    private String bula;

    @Size(max = 5000, message = "Ficha técnica deve ter no máximo 5000 caracteres")
    private String fichaTecnica;

    @Size(max = 5000, message = "Manual de uso deve ter no máximo 5000 caracteres")
    private String manualUso;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @Size(max = 1000, message = "Indicações deve ter no máximo 1000 caracteres")
    private String indicacoes;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;

    @Valid
    @JsonDeserialize(using = IntegracaoGovernamentalVacinaRequestDeserializer.class)
    private IntegracaoGovernamentalVacinaRequest integracaoGovernamental;
}
