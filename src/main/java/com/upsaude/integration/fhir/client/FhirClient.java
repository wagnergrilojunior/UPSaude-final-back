package com.upsaude.integration.fhir.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.upsaude.config.FhirProperties;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.exception.FhirClientException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class FhirClient {

    private final WebClient webClient;
    private final FhirProperties fhirProperties;

    public FhirClient(@Qualifier("fhirWebClient") WebClient webClient, FhirProperties fhirProperties) {
        this.webClient = webClient;
        this.fhirProperties = fhirProperties;
    }

    public CodeSystemDTO getCodeSystem(String name) {
        String path = "/CodeSystem-" + name + ".json";
        log.info("Buscando CodeSystem: {}", name);

        try {
            return webClient.get()
                    .uri(path)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode().value() == 404) {
                            return Mono.error(FhirClientException.notFound("CodeSystem/" + name));
                        }
                        return Mono.error(FhirClientException.serverError(
                                response.statusCode().value(), "Erro do cliente"));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(FhirClientException.serverError(
                            response.statusCode().value(), "Erro do servidor FHIR")))
                    .bodyToMono(CodeSystemDTO.class)
                    .block();
        } catch (FhirClientException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao buscar CodeSystem {}: {}", name, e.getMessage());
            throw FhirClientException.connectionError(fhirProperties.getBaseUrl() + path, e);
        }
    }

    public ValueSetDTO getValueSet(String name) {
        String path = "/ValueSet-" + name + ".json";
        log.info("Buscando ValueSet: {}", name);

        try {
            return webClient.get()
                    .uri(path)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode().value() == 404) {
                            return Mono.error(FhirClientException.notFound("ValueSet/" + name));
                        }
                        return Mono.error(FhirClientException.serverError(
                                response.statusCode().value(), "Erro do cliente"));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(FhirClientException.serverError(
                            response.statusCode().value(), "Erro do servidor FHIR")))
                    .bodyToMono(ValueSetDTO.class)
                    .block();
        } catch (FhirClientException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao buscar ValueSet {}: {}", name, e.getMessage());
            throw FhirClientException.connectionError(fhirProperties.getBaseUrl() + path, e);
        }
    }

    public <T> T getResource(String path, Class<T> type) {
        log.info("Buscando recurso FHIR: {}", path);

        try {
            return webClient.get()
                    .uri(path)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode().value() == 404) {
                            return Mono.error(FhirClientException.notFound(path));
                        }
                        return Mono.error(FhirClientException.serverError(
                                response.statusCode().value(), "Erro do cliente"));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(FhirClientException.serverError(
                            response.statusCode().value(), "Erro do servidor FHIR")))
                    .bodyToMono(type)
                    .block();
        } catch (FhirClientException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao buscar recurso {}: {}", path, e.getMessage());
            throw FhirClientException.connectionError(fhirProperties.getBaseUrl() + path, e);
        }
    }

    public boolean testConnection() {
        try {
            String response = webClient.get()
                    .uri("/metadata")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return response != null && response.contains("CapabilityStatement");
        } catch (Exception e) {
            log.warn("Teste de conexão FHIR falhou: {}", e.getMessage());
            return false;
        }
    }

    public String getBaseUrl() {
        return fhirProperties.getBaseUrl();
    }
}
