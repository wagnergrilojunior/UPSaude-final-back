package com.upsaude.controller.api.sia;

import com.upsaude.api.response.sia.anomalia.SiaPaAnomaliaResponse;
import com.upsaude.service.api.sia.SiaPaAnomaliaDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/sia/anomalias")
@RequiredArgsConstructor
@Tag(name = "SIA-SUS Anomalias", description = "Detecção e consulta de anomalias de produção (SIA-PA)")
public class SiaPaAnomaliaController {

    private final SiaPaAnomaliaDetectionService service;

    @GetMapping
    @Operation(summary = "Listar anomalias por competência/UF (paginado)")
    public ResponseEntity<Page<SiaPaAnomaliaResponse>> listar(
            @Parameter(description = "Competência AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras)", required = true)
            @RequestParam("uf") String uf,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(competencia, uf, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de uma anomalia")
    public ResponseEntity<SiaPaAnomaliaResponse> obterPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PostMapping("/detectar")
    @Operation(summary = "Executar detecção manual de anomalias para competência/UF")
    public ResponseEntity<Map<String, Object>> detectar(
            @Parameter(description = "Competência AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras)", required = true)
            @RequestParam("uf") String uf
    ) {
        int inseridas = service.detectar(competencia, uf);
        return ResponseEntity.ok(Map.of(
                "competencia", competencia,
                "uf", uf,
                "inseridas", inseridas
        ));
    }
}

