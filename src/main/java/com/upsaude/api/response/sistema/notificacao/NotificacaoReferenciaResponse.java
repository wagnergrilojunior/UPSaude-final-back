package com.upsaude.api.response.sistema.notificacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Classe auxiliar para referências simplificadas no NotificacaoResponse
 */
public class NotificacaoReferenciaResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstabelecimentoRef {
        private UUID id;
        private String nome;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PacienteRef {
        private UUID id;
        private String nome;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfissionalRef {
        private UUID id;
        private String nome;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AgendamentoRef {
        private UUID id;
        private String dataHora;
        private String status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemplateRef {
        private UUID id;
        private String nome;
        private Integer brevoTemplateId;
    }
}
