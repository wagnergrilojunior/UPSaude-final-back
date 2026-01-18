package com.upsaude.service.impl.maintenance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Serviço de manutenção de banco de dados.
 * Monitora dead tuples e performance de queries.
 * 
 * Para desabilitar: spring.database.maintenance.enabled=false
 */
@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.database.maintenance.enabled", havingValue = "true", matchIfMissing = true)
public class DatabaseMaintenanceService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    
    /**
     * Monitora tabelas com alto volume de dead tuples.
     * Executa todo dia às 2h da manhã.
     */
    @Scheduled(cron = "${spring.database.maintenance.monitor-cron:0 0 2 * * *}")
    public void monitorarDeadTuples() {
        log.info("Iniciando monitoramento de dead tuples...");
        
        String query = """
            SELECT 
                schemaname,
                relname,
                n_dead_tup,
                n_live_tup,
                ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) AS dead_ratio_pct,
                pg_size_pretty(pg_total_relation_size(schemaname||'.'||relname)) AS size,
                last_autovacuum,
                autovacuum_count
            FROM pg_stat_user_tables
            WHERE n_dead_tup > 1000 OR 
                  (n_dead_tup > 0 AND n_dead_tup::float / NULLIF(n_live_tup, 0) > 0.2)
            ORDER BY n_dead_tup DESC
            LIMIT 20;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            if (results.isEmpty()) {
                log.info("✓ Nenhuma tabela com dead tuples alto detectada.");
                return;
            }
            
            log.warn("⚠ Tabelas com dead tuples altos detectadas:");
            results.forEach(row -> {
                String schema = (String) row[0];
                String tableName = (String) row[1];
                Object deadTuples = row[2];
                Object liveTuples = row[3];
                BigDecimal ratio = (BigDecimal) row[4];
                String size = (String) row[5];
                Object lastVacuum = row[6];
                Object vacuumCount = row[7];
                
                log.warn("  - {}.{}: {} dead tuples ({}%), {} registros vivos, tamanho: {}, último vacuum: {}, count: {}",
                    schema, tableName, deadTuples, ratio, liveTuples, size, lastVacuum, vacuumCount);
            });
            
            // Verificar tabelas críticas especificamente
            verificarTabelasCriticas();
            
        } catch (Exception e) {
            log.error("Erro ao monitorar dead tuples", e);
        }
    }
    
    /**
     * Monitora queries lentas (>10 segundos).
     * Executa a cada hora.
     */
    @Scheduled(cron = "${spring.database.maintenance.monitor-slow-queries-cron:0 0 * * * *}")
    public void monitorarQueriesLentas() {
        log.debug("Monitorando queries lentas...");
        
        String query = """
            SELECT 
                pid,
                now() - pg_stat_activity.query_start AS duration,
                usename,
                application_name,
                state,
                wait_event_type,
                wait_event,
                LEFT(query, 100) as query_preview
            FROM pg_stat_activity
            WHERE state != 'idle'
              AND query NOT ILIKE '%pg_stat_activity%'
              AND pid != pg_backend_pid()
              AND (now() - pg_stat_activity.query_start) > interval '10 seconds'
            ORDER BY duration DESC
            LIMIT 10;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            if (!results.isEmpty()) {
                log.warn("⚠ Queries lentas detectadas (>10s):");
                results.forEach(row -> {
                    Integer pid = (Integer) row[0];
                    Object duration = row[1];
                    String username = (String) row[2];
                    String appName = (String) row[3];
                    String state = (String) row[4];
                    String waitType = (String) row[5];
                    String waitEvent = (String) row[6];
                    String queryPreview = (String) row[7];
                    
                    log.warn("  - PID {}: {} - {} - {} - Wait: {}/{} - Query: {}",
                        pid, duration, username, state, waitType, waitEvent, queryPreview);
                });
            }
        } catch (Exception e) {
            log.error("Erro ao monitorar queries lentas", e);
        }
    }
    
    /**
     * Verifica tabelas críticas específicas que historicamente tiveram problemas.
     */
    private void verificarTabelasCriticas() {
        String[] tabelasCriticas = {"sia_pa", "estados", "competencia_financeira"};
        
        String query = """
            SELECT 
                relname,
                n_dead_tup,
                n_live_tup,
                ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) AS dead_ratio_pct,
                last_autovacuum
            FROM pg_stat_user_tables
            WHERE relname = ?
        """;
        
        for (String tabela : tabelasCriticas) {
            try {
                @SuppressWarnings("unchecked")
                List<Object[]> results = entityManager.createNativeQuery(query)
                    .setParameter(1, tabela)
                    .getResultList();
                
                if (!results.isEmpty()) {
                    Object[] row = results.get(0);
                    Object deadTuples = row[1];
                    Object liveTuples = row[2];
                    BigDecimal ratio = (BigDecimal) row[3];
                    Object lastVacuum = row[4];
                    
                    if (ratio != null && ratio.doubleValue() > 20.0) {
                        log.error("❌ CRÍTICO: Tabela {} com {}% de dead tuples! Dead: {}, Live: {}, Último VACUUM: {}",
                            tabela, ratio, deadTuples, liveTuples, lastVacuum);
                    } else if (ratio != null && ratio.doubleValue() > 10.0) {
                        log.warn("⚠ ATENÇÃO: Tabela {} com {}% de dead tuples. Dead: {}, Live: {}, Último VACUUM: {}",
                            tabela, ratio, deadTuples, liveTuples, lastVacuum);
                    }
                }
            } catch (Exception e) {
                log.error("Erro ao verificar tabela crítica: " + tabela, e);
            }
        }
    }
    
    /**
     * Fornece estatísticas gerais do banco de dados.
     * Executa uma vez por dia às 6h.
     */
    @Scheduled(cron = "${spring.database.maintenance.stats-cron:0 0 6 * * *}")
    public void estatisticasBancoDados() {
        log.info("Gerando estatísticas do banco de dados...");
        
        String query = """
            SELECT 
                COUNT(*) as total_tables,
                pg_size_pretty(SUM(pg_total_relation_size(schemaname||'.'||relname))) as total_size,
                SUM(n_live_tup) as total_live_tuples,
                SUM(n_dead_tup) as total_dead_tuples,
                ROUND(100.0 * SUM(n_dead_tup) / NULLIF(SUM(n_live_tup) + SUM(n_dead_tup), 0), 2) as avg_dead_ratio
            FROM pg_stat_user_tables;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            if (!results.isEmpty()) {
                Object[] row = results.get(0);
                log.info("📊 Estatísticas Gerais do Banco:");
                log.info("  - Total de tabelas: {}", row[0]);
                log.info("  - Tamanho total: {}", row[1]);
                log.info("  - Total registros vivos: {}", row[2]);
                log.info("  - Total dead tuples: {}", row[3]);
                log.info("  - Média dead ratio: {}%", row[4]);
            }
        } catch (Exception e) {
            log.error("Erro ao gerar estatísticas do banco", e);
        }
    }
}
