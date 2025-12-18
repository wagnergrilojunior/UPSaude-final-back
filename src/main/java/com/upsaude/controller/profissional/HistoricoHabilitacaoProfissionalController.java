package com.upsaude.controller.profissional;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.service.profissional.HistoricoHabilitacaoProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/historico-habilitacao-profissional")
@Tag(name = "Histórico Habilitação Profissional", description = "API para gerenciamento do histórico de habilitação do profissional")
@RequiredArgsConstructor
@Slf4j
public class HistoricoHabilitacaoProfissionalController {

    private final HistoricoHabilitacaoProfissionalService service;

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cria um registro no histórico de habilitação do profissional")
    public ResponseEntity<HistoricoHabilitacaoProfissionalResponse> criar(@Valid @RequestBody HistoricoHabilitacaoProfissionalRequest request) {
        log.debug("REQUEST POST /v1/historico-habilitacao-profissional - payload: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar registros", description = "Lista registros do histórico de habilitação do profissional (paginado)")
    public ResponseEntity<Page<HistoricoHabilitacaoProfissionalResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable) {
        log.debug("REQUEST GET /v1/historico-habilitacao-profissional - pageable: {}", pageable);
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro por ID", description = "Retorna um registro do histórico pelo ID")
    public ResponseEntity<HistoricoHabilitacaoProfissionalResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/historico-habilitacao-profissional/{}", id);
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro do histórico")
    public ResponseEntity<HistoricoHabilitacaoProfissionalResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody HistoricoHabilitacaoProfissionalRequest request) {
        log.debug("REQUEST PUT /v1/historico-habilitacao-profissional/{} - payload: {}", id, request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro", description = "Exclui (desativa) um registro")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/historico-habilitacao-profissional/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
