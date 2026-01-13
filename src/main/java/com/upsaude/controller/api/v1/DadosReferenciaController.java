package com.upsaude.controller.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/dados-referencia")
@RequiredArgsConstructor
@Tag(name = "Dados de Referência", description = "Dados demográficos e geográficos")
public class DadosReferenciaController {

    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    // ==================== GEOGRAFIA ====================

    @GetMapping("/geografia/estados")
    @Operation(summary = "Listar estados sincronizados")
    public ResponseEntity<List<Estados>> listarEstados() {
        return ResponseEntity.ok(estadosRepository.findAll(Sort.by("nome")));
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
    public ResponseEntity<Page<Cidades>> listarMunicipios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(cidadesRepository.findAll(
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/geografia/municipios/{codigoIbge}")
    @Operation(summary = "Buscar município por código IBGE")
    public ResponseEntity<Cidades> buscarMunicipioPorCodigoIbge(@PathVariable String codigoIbge) {
        return cidadesRepository.findByCodigoIbge(codigoIbge)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/geografia/municipios/buscar")
    @Operation(summary = "Buscar municípios por nome")
    public ResponseEntity<List<Cidades>> buscarMunicipiosPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(cidadesRepository.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos dados de referência sincronizados")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("estados", estadosRepository.count());
        response.put("municipios", cidadesRepository.count());
        return ResponseEntity.ok(response);
    }
}
