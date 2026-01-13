package com.upsaude.controller.api.alergia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.dto.alergia.AlergenoResponse;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.alergia.AlergenoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/alergias/catalogo/alergenos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Catálogo de Alérgenos", description = "Consulta do catálogo nacional de alérgenos (CBARA)")
public class AlergenoController {

    private final AlergenoRepository alergenoRepository;

    @GetMapping
    @Operation(summary = "Listar alérgenos (paginado)")
    public ResponseEntity<Page<AlergenoResponse>> listar(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Listando alérgenos - página: {}, tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<AlergenoResponse> response = alergenoRepository.findByAtivoTrue(pageable)
                .map(AlergenoResponse::fromEntity);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alérgeno por ID")
    public ResponseEntity<AlergenoResponse> buscarPorId(@PathVariable UUID id) {
        return alergenoRepository.findById(id)
                .map(AlergenoResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Alérgeno não encontrado"));
    }

    @GetMapping("/codigo/{codigoFhir}")
    @Operation(summary = "Buscar alérgeno por código FHIR")
    public ResponseEntity<AlergenoResponse> buscarPorCodigoFhir(@PathVariable String codigoFhir) {
        return alergenoRepository.findByCodigoFhir(codigoFhir)
                .map(AlergenoResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Alérgeno não encontrado"));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar alérgenos por termo (nome ou código)")
    public ResponseEntity<Page<AlergenoResponse>> buscarPorTermo(
            @RequestParam String termo,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Buscando alérgenos por termo: {}", termo);

        Page<AlergenoResponse> response = alergenoRepository.buscarPorTermo(termo, pageable)
                .map(AlergenoResponse::fromEntity);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar alérgenos por categoria")
    public ResponseEntity<Page<AlergenoResponse>> listarPorCategoria(
            @PathVariable String categoria,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        Page<AlergenoResponse> response = alergenoRepository.findByAtivoTrue(pageable)
                .map(AlergenoResponse::fromEntity); // Note: Should filter by category if needed

        // Actually implementing the category filter properly
        Page<AlergenoResponse> filtered = alergenoRepository.findByCategoriaAndAtivoTrue(categoria, pageable)
                .map(AlergenoResponse::fromEntity);

        return ResponseEntity.ok(filtered);
    }
}
