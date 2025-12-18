package com.upsaude.controller.integracao;

import com.upsaude.exception.NotFoundException;
import com.upsaude.service.impl.Cid10FileImportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/cid10")
@Tag(name = "CID10 Importação", description = "Endpoints para importação de arquivos CSV do CID10")
@RequiredArgsConstructor
@Slf4j
public class Cid10FileImportController {

    private final Cid10FileImportServiceImpl importService;
    
    @Value("${cid10.import.base-path:data_import/cid10}")
    private String basePath;

    @PostMapping("/import/{competencia}")
    @Operation(
            summary = "Importar arquivos CID10",
            description = "Importa todos os arquivos CSV de uma competência específica do CID10",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Importação concluída"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro na importação")
            }
    )
    public ResponseEntity<Map<String, Object>> importarCompetencia(
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST POST /v1/cid10/import/{}", competencia);
        Cid10FileImportServiceImpl.ImportResult result = importService.importarCompetencia(competencia);

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
    }

    @GetMapping("/import/arquivos/{competencia}")
    @Operation(
            summary = "Listar arquivos disponíveis",
            description = "Lista todos os arquivos CSV disponíveis para importação de uma competência",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de arquivos"),
                    @ApiResponse(responseCode = "404", description = "Pasta da competência não encontrada")
            }
    )
    public ResponseEntity<Map<String, Object>> listarArquivos(
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST GET /v1/cid10/import/arquivos/{}", competencia);
        Path competenciaPath = Paths.get(basePath, competencia);

        if (!Files.exists(competenciaPath) || !Files.isDirectory(competenciaPath)) {
            throw new NotFoundException("Pasta da competência não encontrada: " + competenciaPath);
        }

        try (Stream<Path> paths = Files.list(competenciaPath)) {
            Map<String, Object> response = new HashMap<>();
            response.put("competencia", competencia);
            response.put("arquivos", paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".CSV"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList());

            log.debug("Arquivos listados com sucesso para competência: {}. Total: {}", 
                    competencia, ((java.util.List<?>) response.get("arquivos")).size());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Erro ao listar arquivos da competência {} - mensagem: {}", competencia, e.getMessage(), e);
            throw new NotFoundException("Erro ao acessar pasta da competência: " + competenciaPath, e);
        }
    }
}
