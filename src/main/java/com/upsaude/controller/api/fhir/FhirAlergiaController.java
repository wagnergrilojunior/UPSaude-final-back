package com.upsaude.controller.api.fhir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.service.alergia.AlergiaSyncService;
import com.upsaude.integration.fhir.service.alergia.AlergiaSyncService.SyncResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/fhir/alergia")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "FHIR Alergias", description = "Sincronização e consulta externa de alergias e reações FHIR")
public class FhirAlergiaController {

    private final AlergiaSyncService alergiaSyncService;

    @PostMapping("/sincronizar/todos")
    @Operation(summary = "Sincronizar todos os catálogos de alergias")
    public ResponseEntity<Map<String, Object>> sincronizarTodos() {
        log.info("Iniciando sincronização completa via API");
        List<SyncResult> results = alergiaSyncService.sincronizarTodos(null);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCESSO");
        response.put("detalhes", results);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sincronizar/alergenos")
    @Operation(summary = "Sincronizar catálogo de alérgenos (CBARA)")
    public ResponseEntity<Map<String, Object>> sincronizarAlergenos() {
        SyncResult result = alergiaSyncService.sincronizarAlergenos(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/reacoes-adversas")
    @Operation(summary = "Sincronizar catálogo de reações adversas (MedDRA)")
    public ResponseEntity<Map<String, Object>> sincronizarReacoes() {
        SyncResult result = alergiaSyncService.sincronizarReacoesAdversas(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/criticidade")
    @Operation(summary = "Sincronizar níveis de criticidade")
    public ResponseEntity<Map<String, Object>> sincronizarCriticidade() {
        SyncResult result = alergiaSyncService.sincronizarCriticidade(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/categorias")
    @Operation(summary = "Sincronizar categorias de agente")
    public ResponseEntity<Map<String, Object>> sincronizarCategorias() {
        SyncResult result = alergiaSyncService.sincronizarCategoriasAgente(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @GetMapping("/externo/alergenos")
    @Operation(summary = "Consultar alérgenos direto no FHIR")
    public ResponseEntity<List<ConceptDTO>> consultarAlergenosExternos() {
        return ResponseEntity.ok(alergiaSyncService.consultarAlergenosExternos());
    }

    @GetMapping("/externo/reacoes-adversas")
    @Operation(summary = "Consultar reações adversas direto no FHIR")
    public ResponseEntity<List<ConceptDTO>> consultarReacoesExternas() {
        return ResponseEntity.ok(alergiaSyncService.consultarReacoesAdversasExternas());
    }

    private Map<String, Object> buildSyncResponse(SyncResult result) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCESSO");
        response.put("recurso", result.recurso());
        response.put("total", result.totalEncontrados());
        response.put("novos", result.novosInseridos());
        response.put("atualizados", result.atualizados());
        return response;
    }
}
