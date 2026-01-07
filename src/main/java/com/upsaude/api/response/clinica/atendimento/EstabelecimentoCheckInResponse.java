package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.geral.EnderecoResponse;
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
public class EstabelecimentoCheckInResponse {
    private UUID id;
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cnes;
    private EnderecoResponse endereco;
    private UUID tenantId;
}
