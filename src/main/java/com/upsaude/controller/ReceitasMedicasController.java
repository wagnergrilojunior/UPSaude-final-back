package com.upsaude.controller;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.service.ReceitasMedicasService;
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
 * Controlador REST para operações relacionadas a Receitas Médicas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/receitas-medicas")
@Tag(name = "Receitas Médicas", description = "API para gerenciamento de Receitas Médicas")
@RequiredArgsConstructor
public class ReceitasMedicasController {

    private final ReceitasMedicasService receitasMedicasService;

    @PostMapping
    @Operation(summary = "Criar nova receita médica", description = "Cria uma nova receita médica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita médica criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReceitasMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitasMedicasResponse> criar(@Valid @RequestBody ReceitasMedicasRequest request) {
        ReceitasMedicasResponse response = receitasMedicasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar receitas médicas", description = "Retorna uma lista paginada de receitas médicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas médicas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ReceitasMedicasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ReceitasMedicasResponse> response = receitasMedicasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter receita médica por ID", description = "Retorna uma receita médica específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita médica encontrada",
                    content = @Content(schema = @Schema(implementation = ReceitasMedicasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Receita médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitasMedicasResponse> obterPorId(
            @Parameter(description = "ID da receita médica", required = true)
            @PathVariable UUID id) {
        ReceitasMedicasResponse response = receitasMedicasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar receita médica", description = "Atualiza uma receita médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita médica atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReceitasMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Receita médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitasMedicasResponse> atualizar(
            @Parameter(description = "ID da receita médica", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ReceitasMedicasRequest request) {
        ReceitasMedicasResponse response = receitasMedicasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir receita médica", description = "Exclui (desativa) uma receita médica do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Receita médica excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da receita médica", required = true)
            @PathVariable UUID id) {
        receitasMedicasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

