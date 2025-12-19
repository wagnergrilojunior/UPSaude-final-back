package com.upsaude.dto.referencia.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapOcupacaoResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
}
