package com.upsaude.controller;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.service.MedicacaoService;
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
 * Controlador REST para operações relacionadas a Medicações.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/medicacoes")
@Tag(name = "Medicações", description = "API para gerenciamento de Medicações")
@RequiredArgsConstructor
public class MedicacaoController {

    private final MedicacaoService medicacaoService;

    @PostMapping
    @Operation(summary = "Criar nova medicação", description = "Cria uma nova medicação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoResponse> criar(@Valid @RequestBody MedicacaoRequest request) {
        MedicacaoResponse response = medicacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar medicações", description = "Retorna uma lista paginada de medicações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicações retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacaoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<MedicacaoResponse> response = medicacaoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicação por ID", description = "Retorna uma medicação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoResponse> obterPorId(
            @Parameter(description = "ID da medicação", required = true)
            @PathVariable UUID id) {
        MedicacaoResponse response = medicacaoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação", description = "Atualiza uma medicação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoResponse> atualizar(
            @Parameter(description = "ID da medicação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacaoRequest request) {
        MedicacaoResponse response = medicacaoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicação", description = "Exclui (desativa) uma medicação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medicação", required = true)
            @PathVariable UUID id) {
        medicacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

