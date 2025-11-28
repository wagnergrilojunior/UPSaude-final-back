package com.upsaude.dto;

import com.upsaude.entity.embeddable.CoberturaConvenio;
import com.upsaude.entity.embeddable.ContatoConvenio;
import com.upsaude.entity.embeddable.InformacoesFinanceirasConvenio;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalConvenio;
import com.upsaude.entity.embeddable.RegistroANSConvenio;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
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
public class ConvenioDTO {
    private UUID id;
    private String nome;
    private String nomeFantasia;
    private String codigo;
    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private TipoConvenioEnum tipo;
    private ModalidadeConvenioEnum modalidade;
    private String categoria;
    private UUID enderecoId;
    private ContatoConvenio contato;
    private RegistroANSConvenio registroAns;
    private CoberturaConvenio cobertura;
    private InformacoesFinanceirasConvenio informacoesFinanceiras;
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
    private String contrato;
    private String tabelaPrecos;
    private String manualConvenio;
    private String descricao;
    private String observacoes;
    private IntegracaoGovernamentalConvenio integracaoGovernamental;
    private Boolean active;
}
