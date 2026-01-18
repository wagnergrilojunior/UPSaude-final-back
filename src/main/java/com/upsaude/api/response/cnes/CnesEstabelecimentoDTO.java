package com.upsaude.api.response.cnes;

import com.upsaude.api.response.geral.EnderecoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CnesEstabelecimentoDTO {
    private String cnes;
    private String cnpj;
    private String nomeFantasia;
    private String razaoSocial;
    private String status; 
    private EnderecoResponse endereco;
    private String telefone;
    private String email;
    private OffsetDateTime dataAtualizacaoCnes;
    private List<String> servicosHabilitados;
}
