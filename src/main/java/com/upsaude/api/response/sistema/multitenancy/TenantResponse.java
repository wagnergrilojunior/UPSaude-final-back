package com.upsaude.api.response.sistema.multitenancy;

import com.upsaude.api.response.embeddable.ContatoTenantResponse;
import com.upsaude.api.response.embeddable.DadosFiscaisTenantResponse;
import com.upsaude.api.response.embeddable.DadosIdentificacaoTenantResponse;
import com.upsaude.api.response.embeddable.InformacoesAdicionaisTenantResponse;
import com.upsaude.api.response.embeddable.ResponsavelTenantResponse;
import com.upsaude.api.response.geral.EnderecoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean ativo;
    private Boolean consorcio;
    private String slug;
    private String metadados;
    private String cnes;
    private String tipoInstituicao;
    private EnderecoResponse endereco;
    private DadosIdentificacaoTenantResponse dadosIdentificacao;
    private ContatoTenantResponse contato;
    private DadosFiscaisTenantResponse dadosFiscais;
    private ResponsavelTenantResponse responsavel;
    private InformacoesAdicionaisTenantResponse informacoesAdicionais;
}
