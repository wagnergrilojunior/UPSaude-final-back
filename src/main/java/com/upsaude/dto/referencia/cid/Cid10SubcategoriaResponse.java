package com.upsaude.dto.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class Cid10SubcategoriaResponse {
    private UUID id;
    private String subcat;
    private String categoriaCat;
    private String categoriaDescricao;
    private String classif;
    private String restrSexo;
    private String causaObito;
    private String descricao;
    private String descrAbrev;
    private String refer;
    private String excluidos;
}
