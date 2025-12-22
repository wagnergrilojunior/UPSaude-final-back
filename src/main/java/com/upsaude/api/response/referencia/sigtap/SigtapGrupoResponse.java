package com.upsaude.api.response.referencia.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapGrupoResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
    private String competenciaInicial;
    private String competenciaFinal;
}
