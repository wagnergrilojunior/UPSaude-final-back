package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.CalendarioVacinalRequest;
import com.upsaude.api.request.embeddable.ComposicaoVacinaRequest;
import com.upsaude.api.request.embeddable.ConservacaoVacinaRequest;
import com.upsaude.api.request.embeddable.ContraindicacoesVacinaRequest;
import com.upsaude.api.request.embeddable.EficaciaVacinaRequest;
import com.upsaude.api.request.embeddable.EsquemaVacinalRequest;
import com.upsaude.api.request.embeddable.IdadeAplicacaoVacinaRequest;
import com.upsaude.api.request.embeddable.IntegracaoGovernamentalVacinaRequest;
import com.upsaude.api.request.embeddable.ReacoesAdversasVacinaRequest;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVacinaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    
    private EsquemaVacinalRequest esquemaVacinal;
    private IdadeAplicacaoVacinaRequest idadeAplicacao;
    private ContraindicacoesVacinaRequest contraindicacoes;
    private ConservacaoVacinaRequest conservacao;
    private ComposicaoVacinaRequest composicao;
    private EficaciaVacinaRequest eficacia;
    private ReacoesAdversasVacinaRequest reacoesAdversas;
    private CalendarioVacinalRequest calendario;
    private StatusAtivoEnum status;
    private String bula;
    private String fichaTecnica;
    private String manualUso;
    private String descricao;
    private String indicacoes;
    private String observacoes;
    private IntegracaoGovernamentalVacinaRequest integracaoGovernamental;
}
