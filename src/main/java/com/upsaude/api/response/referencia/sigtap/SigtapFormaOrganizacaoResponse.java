package com.upsaude.api.response.referencia.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapFormaOrganizacaoResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
    private String competenciaInicial;
    private String competenciaFinal;
    
    // Dados do subgrupo
    private String subgrupoCodigo;
    private String subgrupoNome;
    
    // Dados do grupo (via subgrupo)
    private String grupoCodigo;
    private String grupoNome;
}
