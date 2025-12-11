package com.upsaude.controller;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.TratamentosOdontologicosService;
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
@RequestMapping("/v1/tratamentos-odontologicos")
@Tag(name = "Tratamentos Odontológicos", description = "API para gerenciamento de Tratamentos Odontológicos")
@RequiredArgsConstructor
@Slf4j
public class TratamentosOdontologicosController {

    private final TratamentosOdontologicosService tratamentosOdontologicosService;

    @PostMapping
    @Operation(summary = "Criar novo tratamento odontológico", description = "Cria um novo tratamento odontológico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tratamento odontológico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosOdontologicosResponse> criar(@Valid @RequestBody TratamentosOdontologicosRequest request) {
        log.debug("REQUEST POST /v1/tratamentos-odontologicos - payload: {}", request);
        try {
            TratamentosOdontologicosResponse response = tratamentosOdontologicosService.criar(request);
            log.info("Tratamento odontológico criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar tratamento odontológico — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar tratamento odontológico — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar tratamentos odontológicos", description = "Retorna uma lista paginada de tratamentos odontológicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tratamentos odontológicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TratamentosOdontologicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/tratamentos-odontologicos - pageable: {}", pageable);
        try {
            Page<TratamentosOdontologicosResponse> response = tratamentosOdontologicosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar tratamentos odontológicos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tratamento odontológico por ID", description = "Retorna um tratamento odontológico específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento odontológico encontrado",
                    content = @Content(schema = @Schema(implementation = TratamentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Tratamento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosOdontologicosResponse> obterPorId(
            @Parameter(description = "ID do tratamento odontológico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/tratamentos-odontologicos/{}", id);
        try {
            TratamentosOdontologicosResponse response = tratamentosOdontologicosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Tratamento odontológico não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter tratamento odontológico por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tratamento odontológico", description = "Atualiza um tratamento odontológico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento odontológico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Tratamento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosOdontologicosResponse> atualizar(
            @Parameter(description = "ID do tratamento odontológico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TratamentosOdontologicosRequest request) {
        log.debug("REQUEST PUT /v1/tratamentos-odontologicos/{} - payload: {}", id, request);
        try {
            TratamentosOdontologicosResponse response = tratamentosOdontologicosService.atualizar(id, request);
            log.info("Tratamento odontológico atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar tratamento odontológico — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar tratamento odontológico — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tratamento odontológico", description = "Exclui (desativa) um tratamento odontológico do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tratamento odontológico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tratamento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do tratamento odontológico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/tratamentos-odontologicos/{}", id);
        try {
            tratamentosOdontologicosService.excluir(id);
            log.info("Tratamento odontológico excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Tratamento odontológico não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir tratamento odontológico — ID: {}", id, ex);
            throw ex;
        }
    }
}
