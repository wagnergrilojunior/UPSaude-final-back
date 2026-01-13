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

import com.upsaude.dto.alergia.ReacaoAdversaResponse;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.alergia.ReacaoAdversaCatalogoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/alergias/catalogo/reacoes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Catálogo de Reações Adversas", description = "Consulta do catálogo MedDRA de reações adversas")
public class ReacaoAdversaController {

    private final ReacaoAdversaCatalogoRepository reacaoRepository;

    @GetMapping
    @Operation(summary = "Listar reações adversas (paginado)")
    public ResponseEntity<Page<ReacaoAdversaResponse>> listar(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Listando reações adversas - página: {}, tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ReacaoAdversaResponse> response = reacaoRepository.findByAtivoTrue(pageable)
                .map(ReacaoAdversaResponse::fromEntity);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reação por ID")
    public ResponseEntity<ReacaoAdversaResponse> buscarPorId(@PathVariable UUID id) {
        return reacaoRepository.findById(id)
                .map(ReacaoAdversaResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Reação não encontrada"));
    }

    @GetMapping("/codigo/{codigoFhir}")
    @Operation(summary = "Buscar reação por código FHIR")
    public ResponseEntity<ReacaoAdversaResponse> buscarPorCodigoFhir(@PathVariable String codigoFhir) {
        return reacaoRepository.findByCodigoFhir(codigoFhir)
                .map(ReacaoAdversaResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Reação não encontrada"));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar reações por termo (nome ou código)")
    public ResponseEntity<Page<ReacaoAdversaResponse>> buscarPorTermo(
            @RequestParam String termo,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        log.info("Buscando reações por termo: {}", termo);

        Page<ReacaoAdversaResponse> response = reacaoRepository.buscarPorTermo(termo, pageable)
                .map(ReacaoAdversaResponse::fromEntity);

        return ResponseEntity.ok(response);
    }
}
