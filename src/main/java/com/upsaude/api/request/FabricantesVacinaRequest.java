package com.upsaude.api.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FabricantesVacinaRequest {
    private String nome;
    private String cnpj;
    private String pais;
    private String estado;
    private String cidade;
    private EnderecoRequest endereco;
    private String telefone;
    private String email;
    private String site;
    private String registroAnvisa;
    private String registroMs;
    private String observacoes;
}
