package com.upsaude.dto.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class Cid10GrupoResponse {
    private UUID id;
    private String catinic;
    private String catfim;
    private String descricao;
    private String descricaoAbreviada;
}
