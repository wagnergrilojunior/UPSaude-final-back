package com.upsaude.controller.api.anexo;

import com.upsaude.api.request.anexo.AnexoUpdateRequest;
import com.upsaude.api.response.anexo.AnexoDownloadUrlResponse;
import com.upsaude.api.response.anexo.AnexoGestaoResponse;
import com.upsaude.api.response.anexo.AnexoResponse;
import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.anexo.AnexoService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/anexos")
@Tag(name = "Anexos", description = "API para gerenciamento de anexos (arquivos) vinculados a qualquer entidade do sistema")
@RequiredArgsConstructor
@Slf4j
public class AnexoController {

    private final AnexoService anexoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload de anexo",
            description = "Faz upload de um arquivo e vincula a uma entidade do sistema (paciente, agendamento, atendimento, consulta, etc)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anexo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AnexoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Recurso alvo não encontrado")
    })
    public ResponseEntity<AnexoResponse> upload(
            @Parameter(description = "Arquivo a ser anexado", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Tipo do recurso alvo (PACIENTE, AGENDAMENTO, ATENDIMENTO, CONSULTA, etc)", required = true)
            @RequestParam("targetType") TargetTypeAnexoEnum targetType,
            @Parameter(description = "ID do recurso alvo (UUID)", required = true)
            @RequestParam("targetId") UUID targetId,
            @Parameter(description = "Categoria do anexo (LAUDO, EXAME, DOCUMENTO, IMAGEM, etc)")
            @RequestParam(value = "categoria", required = false) CategoriaAnexoEnum categoria,
            @Parameter(description = "Indica se o anexo é visível para o paciente")
            @RequestParam(value = "visivelParaPaciente", required = false, defaultValue = "false") Boolean visivelParaPaciente,
            @Parameter(description = "Descrição opcional do anexo")
            @RequestParam(value = "descricao", required = false) String descricao,
            @Parameter(description = "Tags separadas por vírgula")
            @RequestParam(value = "tags", required = false) String tags) {
        log.debug("REQUEST POST /v1/anexos - targetType: {}, targetId: {}, fileName: {}", 
                  targetType, targetId, file != null ? file.getOriginalFilename() : null);
        try {
            AnexoResponse response = anexoService.upload(file, targetType, targetId, categoria, 
                                                         visivelParaPaciente, descricao, tags);
            log.info("Anexo criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao criar anexo — mensagem: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar anexo", ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar anexos",
            description = "Retorna uma lista paginada de anexos filtrados por targetType e targetId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de anexos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AnexoResponse>> listar(
            @Parameter(description = "Tipo do recurso alvo", required = true)
            @RequestParam("targetType") TargetTypeAnexoEnum targetType,
            @Parameter(description = "ID do recurso alvo", required = true)
            @RequestParam("targetId") UUID targetId,
            @Parameter(description = "Filtrar por categoria")
            @RequestParam(value = "categoria", required = false) CategoriaAnexoEnum categoria,
            @Parameter(description = "Filtrar por status")
            @RequestParam(value = "status", required = false) StatusAnexoEnum status,
            @Parameter(description = "Filtrar apenas anexos visíveis para paciente")
            @RequestParam(value = "visivelParaPaciente", required = false) Boolean visivelParaPaciente,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/anexos - targetType: {}, targetId: {}, categoria: {}, status: {}", 
                  targetType, targetId, categoria, status);
        try {
            Page<AnexoResponse> response = anexoService.listar(targetType, targetId, categoria, 
                                                                 status, visivelParaPaciente, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar anexos", ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter anexo por ID",
            description = "Retorna um anexo específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anexo encontrado",
                    content = @Content(schema = @Schema(implementation = AnexoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AnexoResponse> obterPorId(
            @Parameter(description = "ID do anexo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/anexos/{}", id);
        try {
            AnexoResponse response = anexoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Anexo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter anexo por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}/download")
    @Operation(
            summary = "Download direto do anexo",
            description = "Faz download direto do arquivo via streaming"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Download iniciado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<org.springframework.core.io.Resource> download(
            @Parameter(description = "ID do anexo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/anexos/{}/download", id);
        try {
            // Obter metadados do anexo primeiro
            AnexoResponse anexo = anexoService.obterPorId(id);
            
            // Fazer download do stream
            InputStream inputStream = anexoService.downloadStream(id);

            org.springframework.core.io.InputStreamResource resource = 
                    new org.springframework.core.io.InputStreamResource(inputStream);

            HttpHeaders headers = new HttpHeaders();
            if (anexo.getMimeType() != null) {
                headers.setContentType(MediaType.parseMediaType(anexo.getMimeType()));
            }
            if (anexo.getFileNameOriginal() != null) {
                headers.setContentDispositionFormData("attachment", anexo.getFileNameOriginal());
            }
            if (anexo.getSizeBytes() != null) {
                headers.setContentLength(anexo.getSizeBytes());
            }

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (NotFoundException ex) {
            log.warn("Anexo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao fazer download — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}/thumbnail")
    @Operation(
            summary = "Obter miniatura do anexo (se for imagem)",
            description = "Retorna URL assinada da miniatura do anexo (apenas para imagens)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL da miniatura gerada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
            @ApiResponse(responseCode = "400", description = "Anexo não é uma imagem"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, String>> obterThumbnail(
            @Parameter(description = "ID do anexo", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Largura da miniatura em pixels (padrão: 200)")
            @RequestParam(value = "width", required = false, defaultValue = "200") int width,
            @Parameter(description = "Altura da miniatura em pixels (padrão: 200)")
            @RequestParam(value = "height", required = false, defaultValue = "200") int height) {
        log.debug("REQUEST GET /v1/anexos/{}/thumbnail - width: {}, height: {}", id, width, height);
        try {
            String thumbnailUrl = anexoService.gerarThumbnailUrl(id, width, height);
            Map<String, String> response = new java.util.HashMap<>();
            response.put("thumbnailUrl", thumbnailUrl);
            response.put("width", String.valueOf(width));
            response.put("height", String.valueOf(height));
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Anexo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (BadRequestException ex) {
            log.warn("Erro ao gerar thumbnail — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao gerar thumbnail — ID: {}", id, ex);
            throw ex;
        }
    }

    @PostMapping("/{id}/download-url")
    @Operation(
            summary = "Gerar URL assinada para download",
            description = "Gera uma URL assinada temporária para download do anexo do Supabase Storage"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL assinada gerada com sucesso",
                    content = @Content(schema = @Schema(implementation = AnexoDownloadUrlResponse.class))),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AnexoDownloadUrlResponse> gerarUrlDownload(
            @Parameter(description = "ID do anexo", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Tempo de expiração da URL em segundos (padrão: 300 segundos = 5 minutos)")
            @RequestParam(value = "expiresIn", required = false, defaultValue = "300") int expiresIn) {
        log.debug("REQUEST POST /v1/anexos/{}/download-url - expiresIn: {}s", id, expiresIn);
        try {
            AnexoDownloadUrlResponse response = anexoService.gerarUrlDownload(id, expiresIn);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Anexo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao gerar URL de download — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/gestao")
    @Operation(
            summary = "Listagem completa de anexos para gestão/admin",
            description = "Retorna uma lista paginada completa de anexos com todos os filtros e informações detalhadas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de anexos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AnexoGestaoResponse>> listarGestao(
            @Parameter(description = "Tipo do recurso alvo")
            @RequestParam(value = "targetType", required = false) TargetTypeAnexoEnum targetType,
            @Parameter(description = "ID do recurso alvo")
            @RequestParam(value = "targetId", required = false) UUID targetId,
            @Parameter(description = "Filtrar por categoria")
            @RequestParam(value = "categoria", required = false) CategoriaAnexoEnum categoria,
            @Parameter(description = "Filtrar por status")
            @RequestParam(value = "status", required = false) StatusAnexoEnum status,
            @Parameter(description = "Filtrar apenas anexos visíveis para paciente")
            @RequestParam(value = "visivelParaPaciente", required = false) Boolean visivelParaPaciente,
            @Parameter(description = "Buscar por nome do arquivo (busca parcial)")
            @RequestParam(value = "fileName", required = false) String fileName,
            @Parameter(description = "Filtrar por ID do usuário que criou")
            @RequestParam(value = "criadoPor", required = false) UUID criadoPor,
            @Parameter(description = "Data de início para filtrar por data de upload")
            @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @Parameter(description = "Data de fim para filtrar por data de upload")
            @RequestParam(value = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/anexos/gestao - targetType: {}, targetId: {}, categoria: {}, status: {}, fileName: {}, criadoPor: {}", 
                  targetType, targetId, categoria, status, fileName, criadoPor);
        try {
            Page<AnexoGestaoResponse> response = anexoService.listarGestao(
                    targetType, targetId, categoria, status, visivelParaPaciente,
                    fileName, criadoPor, dataInicio, dataFim, pageable
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar anexos para gestão", ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar metadados do anexo",
            description = "Atualiza metadados do anexo (categoria, visibilidade, descrição, tags, status)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anexo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AnexoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AnexoResponse> atualizar(
            @Parameter(description = "ID do anexo", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AnexoUpdateRequest request) {
        log.debug("REQUEST PATCH /v1/anexos/{} - payload: {}", id, request);
        try {
            AnexoResponse response = anexoService.atualizar(id, request);
            log.info("Anexo atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar anexo — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar anexo — ID: {}", id, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir anexo",
            description = "Exclui um anexo (soft delete). Opcionalmente pode deletar o arquivo do storage também"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anexo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anexo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do anexo", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Se true, deleta o arquivo do storage também (padrão: false)")
            @RequestParam(value = "deleteFromStorage", required = false, defaultValue = "false") boolean deleteFromStorage) {
        log.debug("REQUEST DELETE /v1/anexos/{} - deleteFromStorage: {}", id, deleteFromStorage);
        try {
            anexoService.excluir(id, deleteFromStorage);
            log.info("Anexo excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Anexo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir anexo — ID: {}", id, ex);
            throw ex;
        }
    }
}
