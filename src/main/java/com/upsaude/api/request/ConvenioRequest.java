package com.upsaude.api.request;

import com.upsaude.entity.embeddable.CoberturaConvenio;
import com.upsaude.entity.embeddable.ContatoConvenio;
import com.upsaude.entity.embeddable.InformacoesFinanceirasConvenio;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalConvenio;
import com.upsaude.entity.embeddable.RegistroANSConvenio;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioRequest {
    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    private String inscricaoMunicipal;

    @NotNull(message = "Tipo de convênio é obrigatório")
    private TipoConvenioEnum tipo;

    private ModalidadeConvenioEnum modalidade;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    private UUID enderecoId;

    private ContatoConvenio contato;

    private RegistroANSConvenio registroAns;

    private CoberturaConvenio cobertura;

    private InformacoesFinanceirasConvenio informacoesFinanceiras;

    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    private Boolean ativoComercial;

    private Boolean aceitaNovosClientes;

    private LocalDate dataCadastro;

    private LocalDate dataAtivacao;

    private LocalDate dataDesativacao;

    private Boolean redeCredenciadaNacional;

    private Boolean redeCredenciadaRegional;

    private Integer quantidadeEstabelecimentosCredenciados;

    private Integer quantidadeProfissionaisCredenciados;

    @Size(max = 255, message = "Contrato deve ter no máximo 255 caracteres")
    private String contrato;

    @Size(max = 255, message = "Tabela de preços deve ter no máximo 255 caracteres")
    private String tabelaPrecos;

    @Size(max = 255, message = "Manual do convênio deve ter no máximo 255 caracteres")
    private String manualConvenio;

    private String descricao;

    private String observacoes;

    private IntegracaoGovernamentalConvenio integracaoGovernamental;
}
