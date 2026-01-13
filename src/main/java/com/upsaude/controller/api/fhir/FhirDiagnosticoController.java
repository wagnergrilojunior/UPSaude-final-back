package com.upsaude.controller.api.fhir;

import com.upsaude.integration.fhir.service.diagnostico.DiagnosticoSyncService;
import com.upsaude.integration.fhir.service.diagnostico.DiagnosticoSyncService.SyncResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fhir/diagnostico")
@RequiredArgsConstructor
@Tag(name = "FHIR Diagnósticos", description = "Endpoints para sincronização de CID-10 e CIAP-2 via FHIR")
public class FhirDiagnosticoController {

    private final DiagnosticoSyncService syncService;

    @PostMapping("/sincronizar/cid10")
    @Operation(summary = "Sincroniza catálogo CID-10 do FHIR Health.gov.br")
    public ResponseEntity<SyncResult> syncCid10() {
        return ResponseEntity.ok(syncService.syncCid10());
    }

    @PostMapping("/sincronizar/ciap2")
    @Operation(summary = "Sincroniza catálogo CIAP-2 do FHIR Health.gov.br")
    public ResponseEntity<SyncResult> syncCiap2() {
        return ResponseEntity.ok(syncService.syncCiap2());
    }

    @PostMapping("/sincronizar/todos")
    @Operation(summary = "Sincroniza todos os catálogos de diagnóstico (CID-10 e CIAP-2)")
    public ResponseEntity<List<SyncResult>> syncAll() {
        List<SyncResult> results = new ArrayList<>();
        results.add(syncService.syncCid10());
        results.add(syncService.syncCiap2());
        return ResponseEntity.ok(results);
    }
}
