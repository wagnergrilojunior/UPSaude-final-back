package com.upsaude.controller.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.InfraestruturaEstabelecimentoResponse;
import com.upsaude.service.api.estabelecimento.InfraestruturaEstabelecimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/v1/infraestrutura-estabelecimento")
@Tag(name = "Infraestrutura do Estabelecimento", description = "API para gerenciamento de infraestrutura do estabelecimento")
@RequiredArgsConstructor
@Slf4j
public class InfraestruturaEstabelecimentoController {

    private final InfraestruturaEstabelecimentoService service;

    @PostMapping
    @Operation(summary = "Criar", description = "Cria um registro de infraestrutura do estabelecimento")
    public ResponseEntity<InfraestruturaEstabelecimentoResponse> criar(@Valid @RequestBody InfraestruturaEstabelecimentoRequest request) {
        log.debug("REQUEST POST /v1/infraestrutura-estabelecimento - payload: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar", description = "Lista registros de infraestrutura do estabelecimento (paginado)")
    public ResponseEntity<Page<InfraestruturaEstabelecimentoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable) {
        log.debug("REQUEST GET /v1/infraestrutura-estabelecimento - pageable: {}", pageable);
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna um registro de infraestrutura pelo ID")
    public ResponseEntity<InfraestruturaEstabelecimentoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/infraestrutura-estabelecimento/{}", id);
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar", description = "Atualiza um registro de infraestrutura")
    public ResponseEntity<InfraestruturaEstabelecimentoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody InfraestruturaEstabelecimentoRequest request) {
        log.debug("REQUEST PUT /v1/infraestrutura-estabelecimento/{} - payload: {}", id, request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui (desativa) um registro de infraestrutura")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/infraestrutura-estabelecimento/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
