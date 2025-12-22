package com.upsaude.api.response.referencia.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapSubgrupoResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
    private String competenciaInicial;
    private String competenciaFinal;
    
    // Dados do grupo
    private String grupoCodigo;
    private String grupoNome;
}
