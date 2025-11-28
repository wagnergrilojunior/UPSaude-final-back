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
import com.upsaude.enums.ViaAdministracaoVacinaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull(message = "Tipo de vacina é obrigatório")
    private TipoVacinaEnum tipo;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @Size(max = 100, message = "Grupo alvo deve ter no máximo 100 caracteres")
    private String grupoAlvo;

    private UUID fabricanteId;

    @Size(max = 50, message = "Lote padrão deve ter no máximo 50 caracteres")
    private String lotePadrao;

    @NotNull(message = "Via de administração é obrigatória")
    private ViaAdministracaoVacinaEnum viaAdministracao;

    private UnidadeMedidaEnum unidadeMedida;

    private EsquemaVacinal esquemaVacinal;

    private IdadeAplicacaoVacina idadeAplicacao;

    private ContraindicacoesVacina contraindicacoes;

    private ConservacaoVacina conservacao;

    private ComposicaoVacina composicao;

    private EficaciaVacina eficacia;

    private ReacoesAdversasVacina reacoesAdversas;

    private CalendarioVacinal calendario;

    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    private Boolean disponivelUso;
    private Boolean obrigatoria;

    @Size(max = 255, message = "Bula deve ter no máximo 255 caracteres")
    private String bula;

    @Size(max = 255, message = "Ficha técnica deve ter no máximo 255 caracteres")
    private String fichaTecnica;

    @Size(max = 255, message = "Manual de uso deve ter no máximo 255 caracteres")
    private String manualUso;

    private String descricao;
    private String indicacoes;
    private String observacoes;

    private IntegracaoGovernamentalVacina integracaoGovernamental;
}

