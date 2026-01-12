package com.upsaude.controller.api.vacinacao;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.upsaude.dto.vacinacao.LoteVacinaRequest;
import com.upsaude.dto.vacinacao.LoteVacinaResponse;
import com.upsaude.service.vacinacao.LoteVacinaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/vacinacao/lotes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Lotes de Vacina", description = "Gerenciamento de lotes de vacina")
public class LoteVacinaController {

    private final LoteVacinaService loteService;

    @PostMapping
    @Operation(summary = "Criar novo lote de vacina")
    public ResponseEntity<LoteVacinaResponse> criar(@Valid @RequestBody LoteVacinaRequest request) {
        log.info("Criando novo lote de vacina: {}", request.getNumeroLote());
        LoteVacinaResponse response = loteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os lotes de vacina")
    public ResponseEntity<List<LoteVacinaResponse>> listarTodos() {
        log.info("Listando todos os lotes de vacina");
        return ResponseEntity.ok(loteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lote de vacina por ID")
    public ResponseEntity<LoteVacinaResponse> buscarPorId(@PathVariable UUID id) {
        log.info("Buscando lote de vacina: {}", id);
        return ResponseEntity.ok(loteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lote de vacina")
    public ResponseEntity<LoteVacinaResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody LoteVacinaRequest request) {
        log.info("Atualizando lote de vacina: {}", id);
        return ResponseEntity.ok(loteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir lote de vacina (soft delete)")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.info("Excluindo lote de vacina: {}", id);
        loteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar lotes disponíveis (não vencidos e com estoque)")
    public ResponseEntity<List<LoteVacinaResponse>> listarDisponiveis() {
        log.info("Listando lotes disponíveis");
        return ResponseEntity.ok(loteService.listarDisponiveis());
    }

    @GetMapping("/imunobiologico/{imunobiologicoId}")
    @Operation(summary = "Listar lotes por imunobiológico")
    public ResponseEntity<List<LoteVacinaResponse>> listarPorImunobiologico(
            @PathVariable UUID imunobiologicoId) {
        log.info("Listando lotes por imunobiológico: {}", imunobiologicoId);
        return ResponseEntity.ok(loteService.listarPorImunobiologico(imunobiologicoId));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar lotes por estabelecimento")
    public ResponseEntity<List<LoteVacinaResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId) {
        log.info("Listando lotes por estabelecimento: {}", estabelecimentoId);
        return ResponseEntity.ok(loteService.listarPorEstabelecimento(estabelecimentoId));
    }
}
