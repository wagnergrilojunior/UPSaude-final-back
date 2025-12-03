package com.upsaude.dto;

import com.upsaude.entity.embeddable.CoberturaConvenio;
import com.upsaude.entity.embeddable.ContatoConvenio;
import com.upsaude.entity.embeddable.InformacoesFinanceirasConvenio;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalConvenio;
import com.upsaude.entity.embeddable.RegistroANSConvenio;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioDTO {
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
    private EnderecoDTO endereco;
    private ContatoConvenio contato;
    private RegistroANSConvenio registroAns;
    private CoberturaConvenio cobertura;
    private InformacoesFinanceirasConvenio informacoesFinanceiras;
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
    private IntegracaoGovernamentalConvenio integracaoGovernamental;
}
