package com.upsaude.api.response.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class Cid10CategoriaResponse {
    private UUID id;
    private String cat;
    private String classif;
    private String descricao;
    private String descrAbrev;
    private String refer;
    private String excluidos;
}
