package com.upsaude.dto;

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
public class EstabelecimentosDTO {
    private UUID id;
    private String nome;
    private String tipo;
    private String enderecoJson;
    private String contatoJson;
    private String metadados;
    private List<UUID> enderecosIds;
    private Boolean active;
}

