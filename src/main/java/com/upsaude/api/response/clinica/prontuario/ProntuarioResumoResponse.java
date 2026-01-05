package com.upsaude.api.response.clinica.prontuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioResumoResponse {

    private UUID pacienteId;
    private String pacienteNome;
    private OffsetDateTime ultimaAtualizacao;
    private Integer totalAtendimentos;
    private Integer totalConsultas;
    private Integer totalReceitas;
    private Integer totalDispensacoes;
    private List<String> principaisDiagnosticos;
    private List<ProntuarioEventoRecenteResponse> eventosRecentes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProntuarioEventoRecenteResponse {
        private UUID id;
        private String tipoRegistro;
        private OffsetDateTime dataRegistro;
        private String resumo;
    }
}

