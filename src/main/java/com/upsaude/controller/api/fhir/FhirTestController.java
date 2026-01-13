package com.upsaude.controller.api.fhir;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.config.FhirProperties;
import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.exception.FhirClientException;
import com.upsaude.integration.fhir.service.FhirCacheService;
import com.upsaude.integration.fhir.service.FhirSyncLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fhir/test")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "FHIR Test", description = "Endpoints para teste da integração FHIR")
public class FhirTestController {

    private final FhirClient fhirClient;
    private final FhirCacheService cacheService;
    private final FhirSyncLogService syncLogService;
    private final FhirProperties fhirProperties;

    @GetMapping("/health")
    @Operation(summary = "Verificar conexão com servidor FHIR")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", OffsetDateTime.now());
        response.put("baseUrl", fhirClient.getBaseUrl());

        boolean connected = fhirClient.testConnection();
        response.put("connected", connected);
        response.put("status", connected ? "UP" : "DOWN");

        if (connected) {
            response.put("message", "Conexão com servidor FHIR estabelecida com sucesso");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Não foi possível conectar ao servidor FHIR");
            return ResponseEntity.status(503).body(response);
        }
    }

    @GetMapping("/codesystem/{name}")
    @Operation(summary = "Buscar CodeSystem por nome")
    public ResponseEntity<Map<String, Object>> getCodeSystem(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", OffsetDateTime.now());
        response.put("requested", "CodeSystem/" + name);

        String cacheKey = "codesystem:" + name;
        Optional<CodeSystemDTO> cached = cacheService.get(cacheKey, CodeSystemDTO.class);

        if (cached.isPresent()) {
            response.put("source", "cache");
            response.put("codeSystem", cached.get());
            response.put("conceptCount", cached.get().getConceptCount());
            return ResponseEntity.ok(response);
        }

        try {
            CodeSystemDTO codeSystem = fhirClient.getCodeSystem(name);
            cacheService.put(cacheKey, codeSystem);

            response.put("source", "fhir-server");
            response.put("codeSystem", buildCodeSystemSummary(codeSystem));
            response.put("conceptCount", codeSystem.getConceptCount());
            response.put("concepts",
                    codeSystem.getConcept() != null ? codeSystem.getConcept().stream().limit(10).toList() : List.of());
            response.put("message", "Primeiros 10 conceitos exibidos. Total: " + codeSystem.getConceptCount());

            return ResponseEntity.ok(response);
        } catch (FhirClientException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/valueset/{name}")
    @Operation(summary = "Buscar ValueSet por nome")
    public ResponseEntity<Map<String, Object>> getValueSet(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", OffsetDateTime.now());
        response.put("requested", "ValueSet/" + name);

        String cacheKey = "valueset:" + name;
        Optional<ValueSetDTO> cached = cacheService.get(cacheKey, ValueSetDTO.class);

        if (cached.isPresent()) {
            response.put("source", "cache");
            response.put("valueSet", cached.get());
            return ResponseEntity.ok(response);
        }

        try {
            ValueSetDTO valueSet = fhirClient.getValueSet(name);
            cacheService.put(cacheKey, valueSet);

            response.put("source", "fhir-server");
            response.put("valueSet", buildValueSetSummary(valueSet));

            return ResponseEntity.ok(response);
        } catch (FhirClientException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/codesystem/{name}/concepts")
    @Operation(summary = "Buscar todos os conceitos de um CodeSystem")
    public ResponseEntity<Map<String, Object>> getCodeSystemConcepts(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();

        try {
            String cacheKey = "codesystem:" + name;
            Optional<CodeSystemDTO> cached = cacheService.get(cacheKey, CodeSystemDTO.class);

            CodeSystemDTO codeSystem;
            if (cached.isPresent()) {
                codeSystem = cached.get();
                response.put("source", "cache");
            } else {
                codeSystem = fhirClient.getCodeSystem(name);
                cacheService.put(cacheKey, codeSystem);
                response.put("source", "fhir-server");
            }

            List<ConceptDTO> concepts = codeSystem.getConcept();
            response.put("codeSystem", name);
            response.put("totalConcepts", codeSystem.getConceptCount());
            response.put("concepts", concepts);

            return ResponseEntity.ok(response);
        } catch (FhirClientException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/cache/status")
    @Operation(summary = "Status do cache FHIR")
    public ResponseEntity<Map<String, Object>> cacheStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", OffsetDateTime.now());
        response.put("enabled", cacheService.isEnabled());
        response.put("ttlHours", fhirProperties.getCache().getTtlHours());
        response.put("keyPrefix", fhirProperties.getCache().getKeyPrefix());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cache/clear")
    @Operation(summary = "Limpar cache FHIR")
    public ResponseEntity<Map<String, Object>> clearCache() {
        cacheService.evictAll();
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", OffsetDateTime.now());
        response.put("message", "Cache FHIR limpo com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sync/logs")
    @Operation(summary = "Listar logs de sincronização recentes")
    public ResponseEntity<List<FhirSyncLog>> getSyncLogs() {
        return ResponseEntity.ok(syncLogService.listarRecentes(20));
    }

    @GetMapping("/sync/recursos")
    @Operation(summary = "Listar recursos sincronizados")
    public ResponseEntity<List<String>> getRecursosSincronizados() {
        return ResponseEntity.ok(syncLogService.listarRecursosSincronizados());
    }

    private Map<String, Object> buildCodeSystemSummary(CodeSystemDTO cs) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("id", cs.getId());
        summary.put("name", cs.getName());
        summary.put("title", cs.getTitle());
        summary.put("url", cs.getUrl());
        summary.put("version", cs.getVersion());
        summary.put("status", cs.getStatus());
        summary.put("content", cs.getContent());
        summary.put("count", cs.getCount());
        return summary;
    }

    private Map<String, Object> buildValueSetSummary(ValueSetDTO vs) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("id", vs.getId());
        summary.put("name", vs.getName());
        summary.put("title", vs.getTitle());
        summary.put("url", vs.getUrl());
        summary.put("version", vs.getVersion());
        summary.put("status", vs.getStatus());
        return summary;
    }
}
