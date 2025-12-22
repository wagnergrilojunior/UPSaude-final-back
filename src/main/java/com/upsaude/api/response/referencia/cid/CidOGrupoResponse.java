package com.upsaude.api.response.referencia.cid;

import lombok.Data;

import java.util.UUID;

@Data
public class CidOGrupoResponse {
    private UUID id;
    private String catinic;
    private String catfim;
    private String descricao;
    private String refer;
}
