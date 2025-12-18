package com.upsaude.api.response.convenio;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.api.response.embeddable.CoberturaConvenioResponse;
import com.upsaude.api.response.embeddable.ContatoConvenioResponse;
import com.upsaude.api.response.embeddable.InformacoesFinanceirasConvenioResponse;
import com.upsaude.api.response.embeddable.IntegracaoGovernamentalConvenioResponse;
import com.upsaude.api.response.embeddable.RegistroANSConvenioResponse;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;

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
public class ConvenioResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeFantasia;
    private String codigo;
    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private TipoConvenioEnum tipo;
    private ModalidadeConvenioEnum modalidade;
    private String categoria;
    private EnderecoResponse endereco;
    private ContatoConvenioResponse contato;
    private RegistroANSConvenioResponse registroAns;
    private CoberturaConvenioResponse cobertura;
    private InformacoesFinanceirasConvenioResponse informacoesFinanceiras;
    private StatusAtivoEnum status;
    private LocalDate dataCadastro;
    private LocalDate dataAtivacao;
    private LocalDate dataDesativacao;
    private Integer quantidadeEstabelecimentosCredenciados;
    private Integer quantidadeProfissionaisCredenciados;
    private String contrato;
    private String tabelaPrecos;
    private String manualConvenio;
    private String descricao;
    private String observacoes;
    private IntegracaoGovernamentalConvenioResponse integracaoGovernamental;
}
