package com.upsaude.controller.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioExportRequest;
import com.upsaude.service.api.sistema.relatorios.RelatorioExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/relatorios/export")
@RequiredArgsConstructor
@Tag(name = "Exportação de Relatórios", description = "Exportação de relatórios em diferentes formatos (PDF, Excel, CSV)")
public class RelatorioExportController {

    private final RelatorioExportService relatorioExportService;

    @PostMapping
    @Operation(summary = "Exporta um relatório no formato especificado")
    public ResponseEntity<Resource> exportarRelatorio(
            @Parameter(description = "Dados do relatório a ser exportado", required = true)
            @Valid @RequestBody RelatorioExportRequest request
    ) {
        log.debug("REQUEST POST /v1/relatorios/export - Tipo: {}, Formato: {}", 
                request.getTipoRelatorio(), request.getFormatoExportacao());

        Resource resource = relatorioExportService.exportarRelatorio(request);
        String contentType = getContentType(request.getFormatoExportacao());
        String filename = gerarNomeArquivo(request);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    private String getContentType(RelatorioExportRequest.FormatoExportacao formato) {
        return switch (formato) {
            case PDF -> "application/pdf";
            case EXCEL -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case CSV -> "text/csv";
        };
    }

    private String gerarNomeArquivo(RelatorioExportRequest request) {
        String timestamp = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String extensao = switch (request.getFormatoExportacao()) {
            case PDF -> ".pdf";
            case EXCEL -> ".xlsx";
            case CSV -> ".csv";
        };
        return String.format("%s_%s%s", 
                request.getTipoRelatorio().name().toLowerCase(),
                timestamp,
                extensao);
    }
}
