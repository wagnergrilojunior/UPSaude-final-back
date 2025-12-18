package com.upsaude.controller.equipe;

import com.upsaude.api.request.equipe.EscalaTrabalhoRequest;
import com.upsaude.api.response.equipe.EscalaTrabalhoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.equipe.EscalaTrabalhoService;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/v1/escala-trabalho")
@Tag(name = "Escala de Trabalho", description = "API para gerenciamento de escala de trabalho")
@RequiredArgsConstructor
@Slf4j
public class EscalaTrabalhoController {

    private final EscalaTrabalhoService service;

    @PostMapping
    @Operation(summary = "Criar escala de trabalho", description = "Cria uma nova escala de trabalho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Escala criada com sucesso",
            content = @Content(schema = @Schema(implementation = EscalaTrabalhoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EscalaTrabalhoResponse> criar(@Valid @RequestBody EscalaTrabalhoRequest request) {
        log.debug("REQUEST POST /v1/escala-trabalho - payload: {}", request);
        try {
            EscalaTrabalhoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar escala de trabalho — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar escalas de trabalho", description = "Retorna uma lista paginada de escalas de trabalho")
    public ResponseEntity<Page<EscalaTrabalhoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID medicoId,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) DayOfWeek diaSemana,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vigentesEm,
        @RequestParam(required = false) Boolean apenasAtivos
    ) {
        log.debug("REQUEST GET /v1/escala-trabalho - pageable: {}, profissionalId: {}, medicoId: {}, estabelecimentoId: {}, diaSemana: {}, vigentesEm: {}, apenasAtivos: {}",
            pageable, profissionalId, medicoId, estabelecimentoId, diaSemana, vigentesEm, apenasAtivos);
        Page<EscalaTrabalhoResponse> response = service.listar(pageable, profissionalId, medicoId, estabelecimentoId, diaSemana, vigentesEm, apenasAtivos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter escala de trabalho por ID", description = "Retorna uma escala de trabalho pelo ID")
    public ResponseEntity<EscalaTrabalhoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/escala-trabalho/{}", id);
        try {
            EscalaTrabalhoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Escala de trabalho não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar escala de trabalho", description = "Atualiza uma escala de trabalho existente")
    public ResponseEntity<EscalaTrabalhoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody EscalaTrabalhoRequest request) {
        log.debug("REQUEST PUT /v1/escala-trabalho/{} - payload: {}", id, request);
        EscalaTrabalhoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir escala de trabalho", description = "Exclui (desativa) uma escala de trabalho")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/escala-trabalho/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
