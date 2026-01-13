package com.upsaude.integration.rnds.client;

import com.upsaude.config.RndsProperties;
import com.upsaude.integration.rnds.dto.RndsAppointmentDTO;
import com.upsaude.integration.rnds.dto.RndsEncounterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RndsClient {

    private final RestTemplate rndsRestTemplate;
    private final RndsProperties rndsProperties;

    public RndsResponse enviarAppointment(RndsAppointmentDTO appointment) {
        try {
            String url = rndsProperties.getBaseUrl() + "/Appointment";
            HttpHeaders headers = criarHeaders();
            HttpEntity<RndsAppointmentDTO> request = new HttpEntity<>(appointment, headers);

            log.debug("Enviando Appointment para RNDS: {}", url);
            ResponseEntity<Map<String, Object>> response = rndsRestTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            return RndsResponse.builder()
                    .statusCode(response.getStatusCode().value())
                    .body(response.getBody())
                    .protocolo(extrairProtocolo(response.getBody()))
                    .build();
        } catch (RestClientException e) {
            log.error("Erro ao enviar Appointment para RNDS: {}", e.getMessage(), e);
            throw new RndsClientException("Erro ao enviar Appointment para RNDS", e);
        }
    }

    public RndsResponse enviarEncounter(RndsEncounterDTO encounter) {
        try {
            String url = rndsProperties.getBaseUrl() + "/Encounter";
            HttpHeaders headers = criarHeaders();
            HttpEntity<RndsEncounterDTO> request = new HttpEntity<>(encounter, headers);

            log.debug("Enviando Encounter para RNDS: {}", url);
            ResponseEntity<Map<String, Object>> response = rndsRestTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            return RndsResponse.builder()
                    .statusCode(response.getStatusCode().value())
                    .body(response.getBody())
                    .protocolo(extrairProtocolo(response.getBody()))
                    .build();
        } catch (RestClientException e) {
            log.error("Erro ao enviar Encounter para RNDS: {}", e.getMessage(), e);
            throw new RndsClientException("Erro ao enviar Encounter para RNDS", e);
        }
    }

    private HttpHeaders criarHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (rndsProperties.getApiKey() != null) {
            headers.set("X-API-Key", rndsProperties.getApiKey());
        }
        if (rndsProperties.getApiSecret() != null) {
            headers.set("X-API-Secret", rndsProperties.getApiSecret());
        }
        return headers;
    }

    private String extrairProtocolo(Map<String, Object> body) {
        if (body == null) return null;
        Object id = body.get("id");
        if (id != null) {
            return id.toString();
        }
        return null;
    }

    @lombok.Builder
    @lombok.Data
    public static class RndsResponse {
        private int statusCode;
        private Map<String, Object> body;
        private String protocolo;
    }

    public static class RndsClientException extends RuntimeException {
        public RndsClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
