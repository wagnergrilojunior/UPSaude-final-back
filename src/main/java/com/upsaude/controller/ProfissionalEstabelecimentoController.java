package com.upsaude.controller;

import com.upsaude.api.request.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.ProfissionalEstabelecimentoResponse;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.service.ProfissionalEstabelecimentoService;
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
 * Controlador REST para operações relacionadas a Vínculos de Profissionais com Estabelecimentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/profissionais-estabelecimentos")
@Tag(name = "Vínculos Profissional-Estabelecimento", description = "API para gerenciamento de vínculos entre profissionais e estabelecimentos")
@RequiredArgsConstructor
public class ProfissionalEstabelecimentoController {

    private final ProfissionalEstabelecimentoService profissionalEstabelecimentoService;

    @PostMapping
    @Operation(summary = "Criar novo vínculo", description = "Cria um novo vínculo entre um profissional e um estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProfissionalEstabelecimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionalEstabelecimentoResponse> criar(@Valid @RequestBody ProfissionalEstabelecimentoRequest request) {
        ProfissionalEstabelecimentoResponse response = profissionalEstabelecimentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar vínculos", description = "Retorna uma lista paginada de vínculos entre profissionais e estabelecimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar vínculos por profissional", description = "Retorna uma lista paginada de vínculos de um profissional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listarPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar vínculos por estabelecimento", description = "Retorna uma lista paginada de vínculos de um estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listarPorEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}/tipo-vinculo/{tipoVinculo}")
    @Operation(summary = "Listar vínculos por tipo e estabelecimento", description = "Retorna uma lista paginada de vínculos filtrados por tipo e estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listarPorTipoVinculo(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Tipo de vínculo (EFETIVO, CONTRATO, TEMPORARIO, etc.)", required = true)
            @PathVariable TipoVinculoProfissionalEnum tipoVinculo,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listarPorTipoVinculo(tipoVinculo, estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vínculo por ID", description = "Retorna um vínculo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo encontrado",
                    content = @Content(schema = @Schema(implementation = ProfissionalEstabelecimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionalEstabelecimentoResponse> obterPorId(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id) {
        ProfissionalEstabelecimentoResponse response = profissionalEstabelecimentoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vínculo", description = "Atualiza um vínculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProfissionalEstabelecimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionalEstabelecimentoResponse> atualizar(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProfissionalEstabelecimentoRequest request) {
        ProfissionalEstabelecimentoResponse response = profissionalEstabelecimentoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vínculo", description = "Exclui (desativa) um vínculo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id) {
        profissionalEstabelecimentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

