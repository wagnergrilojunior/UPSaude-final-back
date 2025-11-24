package com.upsaude.controller;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.service.EnderecoService;
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
 * Controlador REST para operações relacionadas a Endereços.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/enderecos")
@Tag(name = "Endereços", description = "API para gerenciamento de Endereços")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    @Operation(summary = "Criar novo endereço", description = "Cria um novo endereço no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EnderecoResponse> criar(@Valid @RequestBody EnderecoRequest request) {
        EnderecoResponse response = enderecoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar endereços", description = "Retorna uma lista paginada de endereços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EnderecoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<EnderecoResponse> response = enderecoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter endereço por ID", description = "Retorna um endereço específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EnderecoResponse> obterPorId(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id) {
        EnderecoResponse response = enderecoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EnderecoResponse> atualizar(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EnderecoRequest request) {
        EnderecoResponse response = enderecoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir endereço", description = "Exclui (desativa) um endereço do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id) {
        enderecoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

