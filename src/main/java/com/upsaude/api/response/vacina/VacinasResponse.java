package com.upsaude.api.response.vacina;

import com.upsaude.api.response.embeddable.CalendarioVacinalResponse;
import com.upsaude.api.response.embeddable.ComposicaoVacinaResponse;
import com.upsaude.api.response.embeddable.ConservacaoVacinaResponse;
import com.upsaude.api.response.embeddable.ContraindicacoesVacinaResponse;
import com.upsaude.api.response.embeddable.EficaciaVacinaResponse;
import com.upsaude.api.response.embeddable.EsquemaVacinalResponse;
import com.upsaude.api.response.embeddable.IdadeAplicacaoVacinaResponse;
import com.upsaude.api.response.embeddable.IntegracaoGovernamentalVacinaResponse;
import com.upsaude.api.response.embeddable.ReacoesAdversasVacinaResponse;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVacinaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import java.time.OffsetDateTime;
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
public class VacinasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeComercial;
    private String codigoInterno;
    private String codigoPni;
    private String codigoSus;
    private String registroAnvisa;
    private TipoVacinaEnum tipo;
    private String categoria;
    private String grupoAlvo;
    private FabricantesVacinaResponse fabricante;
    private String lotePadrao;
    private ViaAdministracaoEnum viaAdministracao;
    private UnidadeMedidaEnum unidadeMedida;
    private EsquemaVacinalResponse esquemaVacinal;
    private IdadeAplicacaoVacinaResponse idadeAplicacao;
    private ContraindicacoesVacinaResponse contraindicacoes;
    private ConservacaoVacinaResponse conservacao;
    private ComposicaoVacinaResponse composicao;
    private EficaciaVacinaResponse eficacia;
    private ReacoesAdversasVacinaResponse reacoesAdversas;
    private CalendarioVacinalResponse calendario;
    private StatusAtivoEnum status;
    private String bula;
    private String fichaTecnica;
    private String manualUso;
    private String descricao;
    private String indicacoes;
    private String observacoes;
    private IntegracaoGovernamentalVacinaResponse integracaoGovernamental;
}
