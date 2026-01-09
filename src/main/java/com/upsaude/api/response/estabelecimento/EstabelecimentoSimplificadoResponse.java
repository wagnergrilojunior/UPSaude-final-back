package com.upsaude.api.response.estabelecimento;

import com.upsaude.api.response.embeddable.DadosIdentificacaoEstabelecimentoResponse;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.api.response.sistema.multitenancy.TenantResponse;
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
public class EstabelecimentoSimplificadoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private DadosIdentificacaoEstabelecimentoResponse dadosIdentificacao;
    private EnderecoResponse enderecoPrincipal;
    private TenantResponse tenant;
}
