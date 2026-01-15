package com.upsaude.service.impl.api.sia;

import com.upsaude.service.api.sia.SiaPaAnomaliaDetectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "sia.anomaly.detection", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SiaPaAnomaliaDetectionJob {

    private final JdbcTemplate jdbcTemplate;
    private final SiaPaAnomaliaDetectionService service;

    @Scheduled(cron = "${sia.anomaly.detection.cron:0 0 3 * * ?}")
    public void scheduledDetect() {
        try {
            // Estratégia: processar a última competência disponível por UF.
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("""
                    SELECT uf, MAX(competencia) AS competencia
                    FROM public.sia_pa
                    GROUP BY uf
                    """);

            if (rows == null || rows.isEmpty()) {
                log.info("SIA-PA: nenhum dado encontrado para detecção automática de anomalias");
                return;
            }

            for (Map<String, Object> row : rows) {
                String uf = row.get("uf") != null ? String.valueOf(row.get("uf")) : null;
                String competencia = row.get("competencia") != null ? String.valueOf(row.get("competencia")) : null;
                if (uf == null || uf.isBlank() || competencia == null || competencia.isBlank()) {
                    continue;
                }
                int n = service.detectar(competencia, uf);
                log.info("SIA-PA: detecção automática concluída. uf={}, competencia={}, inseridas={}", uf, competencia, n);
            }
        } catch (Exception e) {
            log.warn("Falha na detecção automática de anomalias SIA-PA: {}", e.getMessage(), e);
        }
    }
}

