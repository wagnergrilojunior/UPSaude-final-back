package com.upsaude.controller;

import com.upsaude.api.request.MedicacoesContinuasRequest;
import com.upsaude.api.response.MedicacoesContinuasResponse;
import com.upsaude.service.MedicacoesContinuasService;
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
 * Controlador REST para operações relacionadas a Medicações Contínuas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/medicacoes-continuas")
@Tag(name = "Medicações Contínuas", description = "API para gerenciamento de Medicações Contínuas")
@RequiredArgsConstructor
public class MedicacoesContinuasController {

    private final MedicacoesContinuasService medicacoesContinuasService;

    @PostMapping
    @Operation(summary = "Criar nova medicação contínua", description = "Cria uma nova medicação contínua no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicação contínua criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasResponse> criar(@Valid @RequestBody MedicacoesContinuasRequest request) {
        MedicacoesContinuasResponse response = medicacoesContinuasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar medicações contínuas", description = "Retorna uma lista paginada de medicações contínuas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicações contínuas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacoesContinuasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<MedicacoesContinuasResponse> response = medicacoesContinuasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicação contínua por ID", description = "Retorna uma medicação contínua específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicação contínua não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasResponse> obterPorId(
            @Parameter(description = "ID da medicação contínua", required = true)
            @PathVariable UUID id) {
        MedicacoesContinuasResponse response = medicacoesContinuasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação contínua", description = "Atualiza uma medicação contínua existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasResponse> atualizar(
            @Parameter(description = "ID da medicação contínua", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacoesContinuasRequest request) {
        MedicacoesContinuasResponse response = medicacoesContinuasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicação contínua", description = "Exclui (desativa) uma medicação contínua do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicação contínua excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medicação contínua", required = true)
            @PathVariable UUID id) {
        medicacoesContinuasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

