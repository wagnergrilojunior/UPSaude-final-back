package com.upsaude.controller.api.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.entity.alergia.Alergeno;
import com.upsaude.entity.alergia.CategoriaAgenteAlergia;
import com.upsaude.entity.alergia.CriticidadeAlergia;
import com.upsaude.entity.alergia.ReacaoAdversaCatalogo;
import com.upsaude.repository.alergia.AlergenoRepository;
import com.upsaude.repository.alergia.CategoriaAgenteAlergiaRepository;
import com.upsaude.repository.alergia.CriticidadeAlergiaRepository;
import com.upsaude.repository.alergia.ReacaoAdversaCatalogoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/alergia")
@RequiredArgsConstructor
@Tag(name = "Alergias", description = "Gestão de alergias e reações adversas")
public class AlergiaController {

    private final AlergenoRepository alergenoRepository;
    private final ReacaoAdversaCatalogoRepository reacaoAdversaRepository;
    private final CriticidadeAlergiaRepository criticidadeRepository;
    private final CategoriaAgenteAlergiaRepository categoriaAgenteRepository;

    @GetMapping("/alergenos")
    @Operation(summary = "Listar alérgenos sincronizados")
    public ResponseEntity<Page<Alergeno>> listarAlergenos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(alergenoRepository.findByAtivoTrue(
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/alergenos/{id}")
    @Operation(summary = "Buscar alérgeno por ID")
    public ResponseEntity<Alergeno> buscarAlergenoPorId(@PathVariable UUID id) {
        return alergenoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/alergenos/buscar")
    @Operation(summary = "Buscar alérgenos por termo (nome ou código)")
    public ResponseEntity<Page<Alergeno>> buscarAlergenos(
            @RequestParam String termo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(alergenoRepository.buscarPorTermo(termo,
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/reacoes-adversas")
    @Operation(summary = "Listar reações adversas sincronizadas")
    public ResponseEntity<Page<ReacaoAdversaCatalogo>> listarReacoesAdversas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(reacaoAdversaRepository.findByAtivoTrue(
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/reacoes-adversas/{id}")
    @Operation(summary = "Buscar reação adversa por ID")
    public ResponseEntity<ReacaoAdversaCatalogo> buscarReacaoPorId(@PathVariable UUID id) {
        return reacaoAdversaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/criticidade")
    @Operation(summary = "Listar níveis de criticidade sincronizados")
    public ResponseEntity<Iterable<CriticidadeAlergia>> listarCriticidade() {
        return ResponseEntity.ok(criticidadeRepository.findAll());
    }

    @GetMapping("/categorias-agente")
    @Operation(summary = "Listar categorias de agente sincronizadas")
    public ResponseEntity<Iterable<CategoriaAgenteAlergia>> listarCategoriasAgente() {
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
}
