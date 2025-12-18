package com.upsaude.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades da integra??o com o SOA-SIGTAP (DATASUS).
 *
 * <p>Boas pr?ticas para sistemas p?blicos:
 * <ul>
 *   <li>Endpoints e timeouts configur?veis por ambiente (profiles/vari?veis)</li>
 *   <li>Credenciais configur?veis (mesmo sendo p?blicas)</li>
 * </ul>
 */
@Data
@ConfigurationProperties(prefix = "sigtap")
public class SigtapProperties {

    private final Soap soap = new Soap();
    private final Sync sync = new Sync();

    @Data
    public static class Soap {
        /**
         * Ex.: https://servicos.saude.gov.br/sigtap
         */
        private String baseUrl;

        private String procedimentoServicePath;
        private String nivelAgregacaoServicePath;
        private String compatibilidadeServicePath;
        private String compatibilidadePossivelServicePath;

        private String username;
        private String password;

        private int connectTimeoutMs = 10000;
        private int readTimeoutMs = 60000;

        public String procedimentoEndpoint() {
            return normalize(baseUrl) + normalizePath(procedimentoServicePath);
        }

        public String nivelAgregacaoEndpoint() {
            return normalize(baseUrl) + normalizePath(nivelAgregacaoServicePath);
        }

        public String compatibilidadeEndpoint() {
            return normalize(baseUrl) + normalizePath(compatibilidadeServicePath);
        }

        public String compatibilidadePossivelEndpoint() {
            return normalize(baseUrl) + normalizePath(compatibilidadePossivelServicePath);
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

    @Data
    public static class Sync {
        /**
         * Cron mensal (Spring).
         */
        private String cron;

        /**
         * Compet?ncia padr?o (AAAAMM). Se vazio, a aplica??o pode calcular (ex.: m?s anterior).
         */
        private String defaultCompetencia;
    }
}

