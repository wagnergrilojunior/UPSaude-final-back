package com.upsaude.controller.estabelecimento;

import com.upsaude.api.request.estabelecimento.ServicosEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ServicosEstabelecimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.estabelecimento.ServicosEstabelecimentoService;
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
@RequestMapping("/v1/servicos-estabelecimento")
@Tag(name = "Serviços do Estabelecimento", description = "API para gerenciamento de serviços do estabelecimento")
@RequiredArgsConstructor
@Slf4j
public class ServicosEstabelecimentoController {

    private final ServicosEstabelecimentoService service;

    @PostMapping
    @Operation(summary = "Criar serviço do estabelecimento", description = "Cria um novo serviço do estabelecimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso",
            content = @Content(schema = @Schema(implementation = ServicosEstabelecimentoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ServicosEstabelecimentoResponse> criar(@Valid @RequestBody ServicosEstabelecimentoRequest request) {
        log.debug("REQUEST POST /v1/servicos-estabelecimento - payload: {}", request);
        try {
            ServicosEstabelecimentoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar serviço do estabelecimento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar serviços do estabelecimento", description = "Retorna uma lista paginada de serviços do estabelecimento com filtros")
    public ResponseEntity<Page<ServicosEstabelecimentoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String codigoCnes,
        @RequestParam(required = false) Boolean apenasAtivos
    ) {
        log.debug("REQUEST GET /v1/servicos-estabelecimento - pageable: {}, estabelecimentoId: {}, nome: {}, codigoCnes: {}, apenasAtivos: {}",
            pageable, estabelecimentoId, nome, codigoCnes, apenasAtivos);
        return ResponseEntity.ok(service.listar(pageable, estabelecimentoId, nome, codigoCnes, apenasAtivos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter serviço do estabelecimento por ID", description = "Retorna um serviço do estabelecimento pelo ID")
    public ResponseEntity<ServicosEstabelecimentoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/servicos-estabelecimento/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Serviço do estabelecimento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar serviço do estabelecimento", description = "Atualiza um serviço do estabelecimento existente")
    public ResponseEntity<ServicosEstabelecimentoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ServicosEstabelecimentoRequest request) {
        log.debug("REQUEST PUT /v1/servicos-estabelecimento/{} - payload: {}", id, request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir serviço do estabelecimento", description = "Exclui (desativa) um serviço do estabelecimento")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/servicos-estabelecimento/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

