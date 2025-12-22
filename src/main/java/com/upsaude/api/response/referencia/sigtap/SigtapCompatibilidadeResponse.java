package com.upsaude.api.response.referencia.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapCompatibilidadeResponse {
    private UUID id;

    private String codigoCompatibilidadePossivel;
    private String tipoCompatibilidade;

    private String codigoProcedimentoPrincipal;
    private String nomeProcedimentoPrincipal;

    private String codigoProcedimentoSecundario;
    private String nomeProcedimentoSecundario;

    private String competenciaInicial;
    private String competenciaFinal;
    private Integer quantidadePermitida;
}
