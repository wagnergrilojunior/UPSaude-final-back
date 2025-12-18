package com.upsaude.dto.sigtap;

import lombok.Data;

import java.util.UUID;

@Data
public class SigtapProcedimentoDetalheResponse {
    private UUID id;
    private UUID procedimentoId;
    private String competenciaInicial;
    private String competenciaFinal;
    private String descricaoCompleta;

    // JSON serializado do retorno do SIGTAP (mant?m a estrutura original)
    private String cids;
    private String cbos;
    private String categoriasCbo;
    private String tiposLeito;
    private String servicosClassificacoes;
    private String habilitacoes;
    private String gruposHabilitacao;
    private String incrementos;
    private String componentesRede;
    private String origensSigtap;
    private String origensSiaSih;
    private String regrasCondicionadas;
    private String renases;
    private String tuss;
}

