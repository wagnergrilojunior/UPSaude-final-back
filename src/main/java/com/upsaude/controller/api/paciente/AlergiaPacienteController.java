package com.upsaude.controller.api.paciente;

import com.upsaude.api.request.AlergiaPacienteRequest;
import com.upsaude.api.response.AlergiaPacienteResponse;
import com.upsaude.service.api.paciente.AlergiaPacienteService;
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
@RequestMapping("/v1/pacientes/{pacienteId}/alergias")
@Tag(name = "Alergias do Paciente", description = "API para gerenciamento de alergias de pacientes")
@RequiredArgsConstructor
@Slf4j
public class AlergiaPacienteController {

    private final AlergiaPacienteService alergiaPacienteService;

    @PostMapping
    @Operation(
            summary = "Criar alergia para paciente",
            description = "Cria uma nova alergia associada a um paciente. Alergia é informação clínica declarada do paciente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Alergia criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiaPacienteResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Alergia duplicada - já existe alergia ativa com mesma substância e tipo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiaPacienteResponse> criar(
            @Parameter(description = "ID do paciente", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID pacienteId,
            @Valid @RequestBody AlergiaPacienteRequest request) {
        log.debug("REQUEST POST /v1/pacientes/{}/alergias - tipo: {}, substancia: [oculto]", 
                pacienteId, request.getTipo());
        try {
            AlergiaPacienteResponse response = alergiaPacienteService.criar(pacienteId, request);
            log.info("Alergia criada com sucesso. ID: {}, Paciente ID: {}", response.getId(), pacienteId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            log.error("Erro ao criar alergia para paciente {} - Exception: {}", pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar alergias do paciente",
            description = "Retorna uma lista paginada de alergias ativas do paciente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de alergias retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiaPacienteResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AlergiaPacienteResponse>> listar(
            @Parameter(description = "ID do paciente", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pacientes/{}/alergias - pageable: {}", pacienteId, pageable);
        try {
            Page<AlergiaPacienteResponse> response = alergiaPacienteService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao listar alergias do paciente {} - Exception: {}", pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{alergiaId}")
    @Operation(
            summary = "Obter alergia específica do paciente",
            description = "Retorna uma alergia específica do paciente pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alergia encontrada",
                    content = @Content(schema = @Schema(implementation = AlergiaPacienteResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Paciente ou alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiaPacienteResponse> obterPorId(
            @Parameter(description = "ID do paciente", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID pacienteId,
            @Parameter(description = "ID da alergia", required = true, example = "660e8400-e29b-41d4-a716-446655440001")
            @PathVariable UUID alergiaId) {
        log.debug("REQUEST GET /v1/pacientes/{}/alergias/{}", pacienteId, alergiaId);
        try {
            AlergiaPacienteResponse response = alergiaPacienteService.obterPorId(pacienteId, alergiaId);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao obter alergia {} do paciente {} - Exception: {}", alergiaId, pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{alergiaId}")
    @Operation(
            summary = "Atualizar alergia do paciente",
            description = "Atualiza uma alergia existente do paciente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alergia atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiaPacienteResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente ou alergia não encontrada"),
            @ApiResponse(responseCode = "409", description = "Alergia duplicada - já existe alergia ativa com mesma substância e tipo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiaPacienteResponse> atualizar(
            @Parameter(description = "ID do paciente", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID pacienteId,
            @Parameter(description = "ID da alergia", required = true, example = "660e8400-e29b-41d4-a716-446655440001")
            @PathVariable UUID alergiaId,
            @Valid @RequestBody AlergiaPacienteRequest request) {
        log.debug("REQUEST PUT /v1/pacientes/{}/alergias/{} - tipo: {}", pacienteId, alergiaId, request.getTipo());
        try {
            AlergiaPacienteResponse response = alergiaPacienteService.atualizar(pacienteId, alergiaId, request);
            log.info("Alergia atualizada com sucesso. ID: {}, Paciente ID: {}", alergiaId, pacienteId);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao atualizar alergia {} do paciente {} - Exception: {}", alergiaId, pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{alergiaId}")
    @Operation(
            summary = "Excluir alergia do paciente",
            description = "Exclui logicamente uma alergia do paciente (marca como inativa)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente ou alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do paciente", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID pacienteId,
            @Parameter(description = "ID da alergia", required = true, example = "660e8400-e29b-41d4-a716-446655440001")
            @PathVariable UUID alergiaId) {
        log.debug("REQUEST DELETE /v1/pacientes/{}/alergias/{}", pacienteId, alergiaId);
        try {
            alergiaPacienteService.excluir(pacienteId, alergiaId);
            log.info("Alergia excluída com sucesso. ID: {}, Paciente ID: {}", alergiaId, pacienteId);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Erro ao excluir alergia {} do paciente {} - Exception: {}", alergiaId, pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}

