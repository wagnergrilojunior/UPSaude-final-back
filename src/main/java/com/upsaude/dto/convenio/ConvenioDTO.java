package com.upsaude.dto.convenio;

import com.upsaude.dto.embeddable.CoberturaConvenioDTO;
import com.upsaude.dto.embeddable.ContatoConvenioDTO;
import com.upsaude.dto.embeddable.InformacoesFinanceirasConvenioDTO;
import com.upsaude.dto.embeddable.IntegracaoGovernamentalConvenioDTO;
import com.upsaude.dto.embeddable.RegistroANSConvenioDTO;
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
    private ContatoConvenioDTO contato;
    private RegistroANSConvenioDTO registroAns;
    private CoberturaConvenioDTO cobertura;
    private InformacoesFinanceirasConvenioDTO informacoesFinanceiras;
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
    private IntegracaoGovernamentalConvenioDTO integracaoGovernamental;
}
