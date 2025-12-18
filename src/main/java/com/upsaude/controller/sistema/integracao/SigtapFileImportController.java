package com.upsaude.controller.sistema.integracao;

import com.upsaude.service.SigtapSyncService;
import com.upsaude.service.impl.SigtapFileImportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/sigtap")
@Tag(name = "SIGTAP Importação", description = "Endpoints para importação de arquivos TXT do SIGTAP")
@Slf4j
public class SigtapFileImportController {

    private final SigtapFileImportServiceImpl importService;
    private final SigtapSyncService sigtapSyncService;
    
    @Value("${sigtap.import.base-path:data_import/sigtap}")
    private String basePath;

    public SigtapFileImportController(
            SigtapFileImportServiceImpl importService,
            SigtapSyncService sigtapSyncService) {
        this.importService = importService;
        this.sigtapSyncService = sigtapSyncService;
    }

    @PostMapping("/sync")
    @Operation(
        summary = "Sincronização completa do SIGTAP",
        description = "Executa todas as etapas de sincronização na ordem correta. " +
                     "Se houver uma sincronização em andamento ou pausada, retoma de onde parou.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Sincronização executada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro durante a sincronização")
        }
    )
    public ResponseEntity<Map<String, Object>> sincronizar(
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia) {
        log.debug("REQUEST POST /api/v1/sigtap/sync - competencia: {}", competencia);
        try {
            sigtapSyncService.sincronizarTudo(competencia);
            log.info("Sincronização SIGTAP executada com sucesso. Competência: {}", competencia != null ? competencia : "padrão");
            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "mensagem", "Sincronização SIGTAP executada com sucesso",
                    "competencia", competencia != null ? competencia : "padrão"
            ));
        } catch (Exception e) {
            log.error("Erro na sincronização completa - competencia: {}", competencia, e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERRO",
                    "mensagem", "Erro durante sincronização: " + e.getMessage(),
                    "competencia", competencia != null ? competencia : "padrão"
            ));
        }
    }

    @PostMapping("/import/{competencia}")
    @Operation(
            summary = "Importar arquivos SIGTAP",
            description = "Importa todos os arquivos TXT de uma competência específica do SIGTAP",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Importação concluída"),
                    @ApiResponse(responseCode = "500", description = "Erro na importação")
            }
    )
    public ResponseEntity<Map<String, Object>> importarCompetencia(
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST POST /api/v1/sigtap/import/{}", competencia);
        try {
            SigtapFileImportServiceImpl.ImportResult result = importService.importarCompetencia(competencia);

            Map<String, Object> response = new HashMap<>();
            response.put("competencia", result.getCompetencia());
            response.put("totalLinhasProcessadas", result.getTotalLinhasProcessadas());
            response.put("totalErros", result.getTotalErros());
            response.put("linhasPorArquivo", result.getLinhasPorArquivo());
            response.put("erros", result.getErros());
            response.put("sucesso", result.getTotalErros() == 0);

            log.info("Importação da competência {} concluída. Total de linhas processadas: {}, erros: {}", 
                    competencia, result.getTotalLinhasProcessadas(), result.getTotalErros());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao importar competência {} - mensagem: {}", competencia, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());
            errorResponse.put("sucesso", false);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/import/arquivos/{competencia}")
    @Operation(
            summary = "Listar arquivos disponíveis",
            description = "Lista todos os arquivos TXT disponíveis para importação de uma competência",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de arquivos"),
                    @ApiResponse(responseCode = "404", description = "Pasta da competência não encontrada")
            }
    )
    public ResponseEntity<Map<String, Object>> listarArquivos(
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST GET /api/v1/sigtap/import/arquivos/{}", competencia);
        Path competenciaPath = Paths.get(basePath, competencia);

        if (!Files.exists(competenciaPath) || !Files.isDirectory(competenciaPath)) {
            log.warn("Pasta da competência não encontrada: {}", competenciaPath);
            Map<String, Object> response = new HashMap<>();
            response.put("erro", "Pasta da competência não encontrada: " + competenciaPath);
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> paths = Files.list(competenciaPath)) {
            Map<String, Object> response = new HashMap<>();
            response.put("competencia", competencia);
            response.put("arquivos", paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".txt"))
                    .filter(p -> !p.getFileName().toString().endsWith("_layout.txt"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList());

            log.debug("Arquivos listados com sucesso para competência: {}. Total: {}", 
                    competencia, ((java.util.List<?>) response.get("arquivos")).size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao listar arquivos da competência {} - mensagem: {}", competencia, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
