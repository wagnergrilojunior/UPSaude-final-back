package com.upsaude.controller.api.profissional;

import com.upsaude.api.request.profissional.AdicionarEspecialidadeRequest;
import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.profissional.EspecialidadeResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.profissional.MedicosService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/medicos")
@Tag(name = "Médicos", description = "API para gerenciamento de Médicos")
@RequiredArgsConstructor
@Slf4j
public class MedicosController {

    private final MedicosService medicosService;

    @PostMapping
    @Operation(summary = "Criar novo médico", description = "Cria um novo médico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicosResponse> criar(@Valid @RequestBody MedicosRequest request) {
        log.debug("REQUEST POST /v1/medicos - payload: {}", request);
        try {
            MedicosResponse response = medicosService.criar(request);
            log.info("Médico criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar médico — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar médico — Path: /v1/medicos, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar médicos", description = "Retorna uma lista paginada de médicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicos - pageable: {}", pageable);
        try {
            Page<MedicosResponse> response = medicosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar médicos — Path: /v1/medicos, Method: GET, pageable: {}, Exception: {}",
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter médico por ID", description = "Retorna um médico específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado",
                    content = @Content(schema = @Schema(implementation = MedicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicosResponse> obterPorId(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicos/{}", id);
        try {
            MedicosResponse response = medicosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Médico não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter médico por ID — Path: /v1/medicos/{}, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar médico", description = "Atualiza um médico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicosResponse> atualizar(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicosRequest request) {
        log.debug("REQUEST PUT /v1/medicos/{} - payload: {}", id, request);
        try {
            MedicosResponse response = medicosService.atualizar(id, request);
            log.info("Médico atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar médico — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar médico — Path: /v1/medicos/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir médico", description = "Remove permanentemente um médico do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicos/{}", id);
        try {
            medicosService.excluir(id);
            log.info("Médico excluído permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Médico não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir médico — Path: /v1/medicos/{}, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar médico", description = "Inativa um médico no sistema alterando seu status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "400", description = "Médico já está inativo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/medicos/{}/inativar", id);
        try {
            medicosService.inativar(id);
            log.info("Médico inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Médico não encontrado para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar médico — Path: /v1/medicos/{}/inativar, Method: PATCH, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}/especialidades")
    @Operation(summary = "Listar especialidades do médico", description = "Retorna a lista de especialidades (CBO) associadas a um médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<EspecialidadeResponse>> listarEspecialidades(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicos/{}/especialidades", id);
        try {
            List<EspecialidadeResponse> response = medicosService.listarEspecialidades(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Médico não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar especialidades — Path: /v1/medicos/{}/especialidades, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PostMapping("/{id}/especialidades")
    @Operation(summary = "Adicionar especialidade ao médico", description = "Adiciona uma especialidade (CBO) a um médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialidade adicionada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Médico ou CBO não encontrado"),
            @ApiResponse(responseCode = "409", description = "Especialidade já está associada ao médico"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadeResponse> adicionarEspecialidade(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AdicionarEspecialidadeRequest request) {
        log.debug("REQUEST POST /v1/medicos/{}/especialidades - payload: {}", id, request);
        try {
            EspecialidadeResponse response = medicosService.adicionarEspecialidade(id, request.getCodigoCbo());
            log.info("Especialidade adicionada ao médico com sucesso. Médico ID: {}, CBO: {}", id, request.getCodigoCbo());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao adicionar especialidade — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao adicionar especialidade — Path: /v1/medicos/{}/especialidades, Method: POST, ID: {}, payload: {}, Exception: {}",
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}/especialidades/{codigoCbo}")
    @Operation(summary = "Remover especialidade do médico", description = "Remove uma especialidade (CBO) de um médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Especialidade removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico ou especialidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> removerEspecialidade(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Código CBO da especialidade", required = true, example = "225110")
            @PathVariable String codigoCbo) {
        log.debug("REQUEST DELETE /v1/medicos/{}/especialidades/{}", id, codigoCbo);
        try {
            medicosService.removerEspecialidade(id, codigoCbo);
            log.info("Especialidade removida do médico com sucesso. Médico ID: {}, CBO: {}", id, codigoCbo);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Médico ou especialidade não encontrada — ID: {}, CBO: {}, mensagem: {}", id, codigoCbo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao remover especialidade — Path: /v1/medicos/{}/especialidades/{}, Method: DELETE, ID: {}, CBO: {}, Exception: {}",
                id, codigoCbo, id, codigoCbo, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
