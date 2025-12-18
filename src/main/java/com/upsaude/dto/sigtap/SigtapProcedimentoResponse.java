package com.upsaude.dto.sigtap;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SigtapProcedimentoResponse {
    private UUID id;
    private String codigoOficial;
    private String nome;
    private String competenciaInicial;
    private String competenciaFinal;

    private String grupoCodigo;
    private String grupoNome;
    private String subgrupoCodigo;
    private String subgrupoNome;
    private String formaOrganizacaoCodigo;
    private String formaOrganizacaoNome;

    private String sexoPermitido;
    private Integer idadeMinima;
    private Integer idadeMaxima;
    private Integer mediaDiasInternacao;
    private Integer quantidadeMaximaDias;
    private Integer limiteMaximo;

    private BigDecimal valorServicoHospitalar;
    private BigDecimal valorServicoAmbulatorial;
    private BigDecimal valorServicoProfissional;
}

