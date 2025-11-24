package com.upsaude.controller;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.api.response.FabricantesVacinaResponse;
import com.upsaude.service.FabricantesVacinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Fabricantes de Vacina.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/fabricantes-vacina")
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
        FabricantesVacinaResponse response = fabricantesVacinaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<FabricantesVacinaResponse> response = fabricantesVacinaService.listar(pageable);
        return ResponseEntity.ok(response);
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
        FabricantesVacinaResponse response = fabricantesVacinaService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        FabricantesVacinaResponse response = fabricantesVacinaService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        fabricantesVacinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

