package com.upsaude.controller.api.sia;

import com.upsaude.service.api.sia.SiaMaterializedViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/sia/materialized-views")
@RequiredArgsConstructor
@Tag(name = "SIA Materialized Views", description = "Gerenciamento de views materializadas do SIA para otimização de performance")
public class SiaMaterializedViewController {

    private final SiaMaterializedViewService siaMaterializedViewService;

    @PostMapping("/refresh/all")
    @Operation(summary = "Atualiza todas as views materializadas do SIA")
    public ResponseEntity<Map<String, String>> refreshAllViews() {
        log.info("REQUEST POST /v1/sia/materialized-views/refresh/all");
        siaMaterializedViewService.refreshAllViews();
        return ResponseEntity.ok(Map.of("message", "Todas as views materializadas foram atualizadas com sucesso"));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Atualiza uma view materializada específica do SIA")
    public ResponseEntity<Map<String, String>> refreshView(
            @Parameter(description = "Nome da view materializada", required = true)
            @RequestParam("viewName") String viewName
    ) {
        log.info("REQUEST POST /v1/sia/materialized-views/refresh?viewName={}", viewName);
        siaMaterializedViewService.refreshView(viewName);
        return ResponseEntity.ok(Map.of("message", "View materializada " + viewName + " foi atualizada com sucesso"));
    }
}
