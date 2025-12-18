package com.upsaude.dto.vacina;

import com.upsaude.dto.embeddable.CalendarioVacinalDTO;
import com.upsaude.dto.embeddable.ComposicaoVacinaDTO;
import com.upsaude.dto.embeddable.ConservacaoVacinaDTO;
import com.upsaude.dto.embeddable.ContraindicacoesVacinaDTO;
import com.upsaude.dto.embeddable.EficaciaVacinaDTO;
import com.upsaude.dto.embeddable.EsquemaVacinalDTO;
import com.upsaude.dto.embeddable.IdadeAplicacaoVacinaDTO;
import com.upsaude.dto.embeddable.IntegracaoGovernamentalVacinaDTO;
import com.upsaude.dto.embeddable.ReacoesAdversasVacinaDTO;
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
public class VacinasDTO {
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
    private FabricantesVacinaDTO fabricante;
    private String lotePadrao;
    private ViaAdministracaoEnum viaAdministracao;
    private UnidadeMedidaEnum unidadeMedida;
    private EsquemaVacinalDTO esquemaVacinal;
    private IdadeAplicacaoVacinaDTO idadeAplicacao;
    private ContraindicacoesVacinaDTO contraindicacoes;
    private ConservacaoVacinaDTO conservacao;
    private ComposicaoVacinaDTO composicao;
    private EficaciaVacinaDTO eficacia;
    private ReacoesAdversasVacinaDTO reacoesAdversas;
    private CalendarioVacinalDTO calendario;
    private StatusAtivoEnum status;
    private String bula;
    private String fichaTecnica;
    private String manualUso;
    private String descricao;
    private String indicacoes;
    private String observacoes;
    private IntegracaoGovernamentalVacinaDTO integracaoGovernamental;
}
