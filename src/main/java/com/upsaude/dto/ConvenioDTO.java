package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioDTO {
    private UUID id;
    private String nome;
    private String codigo;
    private String cnpj;
    private String registroAns;
    private String contatoJson;
    private String observacoes;
    private Boolean active;
}

