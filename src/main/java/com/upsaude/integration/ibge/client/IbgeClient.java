package com.upsaude.integration.ibge.client;

import com.upsaude.integration.ibge.dto.IbgeEstadoDTO;
import com.upsaude.integration.ibge.dto.IbgeMunicipioDTO;
import com.upsaude.integration.ibge.dto.IbgeProjecaoPopulacaoDTO;
import com.upsaude.integration.ibge.dto.IbgeRegiaoDTO;
import com.upsaude.integration.ibge.exception.IbgeIntegrationException;
import com.upsaude.integration.ibge.exception.IbgeTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Cliente HTTP para integração com a API IBGE
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IbgeClient {

    @Qualifier("ibgeRestTemplate")
    private final RestTemplate restTemplate;
    private final IbgeClientConfig config;

    /**
     * Busca todas as regiões do Brasil
     * Endpoint: GET /api/v1/localidades/regioes
     */
    public List<IbgeRegiaoDTO> buscarRegioes() {
        String url = config.getBaseUrl() + "/localidades/regioes";
        return executarComRetry(
                url,
                new ParameterizedTypeReference<List<IbgeRegiaoDTO>>() {},
                config.getRetry().getMaxAttempts(),
                "buscar regiões"
        );
    }

    /**
     * Busca todos os estados do Brasil
     * Endpoint: GET /api/v1/localidades/estados
     */
    public List<IbgeEstadoDTO> buscarEstados() {
        String url = config.getBaseUrl() + "/localidades/estados";
        return executarComRetry(
                url,
                new ParameterizedTypeReference<List<IbgeEstadoDTO>>() {},
                config.getRetry().getMaxAttempts(),
                "buscar estados"
        );
    }

    /**
     * Busca municípios por UF
     * Endpoint: GET /api/v1/localidades/estados/{UF}/municipios
     */
    public List<IbgeMunicipioDTO> buscarMunicipiosPorUf(String uf) {
        String url = config.getBaseUrl() + "/localidades/estados/" + uf + "/municipios";
        return executarComRetry(
                url,
                new ParameterizedTypeReference<List<IbgeMunicipioDTO>>() {},
                config.getRetry().getMaxAttempts(),
                "buscar municípios por UF: " + uf
        );
    }

    /**
     * Busca município por código IBGE
     * Endpoint: GET /api/v1/localidades/municipios/{codigo_ibge}
     */
    public IbgeMunicipioDTO buscarMunicipioPorCodigoIbge(String codigoIbge) {
        String url = config.getBaseUrl() + "/localidades/municipios/" + codigoIbge;
        List<IbgeMunicipioDTO> resultado = executarComRetry(
                url,
                new ParameterizedTypeReference<List<IbgeMunicipioDTO>>() {},
                config.getRetry().getMaxAttemptsValidation(),
                "buscar município por código IBGE: " + codigoIbge
        );
        // A API IBGE pode retornar uma lista mesmo para busca por código único
        return resultado != null && !resultado.isEmpty() ? resultado.get(0) : null;
    }

    /**
     * Busca projeção de população para um município
     * Endpoint: GET /api/v1/projecoes/populacao/{codigoMunicipio}
     * Nota: A estrutura exata da API pode variar. Este método será ajustado conforme necessário.
     */
    public IbgeProjecaoPopulacaoDTO buscarProjecaoPopulacao(String codigoMunicipio) {
        String url = config.getBaseUrl() + "/projecoes/populacao/" + codigoMunicipio;
        try {
            ResponseEntity<IbgeProjecaoPopulacaoDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    IbgeProjecaoPopulacaoDTO.class
            );
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
            return null;
        } catch (Exception e) {
            log.warn("Não foi possível buscar projeção de população para município {}: {}", codigoMunicipio, e.getMessage());
            return null; // Retorna null se não conseguir buscar (não é crítico)
        }
    }

    /**
     * Executa requisição HTTP com retry e tratamento de erros
     */
    private <T> T executarComRetry(
            String url,
            ParameterizedTypeReference<T> responseType,
            int maxAttempts,
            String operacao
    ) {
        Exception lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                log.debug("Tentativa {}/{} - {} - URL: {}", attempt, maxAttempts, operacao, url);
                ResponseEntity<T> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        responseType
                );

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    log.debug("Sucesso na operação: {}", operacao);
                    return response.getBody();
                } else {
                    throw new IbgeIntegrationException(
                            String.format("Resposta inválida da API IBGE para %s. Status: %s", operacao, response.getStatusCode())
                    );
                }
            } catch (ResourceAccessException e) {
                lastException = e;
                if (e.getCause() instanceof java.net.SocketTimeoutException ||
                    e.getCause() instanceof java.net.ConnectException) {
                    log.warn("Timeout ou erro de conexão na tentativa {}/{} - {}: {}", attempt, maxAttempts, operacao, e.getMessage());
                    if (attempt < maxAttempts) {
                        try {
                            Thread.sleep(config.getRetry().getBackoffMillis() * attempt);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new IbgeIntegrationException("Operação interrompida: " + operacao, ie);
                        }
                        continue;
                    } else {
                        throw new IbgeTimeoutException("Timeout após " + maxAttempts + " tentativas: " + operacao, e);
                    }
                }
                throw new IbgeIntegrationException("Erro de acesso à API IBGE: " + operacao, e);
            } catch (RestClientException e) {
                lastException = e;
                log.error("Erro na requisição à API IBGE (tentativa {}/{}): {} - {}", attempt, maxAttempts, operacao, e.getMessage());
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(config.getRetry().getBackoffMillis() * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IbgeIntegrationException("Operação interrompida: " + operacao, ie);
                    }
                } else {
                    throw new IbgeIntegrationException("Erro após " + maxAttempts + " tentativas: " + operacao, e);
                }
            }
        }

        throw new IbgeIntegrationException("Falha na operação após " + maxAttempts + " tentativas: " + operacao, lastException);
    }
}

