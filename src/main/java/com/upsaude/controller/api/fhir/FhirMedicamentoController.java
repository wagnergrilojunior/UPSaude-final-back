package com.upsaude.controller.api.fhir;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.integration.fhir.service.farmacia.MedicamentoSyncService;
import com.upsaude.integration.fhir.service.farmacia.MedicamentoSyncService.SyncResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fhir/medicamento")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "FHIR Medicamentos", description = "Sincronização de catálogos de medicamentos via FHIR (RNDS/OBM)")
public class FhirMedicamentoController {

    private final MedicamentoSyncService medicamentoSyncService;

    @PostMapping("/sincronizar/todos")
    @Operation(summary = "Sincronizar todos os catálogos de medicamentos")
    public ResponseEntity<Map<String, Object>> sincronizarTodos() {
        log.info("Iniciando sincronização completa de medicamentos via API");
        List<SyncResult> results = medicamentoSyncService.sincronizarTudo(null);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCESSO");
        response.put("detalhes", results);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sincronizar/principios-ativos")
    @Operation(summary = "Sincronizar catálogo de Princípios Ativos (VTM)")
    public ResponseEntity<Map<String, Object>> sincronizarPrincipiosAtivos() {
        SyncResult result = medicamentoSyncService.sincronizarPrincipiosAtivos(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/medicamentos")
    @Operation(summary = "Sincronizar catálogo de Medicamentos (VMP/AMP)")
    public ResponseEntity<Map<String, Object>> sincronizarMedicamentos() {
        SyncResult result = medicamentoSyncService.sincronizarMedicamentos(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/unidades-medida")
    @Operation(summary = "Sincronizar catálogo de Unidades de Medida")
    public ResponseEntity<Map<String, Object>> sincronizarUnidadesMedida() {
        SyncResult result = medicamentoSyncService.sincronizarUnidadesMedida(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/vias-administracao")
    @Operation(summary = "Sincronizar catálogo de Vias de Administração")
    public ResponseEntity<Map<String, Object>> sincronizarViasAdministracao() {
        SyncResult result = medicamentoSyncService.sincronizarViasAdministracao(null);
        return ResponseEntity.ok(buildSyncResponse(result));
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
