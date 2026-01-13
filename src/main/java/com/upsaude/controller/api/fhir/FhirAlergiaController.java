package com.upsaude.controller.api.fhir;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.service.alergia.AlergiaSyncService;
import com.upsaude.integration.fhir.service.alergia.AlergiaSyncService.SyncResult;
import com.upsaude.repository.alergia.AlergenoRepository;
import com.upsaude.repository.alergia.CategoriaAgenteAlergiaRepository;
import com.upsaude.repository.alergia.CriticidadeAlergiaRepository;
import com.upsaude.repository.alergia.ReacaoAdversaCatalogoRepository;

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
    private final AlergenoRepository alergenoRepository;
    private final ReacaoAdversaCatalogoRepository reacaoAdversaRepository;
    private final CriticidadeAlergiaRepository criticidadeRepository;
    private final CategoriaAgenteAlergiaRepository categoriaAgenteRepository;

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

    // ==================== CONSULTA LOCAL (SINCRONIZADO) ====================

    @GetMapping("/alergenos")
    @Operation(summary = "Listar alérgenos sincronizados")
    public ResponseEntity<List<com.upsaude.entity.alergia.Alergeno>> listarAlergenos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(alergenoRepository.findByAtivoTrue(
                org.springframework.data.domain.PageRequest.of(page, size)).getContent());
    }

    @GetMapping("/alergenos/{id}")
    @Operation(summary = "Buscar alérgeno por ID")
    public ResponseEntity<com.upsaude.entity.alergia.Alergeno> buscarAlergenoPorId(
            @PathVariable java.util.UUID id) {
        return alergenoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/alergenos/buscar")
    @Operation(summary = "Buscar alérgenos por termo (nome ou código)")
    public ResponseEntity<List<com.upsaude.entity.alergia.Alergeno>> buscarAlergenos(
            @RequestParam String termo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(alergenoRepository.buscarPorTermo(termo,
                org.springframework.data.domain.PageRequest.of(page, size)).getContent());
    }

    @GetMapping("/reacoes-adversas")
    @Operation(summary = "Listar reações adversas sincronizadas")
    public ResponseEntity<List<com.upsaude.entity.alergia.ReacaoAdversaCatalogo>> listarReacoesAdversas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(reacaoAdversaRepository.findByAtivoTrue(
                org.springframework.data.domain.PageRequest.of(page, size)).getContent());
    }

    @GetMapping("/reacoes-adversas/{id}")
    @Operation(summary = "Buscar reação adversa por ID")
    public ResponseEntity<com.upsaude.entity.alergia.ReacaoAdversaCatalogo> buscarReacaoPorId(
            @PathVariable java.util.UUID id) {
        return reacaoAdversaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/criticidade")
    @Operation(summary = "Listar níveis de criticidade sincronizados")
    public ResponseEntity<List<com.upsaude.entity.alergia.CriticidadeAlergia>> listarCriticidade() {
        return ResponseEntity.ok(criticidadeRepository.findAll());
    }

    @GetMapping("/categorias-agente")
    @Operation(summary = "Listar categorias de agente sincronizadas")
    public ResponseEntity<List<com.upsaude.entity.alergia.CategoriaAgenteAlergia>> listarCategoriasAgente() {
        return ResponseEntity.ok(categoriaAgenteRepository.findAll());
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos dados de alergias sincronizados")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("alergenos", alergenoRepository.count());
        response.put("reacoesAdversas", reacaoAdversaRepository.count());
        response.put("criticidade", criticidadeRepository.count());
        response.put("categoriasAgente", categoriaAgenteRepository.count());
        return ResponseEntity.ok(response);
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
