package com.upsaude.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cnes")
public class CnesProperties {

    private final Soap soap = new Soap();

    @Data
    public static class Soap {
        /**
         * Ex.: https://servicos.saude.gov.br/cnes
         */
        private String baseUrl;

        private String cnesServicePath;
        private String estabelecimentoServiceV1r0Path;
        private String estabelecimentoServiceV2r0Path;
        private String profissionalServicePath;
        private String equipeServicePath;
        private String vinculacaoServicePath;
        private String equipamentoServicePath;
        private String leitoServicePath;

        private String username;
        private String password;

        private int connectTimeoutMs = 10000;
        private int readTimeoutMs = 60000;

        public String cnesServiceEndpoint() {
            return normalize(baseUrl) + normalizePath(cnesServicePath);
        }

        public String estabelecimentoServiceV1r0Endpoint() {
            return normalize(baseUrl) + normalizePath(estabelecimentoServiceV1r0Path);
        }

        public String estabelecimentoServiceV2r0Endpoint() {
            return normalize(baseUrl) + normalizePath(estabelecimentoServiceV2r0Path);
        }

        public String profissionalServiceEndpoint() {
            return normalize(baseUrl) + normalizePath(profissionalServicePath);
        }

        public String equipeServiceEndpoint() {
            return normalize(baseUrl) + normalizePath(equipeServicePath);
        }

        public String vinculacaoServiceEndpoint() {
            return normalize(baseUrl) + normalizePath(vinculacaoServicePath);
        }

        public String equipamentoServiceEndpoint() {
            return normalize(baseUrl) + normalizePath(equipamentoServicePath);
        }

        public String leitoServiceEndpoint() {
            return normalize(baseUrl) + normalizePath(leitoServicePath);
        }

        private String normalize(String url) {
            if (url == null) return "";
            return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        }

        private String normalizePath(String path) {
            if (path == null || path.isBlank()) return "";
            return path.startsWith("/") ? path : "/" + path;
        }
    }
}

