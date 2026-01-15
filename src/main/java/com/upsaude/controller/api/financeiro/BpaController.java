package com.upsaude.controller.api.financeiro;

import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.BpaExportService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/financeiro/bpa")
@Tag(name = "Financeiro - BPA", description = "API para geração e exportação de arquivos BPA")
@RequiredArgsConstructor
@Slf4j
public class BpaController {

    private final BpaExportService bpaExportService;
    private final TenantService tenantService;

    @GetMapping("/competencias/{competenciaId}/download")
    @Operation(
            summary = "Download do arquivo BPA gerado",
            description = "Faz o download do arquivo BPA em formato TXT de largura fixa para uma competência fechada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo BPA retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "BPA não foi gerado para esta competência"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @SuppressWarnings("null")
    public ResponseEntity<Resource> downloadBpa(
            @Parameter(description = "ID da competência financeira", required = true)
            @PathVariable UUID competenciaId
    ) {
        log.debug("REQUEST GET /api/v1/financeiro/bpa/competencias/{}/download", competenciaId);

        UUID tenantId = tenantService.validarTenantAtual();

        try {
            Resource resource = bpaExportService.exportarBpa(competenciaId, tenantId);

            String filename = "BPA-" + competenciaId + ".txt";

            @SuppressWarnings("null")
            MediaType contentType = MediaType.TEXT_PLAIN;
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(contentType)
                    .body(resource);
        } catch (NotFoundException ex) {
            log.warn("BPA não encontrado para competência {} e tenant {}", competenciaId, tenantId);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao exportar BPA — competenciaId: {}", competenciaId, ex);
            throw ex;
        }
    }

    @GetMapping("/competencias/{competenciaId}/status")
    @Operation(
            summary = "Status do BPA",
            description = "Verifica se o BPA foi gerado para uma competência."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, Object>> statusBpa(
            @Parameter(description = "ID da competência financeira", required = true)
            @PathVariable UUID competenciaId
    ) {
        log.debug("REQUEST GET /api/v1/financeiro/bpa/competencias/{}/status", competenciaId);

        UUID tenantId = tenantService.validarTenantAtual();

        boolean foiGerado = bpaExportService.bpaFoiGerado(competenciaId, tenantId);

        Map<String, Object> response = new HashMap<>();
        response.put("competenciaId", competenciaId);
        response.put("bpaGerado", foiGerado);
        response.put("mensagem", foiGerado
                ? "BPA foi gerado e está disponível para download"
                : "BPA ainda não foi gerado. É necessário fechar a competência primeiro.");

        return ResponseEntity.ok(response);
    }
}
