package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.CoberturaConvenioRequest;
import com.upsaude.api.request.embeddable.ContatoConvenioRequest;
import com.upsaude.api.request.embeddable.InformacoesFinanceirasConvenioRequest;
import com.upsaude.api.request.embeddable.IntegracaoGovernamentalConvenioRequest;
import com.upsaude.api.request.embeddable.RegistroANSConvenioRequest;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
public class ConvenioRequest {
    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;
    
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;
    
    @Pattern(regexp = "^$|^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;
    
    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    private String inscricaoEstadual;
    
    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    private String inscricaoMunicipal;
    
    @NotNull(message = "Tipo de convênio é obrigatório")
    private TipoConvenioEnum tipo;
    
    @NotNull(message = "Modalidade de convênio é obrigatória")
    private ModalidadeConvenioEnum modalidade;
    
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;
    private UUID endereco;
    
    @Valid
    private ContatoConvenioRequest contato;
    
    @Valid
    private RegistroANSConvenioRequest registroAns;
    
    @Valid
    private CoberturaConvenioRequest cobertura;
    
    @Valid
    private InformacoesFinanceirasConvenioRequest informacoesFinanceiras;
    
    private StatusAtivoEnum status;
    private LocalDate dataCadastro;
    private LocalDate dataAtivacao;
    private LocalDate dataDesativacao;
    private Integer quantidadeEstabelecimentosCredenciados;
    private Integer quantidadeProfissionaisCredenciados;
    
    @Size(max = 500, message = "Contrato deve ter no máximo 500 caracteres")
    private String contrato;
    
    @Size(max = 500, message = "Tabela de preços deve ter no máximo 500 caracteres")
    private String tabelaPrecos;
    
    @Size(max = 500, message = "Manual do convênio deve ter no máximo 500 caracteres")
    private String manualConvenio;
    
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
    
    @Valid
    private IntegracaoGovernamentalConvenioRequest integracaoGovernamental;
}
