package com.upsaude.controller.prontuario;

import com.upsaude.api.request.prontuario.HistoricoClinicoRequest;
import com.upsaude.api.response.prontuario.HistoricoClinicoResponse;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.prontuario.HistoricoClinicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/v1/historico-clinico")
@Tag(name = "Histórico Clínico", description = "API para gerenciamento de Histórico Clínico")
@RequiredArgsConstructor
@Slf4j
public class HistoricoClinicoController {

    private final HistoricoClinicoService service;

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cria um registro no histórico clínico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro criado com sucesso",
            content = @Content(schema = @Schema(implementation = HistoricoClinicoResponse.class)))
    })
    public ResponseEntity<HistoricoClinicoResponse> criar(@Valid @RequestBody HistoricoClinicoRequest request) {
        log.debug("REQUEST POST /v1/historico-clinico - payload: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar registros", description = "Lista registros do histórico clínico (paginado)")
    public ResponseEntity<Page<HistoricoClinicoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable) {
        log.debug("REQUEST GET /v1/historico-clinico - pageable: {}", pageable);
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro por ID", description = "Retorna um registro do histórico clínico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = HistoricoClinicoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<HistoricoClinicoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/historico-clinico/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Registro não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro do histórico clínico")
    public ResponseEntity<HistoricoClinicoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody HistoricoClinicoRequest request) {
        log.debug("REQUEST PUT /v1/historico-clinico/{} - payload: {}", id, request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro", description = "Exclui (desativa) um registro do histórico clínico")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/historico-clinico/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
