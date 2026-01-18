package com.upsaude.api.response.integracoes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IbgeSincronizacaoResponse {

    @Builder.Default
    private Integer regioesSincronizadas = 0;

    @Builder.Default
    private Integer estadosSincronizados = 0;

    @Builder.Default
    private Integer municipiosSincronizados = 0;

    @Builder.Default
    private Integer populacaoAtualizada = 0;

    @Builder.Default
    private List<String> regioesErros = new ArrayList<>();

    @Builder.Default
    private List<String> estadosErros = new ArrayList<>();

    @Builder.Default
    private List<String> municipiosErros = new ArrayList<>();

    @Builder.Default
    private List<String> populacaoErros = new ArrayList<>();

    private Duration tempoExecucao;

    @Builder.Default
    private List<String> etapasExecutadas = new ArrayList<>();
}

