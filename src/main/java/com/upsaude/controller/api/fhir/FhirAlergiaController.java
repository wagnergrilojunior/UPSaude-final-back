package com.upsaude.controller.api.fhir;

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
@RequestMapping("/fhir/alergia")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Alergias", description = "Gestão de alergias e reações adversas")
public class FhirAlergiaController {

    private final AlergiaSyncService alergiaSyncService;

    @PostMapping("/sincronizar/todos")
    @Operation(summary = "[FHIR] Sincronizar todos os catálogos de alergias", tags = "FHIR Alergias")
    public ResponseEntity<Map<String, Object>> sincronizarTodos() {
        log.info("Iniciando sincronização completa via API");
        List<SyncResult> results = alergiaSyncService.sincronizarTodos(null);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCESSO");
        response.put("detalhes", results);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sincronizar/alergenos")
    @Operation(summary = "[FHIR] Sincronizar catálogo de alérgenos (CBARA)", tags = "FHIR Alergias")
    public ResponseEntity<Map<String, Object>> sincronizarAlergenos() {
        SyncResult result = alergiaSyncService.sincronizarAlergenos(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/reacoes-adversas")
    @Operation(summary = "[FHIR] Sincronizar catálogo de reações adversas (MedDRA)", tags = "FHIR Alergias")
    public ResponseEntity<Map<String, Object>> sincronizarReacoes() {
        SyncResult result = alergiaSyncService.sincronizarReacoesAdversas(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/criticidade")
    @Operation(summary = "[FHIR] Sincronizar níveis de criticidade", tags = "FHIR Alergias")
    public ResponseEntity<Map<String, Object>> sincronizarCriticidade() {
        SyncResult result = alergiaSyncService.sincronizarCriticidade(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/categorias")
    @Operation(summary = "[FHIR] Sincronizar categorias de agente", tags = "FHIR Alergias")
    public ResponseEntity<Map<String, Object>> sincronizarCategorias() {
        SyncResult result = alergiaSyncService.sincronizarCategoriasAgente(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @GetMapping("/externo/alergenos")
    @Operation(summary = "[FHIR] Consultar alérgenos direto no servidor FHIR", tags = "FHIR Alergias")
    public ResponseEntity<List<ConceptDTO>> consultarAlergenosExternos() {
        return ResponseEntity.ok(alergiaSyncService.consultarAlergenosExternos());
    }

    @GetMapping("/externo/reacoes-adversas")
    @Operation(summary = "[FHIR] Consultar reações adversas direto no servidor FHIR", tags = "FHIR Alergias")
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
