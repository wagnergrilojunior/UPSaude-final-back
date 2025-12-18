package com.upsaude.controller.vacina;

import com.upsaude.api.request.vacina.FabricantesVacinaRequest;
import com.upsaude.api.response.vacina.FabricantesVacinaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.vacina.FabricantesVacinaService;
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

@Slf4j
@RestController
@RequestMapping("/v1/fabricantes-vacina")
@Tag(name = "Fabricantes de Vacina", description = "API para gerenciamento de Fabricantes de Vacina")
@RequiredArgsConstructor
public class FabricantesVacinaController {

    private final FabricantesVacinaService fabricantesVacinaService;

    @PostMapping
    @Operation(summary = "Criar novo fabricante de vacina", description = "Cria um novo fabricante de vacina no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fabricante de vacina criado com sucesso",
                    content = @Content(schema = @Schema(implementation = FabricantesVacinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesVacinaResponse> criar(@Valid @RequestBody FabricantesVacinaRequest request) {
        log.debug("REQUEST POST /v1/fabricantes-vacina - payload: {}", request);
        try {
            FabricantesVacinaResponse response = fabricantesVacinaService.criar(request);
            log.info("Fabricante de vacina criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar fabricante de vacina — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar fabricante de vacina — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar fabricantes de vacina", description = "Retorna uma lista paginada de fabricantes de vacina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de fabricantes de vacina retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<FabricantesVacinaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/fabricantes-vacina - pageable: {}", pageable);
        try {
            Page<FabricantesVacinaResponse> response = fabricantesVacinaService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar fabricantes de vacina — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter fabricante de vacina por ID", description = "Retorna um fabricante de vacina específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabricante de vacina encontrado",
                    content = @Content(schema = @Schema(implementation = FabricantesVacinaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Fabricante de vacina não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesVacinaResponse> obterPorId(
            @Parameter(description = "ID do fabricante de vacina", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/fabricantes-vacina/{}", id);
        try {
            FabricantesVacinaResponse response = fabricantesVacinaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Fabricante de vacina não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter fabricante de vacina por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fabricante de vacina", description = "Atualiza um fabricante de vacina existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabricante de vacina atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = FabricantesVacinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Fabricante de vacina não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesVacinaResponse> atualizar(
            @Parameter(description = "ID do fabricante de vacina", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody FabricantesVacinaRequest request) {
        log.debug("REQUEST PUT /v1/fabricantes-vacina/{} - payload: {}", id, request);
        try {
            FabricantesVacinaResponse response = fabricantesVacinaService.atualizar(id, request);
            log.info("Fabricante de vacina atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar fabricante de vacina — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar fabricante de vacina — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir fabricante de vacina", description = "Exclui (desativa) um fabricante de vacina do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fabricante de vacina excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fabricante de vacina não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do fabricante de vacina", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/fabricantes-vacina/{}", id);
        try {
            fabricantesVacinaService.excluir(id);
            log.info("Fabricante de vacina excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir fabricante de vacina — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir fabricante de vacina — ID: {}", id, ex);
            throw ex;
        }
    }
}
