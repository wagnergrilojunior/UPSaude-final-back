package com.upsaude.controller.api.vacinacao;

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

import com.upsaude.dto.vacinacao.VacinaResponse;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.vacinacao.ImunobiologicoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/vacinacao/vacinas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Vacinas", description = "Consulta de vacinas (imunobiológicos)")
public class VacinaController {

    private final ImunobiologicoRepository imunobiologicoRepository;

    @GetMapping
    @Operation(summary = "Listar todas as vacinas (paginado)")
    public ResponseEntity<Page<VacinaResponse>> listarTodas(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Listando todas as vacinas - página: {}, tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<VacinaResponse> vacinas = imunobiologicoRepository.findAll(pageable)
                .map(VacinaResponse::fromEntity);

        return ResponseEntity.ok(vacinas);
    }

    @GetMapping("/ativas")
    @Operation(summary = "Listar vacinas ativas (paginado)")
    public ResponseEntity<Page<VacinaResponse>> listarAtivas(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Listando vacinas ativas - página: {}, tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<VacinaResponse> vacinas = imunobiologicoRepository.findByAtivoTrue(pageable)
                .map(VacinaResponse::fromEntity);

        return ResponseEntity.ok(vacinas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vacina por ID")
    public ResponseEntity<VacinaResponse> buscarPorId(@PathVariable UUID id) {
        log.info("Buscando vacina por ID: {}", id);

        return imunobiologicoRepository.findById(id)
                .map(VacinaResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Vacina não encontrada"));
    }

    @GetMapping("/codigo/{codigoFhir}")
    @Operation(summary = "Buscar vacina por código FHIR")
    public ResponseEntity<VacinaResponse> buscarPorCodigoFhir(@PathVariable String codigoFhir) {
        log.info("Buscando vacina por código FHIR: {}", codigoFhir);

        return imunobiologicoRepository.findByCodigoFhir(codigoFhir)
                .map(VacinaResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Vacina não encontrada"));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar vacinas por termo (nome ou código)")
    public ResponseEntity<Page<VacinaResponse>> buscarPorTermo(
            @RequestParam String termo,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Buscando vacinas por termo: {}", termo);

        Page<VacinaResponse> vacinas = imunobiologicoRepository.buscarPorTermoPaginado(termo, pageable)
                .map(VacinaResponse::fromEntity);

        return ResponseEntity.ok(vacinas);
    }
}
