package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentosRequest {
    private String nome;
    private String tipo;
    private String enderecoJson;
    private String contatoJson;
    private String metadados;
    private List<UUID> enderecosIds;
}

