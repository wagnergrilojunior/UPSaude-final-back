package com.upsaude.controller;

import com.upsaude.api.request.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.VinculoProfissionalEquipeResponse;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.service.VinculoProfissionalEquipeService;
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
 * Controller REST para gerenciamento de vínculos entre profissionais de saúde e equipes de saúde.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/vinculos-profissional-equipe")
@Tag(name = "Vínculos Profissional-Equipe", description = "API para gerenciamento de vínculos entre profissionais de saúde e equipes de saúde")
@RequiredArgsConstructor
public class VinculoProfissionalEquipeController {

    private final VinculoProfissionalEquipeService vinculoService;

    @PostMapping
    @Operation(summary = "Criar novo vínculo profissional-equipe", description = "Cria um novo vínculo entre um profissional de saúde e uma equipe de saúde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = VinculoProfissionalEquipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculoProfissionalEquipeResponse> criar(@Valid @RequestBody VinculoProfissionalEquipeRequest request) {
        VinculoProfissionalEquipeResponse response = vinculoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os vínculos profissional-equipe", description = "Retorna uma lista paginada de todos os vínculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listar(Pageable pageable) {
        Page<VinculoProfissionalEquipeResponse> response = vinculoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vínculo por ID", description = "Retorna um vínculo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo encontrado",
                    content = @Content(schema = @Schema(implementation = VinculoProfissionalEquipeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculoProfissionalEquipeResponse> obterPorId(@PathVariable UUID id) {
        VinculoProfissionalEquipeResponse response = vinculoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar vínculos por profissional", description = "Retorna uma lista paginada de vínculos de um profissional específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional", required = true) @PathVariable UUID profissionalId,
            Pageable pageable) {
        Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipe/{equipeId}")
    @Operation(summary = "Listar vínculos por equipe", description = "Retorna uma lista paginada de vínculos de uma equipe específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID da equipe inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorEquipe(
            @Parameter(description = "ID da equipe", required = true) @PathVariable UUID equipeId,
            Pageable pageable) {
        Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorEquipe(equipeId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo-vinculo/{tipoVinculo}/equipe/{equipeId}")
    @Operation(summary = "Listar vínculos por tipo de vínculo e equipe", description = "Retorna uma lista paginada de vínculos filtrados por tipo e equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Tipo de vínculo ou ID da equipe inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorTipoVinculo(
            @Parameter(description = "Tipo de vínculo (EFETIVO, CONTRATO, TEMPORARIO, etc.)", required = true) @PathVariable TipoVinculoProfissionalEnum tipoVinculo,
            @Parameter(description = "ID da equipe", required = true) @PathVariable UUID equipeId,
            Pageable pageable) {
        Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorTipoVinculo(tipoVinculo, equipeId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}/equipe/{equipeId}")
    @Operation(summary = "Listar vínculos por status e equipe", description = "Retorna uma lista paginada de vínculos filtrados por status e equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status ou ID da equipe inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorStatus(
            @Parameter(description = "Status do vínculo (ATIVO, SUSPENSO, INATIVO)", required = true) @PathVariable StatusAtivoEnum status,
            @Parameter(description = "ID da equipe", required = true) @PathVariable UUID equipeId,
            Pageable pageable) {
        Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorStatus(status, equipeId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vínculo profissional-equipe", description = "Atualiza um vínculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = VinculoProfissionalEquipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculoProfissionalEquipeResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody VinculoProfissionalEquipeRequest request) {
        VinculoProfissionalEquipeResponse response = vinculoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vínculo profissional-equipe", description = "Exclui (desativa) um vínculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        vinculoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

