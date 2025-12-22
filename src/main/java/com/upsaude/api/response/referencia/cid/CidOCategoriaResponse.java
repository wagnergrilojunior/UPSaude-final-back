package com.upsaude.api.response.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class CidOCategoriaResponse {
    private UUID id;
    private String cat;
    private String descricao;
    private String refer;
}
