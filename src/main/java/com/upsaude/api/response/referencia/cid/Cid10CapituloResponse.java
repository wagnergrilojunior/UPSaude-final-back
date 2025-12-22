package com.upsaude.api.response.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class Cid10CapituloResponse {
    private UUID id;
    private Integer numcap;
    private String catinic;
    private String catfim;
    private String descricao;
    private String descricaoAbreviada;
}
