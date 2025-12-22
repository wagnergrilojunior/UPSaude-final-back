package com.upsaude.api.response.referencia.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapTussResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
}
