package com.upsaude.api.response.referencia.sigtap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentoSigtapSimplificadoResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
    private BigDecimal valorServicoHospitalar;
    private BigDecimal valorServicoAmbulatorial;
    private BigDecimal valorServicoProfissional;
}

