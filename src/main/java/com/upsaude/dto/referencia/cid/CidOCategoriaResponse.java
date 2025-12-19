package com.upsaude.dto.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class CidOCategoriaResponse {
    private UUID id;
    private String cat;
    private String descricao;
    private String refer;
}
