package com.upsaude.controller.api.fhir;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.service.geografico.GeografiaFhirSyncService;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fhir/dados-referencia")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "FHIR Dados de Referência", description = "Endpoints para dados demográficos e geográficos FHIR")
public class FhirDadosReferenciaController {

    private final GeografiaFhirSyncService geografiaSyncService;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    // ==================== SINCRONIZAÇÃO ====================

    @PostMapping("/geografia/sincronizar/estados")
    @Operation(summary = "Sincronizar estados com FHIR BRDivisaoGeografica")
    public ResponseEntity<Map<String, Object>> sincronizarEstados() {
        FhirSyncLog result = geografiaSyncService.sincronizarEstados();
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/geografia/sincronizar/municipios")
    @Operation(summary = "Sincronizar municípios com FHIR BRDivisaoGeografica")
    public ResponseEntity<Map<String, Object>> sincronizarMunicipios() {
        FhirSyncLog result = geografiaSyncService.sincronizarMunicipios();
        return ResponseEntity.ok(buildSyncResponse(result));
    }

    @PostMapping("/geografia/sincronizar/todos")
    @Operation(summary = "Sincronizar todos os dados geográficos com FHIR")
    public ResponseEntity<Map<String, Object>> sincronizarGeografiaCompleta() {
        List<FhirSyncLog> results = geografiaSyncService.sincronizarTodos();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Sincronização geográfica completa realizada");
        response.put("resultados", results);

        return ResponseEntity.ok(response);
    }

    // ==================== CONSULTA DIRETA FHIR (LIVE) ====================

    @GetMapping("/geografia/externo/divisoes")
    @Operation(summary = "Consultar divisões geográficas diretamente no FHIR (Live)")
    public ResponseEntity<List<ConceptDTO>> consultarDivisoesExternas() {
        return ResponseEntity.ok(geografiaSyncService.consultarDivisoesGeograficasExternas());
    }

    // ==================== CONSULTA LOCAL (SINCRONIZADO) ====================

    @GetMapping("/geografia/estados")
    @Operation(summary = "Listar estados sincronizados")
    public ResponseEntity<List<Estados>> listarEstados() {
        return ResponseEntity.ok(estadosRepository.findAll());
    }

    @GetMapping("/geografia/estados/{sigla}")
    @Operation(summary = "Buscar estado por sigla")
    public ResponseEntity<Estados> buscarEstadoPorSigla(@PathVariable String sigla) {
        return estadosRepository.findBySigla(sigla.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/geografia/municipios")
    @Operation(summary = "Listar municípios sincronizados (paginado)")
    public ResponseEntity<List<Cidades>> listarMunicipios(
            @RequestParam(required = false) String uf,
            @RequestParam(defaultValue = "100") int limit) {
        if (uf != null) {
            return ResponseEntity.ok(cidadesRepository.findByEstadoSiglaOrderByNomeAsc(uf.toUpperCase())
                    .stream().limit(limit).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(cidadesRepository.findAll().stream().limit(limit).collect(Collectors.toList()));
    }

    @GetMapping("/geografia/municipios/{codigoIbge}")
    @Operation(summary = "Buscar município por código IBGE")
    public ResponseEntity<Cidades> buscarMunicipioPorCodigo(@PathVariable String codigoIbge) {
        return cidadesRepository.findByCodigoIbge(codigoIbge)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/geografia/municipios/buscar")
    @Operation(summary = "Buscar municípios por nome")
    public ResponseEntity<List<Cidades>> buscarMunicipiosPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(cidadesRepository.findByNomeContainingIgnoreCase(nome));
    }

    // ==================== DADOS DEMOGRÁFICOS (ENUMS COM FHIR) ====================

    @GetMapping("/demografico/raca-cor")
    @Operation(summary = "Listar opções de Raça/Cor (BRRacaCor)")
    public ResponseEntity<List<Map<String, Object>>> listarRacaCor() {
        List<Map<String, Object>> result = Arrays.stream(RacaCorEnum.values())
                .map(this::enumToMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/demografico/raca-cor/{codigo}")
    @Operation(summary = "Buscar Raça/Cor por código")
    public ResponseEntity<Map<String, Object>> buscarRacaCorPorCodigo(@PathVariable Integer codigo) {
        RacaCorEnum raca = RacaCorEnum.fromCodigo(codigo);
        if (raca == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enumToMap(raca));
    }

    @GetMapping("/demografico/raca-cor/fhir/{codigoFhir}")
    @Operation(summary = "Buscar Raça/Cor por código FHIR")
    public ResponseEntity<Map<String, Object>> buscarRacaCorPorCodigoFhir(@PathVariable String codigoFhir) {
        RacaCorEnum raca = RacaCorEnum.fromCodigoFhir(codigoFhir);
        if (raca == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enumToMap(raca));
    }

    @GetMapping("/demografico/sexo")
    @Operation(summary = "Listar opções de Sexo (AdministrativeGender)")
    public ResponseEntity<List<Map<String, Object>>> listarSexo() {
        List<Map<String, Object>> result = Arrays.stream(SexoEnum.values())
                .map(this::enumToMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/demografico/sexo/{codigo}")
    @Operation(summary = "Buscar Sexo por código")
    public ResponseEntity<Map<String, Object>> buscarSexoPorCodigo(@PathVariable Integer codigo) {
        SexoEnum sexo = SexoEnum.fromCodigo(codigo);
        if (sexo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enumToMap(sexo));
    }

    @GetMapping("/demografico/sexo/fhir/{codigoFhir}")
    @Operation(summary = "Buscar Sexo por código FHIR")
    public ResponseEntity<Map<String, Object>> buscarSexoPorCodigoFhir(@PathVariable String codigoFhir) {
        SexoEnum sexo = SexoEnum.fromCodigoFhir(codigoFhir);
        if (sexo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enumToMap(sexo));
    }

    // ==================== STATUS ====================

    @GetMapping("/status")
    @Operation(summary = "Status dos dados de referência sincronizados")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();

        // Geografia
        response.put("estados", estadosRepository.count());
        response.put("estadosSincronizadosFhir", estadosRepository.countByCodigoFhirIsNotNull());
        response.put("municipios", cidadesRepository.count());
        response.put("municipiosSincronizadosFhir", cidadesRepository.countByCodigoFhirIsNotNull());

        // Demográficos (são enums, sempre completos)
        response.put("racaCor", RacaCorEnum.values().length);
        response.put("sexo", SexoEnum.values().length);

        return ResponseEntity.ok(response);
    }

    // ==================== HELPERS ====================

    private Map<String, Object> buildSyncResponse(FhirSyncLog result) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", result.getMensagemErro() == null);
        response.put("recurso", result.getRecurso());
        response.put("totalEncontrados", result.getTotalEncontrados());
        response.put("novosInseridos", result.getNovosInseridos());
        response.put("atualizados", result.getAtualizados());
        if (result.getMensagemErro() != null) {
            response.put("erro", result.getMensagemErro());
        }
        return response;
    }

    private Map<String, Object> enumToMap(RacaCorEnum raca) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", raca.getCodigo());
        map.put("descricao", raca.getDescricao());
        map.put("codigoFhir", raca.getCodigoFhir());
        map.put("systemFhir", raca.getSystemFhir());
        return map;
    }

    private Map<String, Object> enumToMap(SexoEnum sexo) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", sexo.getCodigo());
        map.put("descricao", sexo.getDescricao());
        map.put("codigoFhir", sexo.getCodigoFhir());
        map.put("systemFhir", sexo.getSystemFhir());
        return map;
    }
}
