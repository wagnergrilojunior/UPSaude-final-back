package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioRequest {
    private String nome;
    private String codigo;
    private String cnpj;
    private String registroAns;
    private String contatoJson;
    private String observacoes;
}

