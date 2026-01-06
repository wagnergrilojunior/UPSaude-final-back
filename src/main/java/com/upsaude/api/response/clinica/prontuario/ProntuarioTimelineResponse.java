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
public class ProntuarioTimelineResponse {

    private UUID pacienteId;
    private String pacienteNome;
    private List<ProntuarioEventoResponse> eventos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProntuarioEventoResponse {
        private UUID id;
        private String tipoRegistro;
        private String tipoRegistroEnum;
        private OffsetDateTime dataRegistro;
        private String resumo;
        private UUID atendimentoId;
        private UUID consultaId;
        private UUID receitaId;
        private UUID dispensacaoId;
    }
}
