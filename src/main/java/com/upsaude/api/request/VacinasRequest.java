package com.upsaude.api.request;

import com.upsaude.entity.embeddable.CalendarioVacinal;
import com.upsaude.entity.embeddable.ComposicaoVacina;
import com.upsaude.entity.embeddable.ConservacaoVacina;
import com.upsaude.entity.embeddable.ContraindicacoesVacina;
import com.upsaude.entity.embeddable.EficaciaVacina;
import com.upsaude.entity.embeddable.EsquemaVacinal;
import com.upsaude.entity.embeddable.IdadeAplicacaoVacina;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalVacina;
import com.upsaude.entity.embeddable.ReacoesAdversasVacina;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVacinaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinasRequest {
    private String nome;
    private String nomeComercial;
    private String codigoInterno;
    private String codigoPni;
    private String codigoSus;
    private String registroAnvisa;
    private TipoVacinaEnum tipo;
    private String categoria;
    private String grupoAlvo;
    private UUID fabricante;
    private String lotePadrao;
    private ViaAdministracaoEnum viaAdministracao;
    private UnidadeMedidaEnum unidadeMedida;
    private EsquemaVacinal esquemaVacinal;
    private IdadeAplicacaoVacina idadeAplicacao;
    private ContraindicacoesVacina contraindicacoes;
    private ConservacaoVacina conservacao;
    private ComposicaoVacina composicao;
    private EficaciaVacina eficacia;
    private ReacoesAdversasVacina reacoesAdversas;
    private CalendarioVacinal calendario;
    private StatusAtivoEnum status;
    private String bula;
    private String fichaTecnica;
    private String manualUso;
    private String descricao;
    private String indicacoes;
    private String observacoes;
    private IntegracaoGovernamentalVacina integracaoGovernamental;
}
