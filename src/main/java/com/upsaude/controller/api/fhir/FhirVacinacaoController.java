package com.upsaude.controller.api.fhir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upsaude.integration.fhir.dto.ConceptDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.entity.vacinacao.EstrategiaVacinacao;
import com.upsaude.entity.vacinacao.FabricanteImunobiologico;
import com.upsaude.entity.vacinacao.Imunobiologico;
import com.upsaude.entity.vacinacao.LocalAplicacao;
import com.upsaude.entity.vacinacao.TipoDose;
import com.upsaude.entity.vacinacao.ViaAdministracao;
import com.upsaude.integration.fhir.service.vacinacao.VacinacaoSyncService;
import com.upsaude.integration.fhir.service.vacinacao.VacinacaoSyncService.SyncResult;
import com.upsaude.repository.vacinacao.EstrategiaVacinacaoRepository;
import com.upsaude.repository.vacinacao.FabricanteImunobiologicoRepository;
import com.upsaude.repository.vacinacao.ImunobiologicoRepository;
import com.upsaude.repository.vacinacao.LocalAplicacaoRepository;
import com.upsaude.repository.vacinacao.TipoDoseRepository;
import com.upsaude.repository.vacinacao.ViaAdministracaoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fhir/vacinacao")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Vacinação", description = "Gestão de imunobiológicos e vacinação")
public class FhirVacinacaoController {

    private final VacinacaoSyncService syncService;
    private final ImunobiologicoRepository imunobiologicoRepository;
    private final FabricanteImunobiologicoRepository fabricanteRepository;
    private final TipoDoseRepository tipoDoseRepository;
    private final LocalAplicacaoRepository localAplicacaoRepository;
    private final ViaAdministracaoRepository viaAdministracaoRepository;
    private final EstrategiaVacinacaoRepository estrategiaRepository;

    @PostMapping("/sincronizar/imunobiologicos")
    @Operation(summary = "[FHIR] Sincronizar catálogo de vacinas do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarImunobiologicos() {
        SyncResult result = syncService.sincronizarImunobiologicos(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/fabricantes")
    @Operation(summary = "[FHIR] Sincronizar fabricantes de vacinas do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarFabricantes() {
        SyncResult result = syncService.sincronizarFabricantes(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/doses")
    @Operation(summary = "[FHIR] Sincronizar tipos de dose do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarDoses() {
        SyncResult result = syncService.sincronizarTiposDose(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/locais-aplicacao")
    @Operation(summary = "[FHIR] Sincronizar locais de aplicação do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarLocaisAplicacao() {
        SyncResult result = syncService.sincronizarLocaisAplicacao(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/vias-administracao")
    @Operation(summary = "[FHIR] Sincronizar vias de administração do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarViasAdministracao() {
        SyncResult result = syncService.sincronizarViasAdministracao(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/estrategias")
    @Operation(summary = "[FHIR] Sincronizar estratégias de vacinação do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarEstrategias() {
        SyncResult result = syncService.sincronizarEstrategias(null);
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/sincronizar/todos")
    @Operation(summary = "[FHIR] Sincronizar todos os recursos de vacinação do FHIR", tags = "FHIR Vacinação")
    public ResponseEntity<Map<String, Object>> sincronizarTodos() {
        List<SyncResult> results = new ArrayList<>();

        log.info("Iniciando sincronização completa de vacinação...");

        results.add(syncService.sincronizarImunobiologicos(null));
        results.add(syncService.sincronizarFabricantes(null));
        results.add(syncService.sincronizarTiposDose(null));
        results.add(syncService.sincronizarLocaisAplicacao(null));
        results.add(syncService.sincronizarViasAdministracao(null));
        results.add(syncService.sincronizarEstrategias(null));

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Sincronização completa realizada");
        response.put("resultados", results);

        int totalNovos = results.stream().mapToInt(SyncResult::novosInseridos).sum();
        int totalAtualizados = results.stream().mapToInt(SyncResult::atualizados).sum();
        response.put("totalNovos", totalNovos);
        response.put("totalAtualizados", totalAtualizados);

        log.info("Sincronização completa concluída: {} novos, {} atualizados", totalNovos, totalAtualizados);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/imunobiologicos")
    @Operation(summary = "Listar vacinas sincronizadas")
    public ResponseEntity<List<Imunobiologico>> listarImunobiologicos() {
        return ResponseEntity.ok(imunobiologicoRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/fabricantes")
    @Operation(summary = "Listar fabricantes sincronizados")
    public ResponseEntity<List<FabricanteImunobiologico>> listarFabricantes() {
        return ResponseEntity.ok(fabricanteRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/doses")
    @Operation(summary = "Listar tipos de dose sincronizados")
    public ResponseEntity<List<TipoDose>> listarDoses() {
        return ResponseEntity.ok(tipoDoseRepository.findByAtivoTrueOrderByOrdemSequenciaAsc());
    }

    @GetMapping("/locais-aplicacao")
    @Operation(summary = "Listar locais de aplicação sincronizados")
    public ResponseEntity<List<LocalAplicacao>> listarLocaisAplicacao() {
        return ResponseEntity.ok(localAplicacaoRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/vias-administracao")
    @Operation(summary = "Listar vias de administração sincronizadas")
    public ResponseEntity<List<ViaAdministracao>> listarViasAdministracao() {
        return ResponseEntity.ok(viaAdministracaoRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/estrategias")
    @Operation(summary = "Listar estratégias de vacinação sincronizadas")
    public ResponseEntity<List<EstrategiaVacinacao>> listarEstrategias() {
        return ResponseEntity.ok(estrategiaRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/externo/imunobiologicos")
    @Operation(summary = "Consultar imunobiológicos diretamente no FHIR (Live)", tags = "FHIR Vacinação")
    public ResponseEntity<List<ConceptDTO>> consultarImunobiologicosExternos() {
        return ResponseEntity.ok(syncService.consultarImunobiologicosExternos());
    }

    @GetMapping("/externo/fabricantes")
    @Operation(summary = "Consultar fabricantes diretamente no FHIR (Live)", tags = "FHIR Vacinação")
    public ResponseEntity<List<ConceptDTO>> consultarFabricantesExternos() {
        return ResponseEntity.ok(syncService.consultarFabricantesExternos());
    }

    @GetMapping("/externo/doses")
    @Operation(summary = "Consultar tipos de dose diretamente no FHIR (Live)", tags = "FHIR Vacinação")
    public ResponseEntity<List<ConceptDTO>> consultarDosesExternas() {
        return ResponseEntity.ok(syncService.consultarTiposDoseExternos());
    }

    @GetMapping("/externo/locais-aplicacao")
    @Operation(summary = "Consultar locais de aplicação diretamente no FHIR (Live)", tags = "FHIR Vacinação")
    public ResponseEntity<List<ConceptDTO>> consultarLocaisAplicacaoExternos() {
        return ResponseEntity.ok(syncService.consultarLocaisAplicacaoExternos());
    }

    @GetMapping("/externo/vias-administracao")
    @Operation(summary = "Consultar vias de administração diretamente no FHIR (Live)", tags = "FHIR Vacinação")
    public ResponseEntity<List<ConceptDTO>> consultarViasAdministracaoExternas() {
        return ResponseEntity.ok(syncService.consultarViasAdministracaoExternos());
    }

    @GetMapping("/externo/estrategias")
    @Operation(summary = "Consultar estratégias diretamente no FHIR (Live)", tags = "FHIR Vacinação")
    public ResponseEntity<List<ConceptDTO>> consultarEstrategiasExternas() {
        return ResponseEntity.ok(syncService.consultarEstrategiasExternos());
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos dados sincronizados")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("imunobiologicos", imunobiologicoRepository.countByAtivoTrue());
        response.put("fabricantes", fabricanteRepository.countByAtivoTrue());
        response.put("tiposDose", tipoDoseRepository.countByAtivoTrue());
        response.put("locaisAplicacao", localAplicacaoRepository.countByAtivoTrue());
        response.put("viasAdministracao", viaAdministracaoRepository.countByAtivoTrue());
        response.put("estrategias", estrategiaRepository.countByAtivoTrue());
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> buildSyncResponse(SyncResult result) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("recurso", result.recurso());
        response.put("totalEncontrados", result.totalEncontrados());
        response.put("novosInseridos", result.novosInseridos());
        response.put("atualizados", result.atualizados());
        return response;
    }
}
