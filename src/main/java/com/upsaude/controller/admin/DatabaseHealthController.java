package com.upsaude.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/v1/admin/database")
@RequiredArgsConstructor
@Tag(name = "Database Health", description = "Monitoramento e gerenciamento da saúde do banco de dados")

public class DatabaseHealthController {

    @PersistenceContext
    private final EntityManager entityManager;

    
    @GetMapping("/queries")
    @Operation(summary = "Listar queries ativas", description = "Lista todas as queries em execução, incluindo represadas")
    public ResponseEntity<Map<String, Object>> listarQueries(
            @Parameter(description = "Filtrar apenas queries lentas (em minutos)")
            @RequestParam(required = false, defaultValue = "1") Integer minutosMinimoLenta) {
        
        log.info("Listando queries ativas no banco (mínimo {} minutos)", minutosMinimoLenta);
        
        String query = """
            SELECT 
                pid,
                now() - pg_stat_activity.query_start AS duration,
                usename,
                application_name,
                client_addr,
                state,
                wait_event_type,
                wait_event,
                query_start,
                state_change,
                query
            FROM pg_stat_activity
            WHERE state != 'idle'
              AND query NOT ILIKE '%pg_stat_activity%'
              AND pid != pg_backend_pid()
              AND (now() - pg_stat_activity.query_start) > interval '? minutes'
            ORDER BY duration DESC;
        """.replace("?", String.valueOf(minutosMinimoLenta));
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            List<Map<String, Object>> queriesRepresadas = new ArrayList<>();
            
            for (Object[] row : results) {
                Map<String, Object> queryInfo = new HashMap<>();
                queryInfo.put("pid", row[0]);
                queryInfo.put("duracao", row[1] != null ? row[1].toString() : "0");
                queryInfo.put("usuario", row[2]);
                queryInfo.put("aplicacao", row[3]);
                queryInfo.put("ip_cliente", row[4]);
                queryInfo.put("estado", row[5]);
                queryInfo.put("wait_event_type", row[6]);
                queryInfo.put("wait_event", row[7]);
                queryInfo.put("inicio", row[8]);
                queryInfo.put("ultima_mudanca_estado", row[9]);
                queryInfo.put("query", row[10]);
                
                queriesRepresadas.add(queryInfo);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("total", queriesRepresadas.size());
            response.put("filtro_minutos", minutosMinimoLenta);
            response.put("queries", queriesRepresadas);
            response.put("timestamp", new Date());
            
            log.info("Encontradas {} queries represadas (>{} min)", queriesRepresadas.size(), minutosMinimoLenta);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao listar queries", e);
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao consultar banco de dados");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @DeleteMapping("/queries/{pid}")
    @Operation(summary = "Matar query específica", description = "Encerra uma query pelo PID do processo")
    public ResponseEntity<Map<String, Object>> matarQuery(
            @Parameter(description = "PID do processo a ser encerrado")
            @PathVariable Integer pid) {
        
        log.warn("Tentando matar query com PID: {}", pid);
        
        String query = "SELECT pg_terminate_backend(?)";
        
        try {
            Object result = entityManager.createNativeQuery(query)
                    .setParameter(1, pid)
                    .getSingleResult();
            
            boolean sucesso = Boolean.TRUE.equals(result);
            
            Map<String, Object> response = new HashMap<>();
            response.put("pid", pid);
            response.put("sucesso", sucesso);
            response.put("mensagem", sucesso ? "Query encerrada com sucesso" : "Falha ao encerrar query (PID pode não existir)");
            response.put("timestamp", new Date());
            
            if (sucesso) {
                log.info("Query PID {} encerrada com sucesso", pid);
            } else {
                log.warn("Falha ao encerrar query PID {}", pid);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao matar query PID {}", pid, e);
            Map<String, Object> error = new HashMap<>();
            error.put("pid", pid);
            error.put("sucesso", false);
            error.put("erro", "Erro ao encerrar query");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @PostMapping("/queries/kill-all")
    @Operation(summary = "Matar todas queries represadas", description = "Encerra todas queries idle in transaction >5min ou ativas >30min")
    public ResponseEntity<Map<String, Object>> matarTodasQueriesRepresadas() {
        
        log.warn("Tentando matar TODAS as queries represadas");
        
        String query = """
            SELECT 
                pg_terminate_backend(pid) as terminated,
                pid,
                now() - pg_stat_activity.query_start AS duration,
                state,
                LEFT(query, 100) as query_preview
            FROM pg_stat_activity
            WHERE 
                pid != pg_backend_pid()
                AND (
                    (state = 'idle in transaction' AND now() - state_change > interval '5 minutes')
                    OR 
                    (state != 'idle' AND query NOT ILIKE '%pg_stat_activity%' AND now() - query_start > interval '30 minutes')
                );
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            List<Map<String, Object>> queriesEncerradas = new ArrayList<>();
            int sucessos = 0;
            int falhas = 0;
            
            for (Object[] row : results) {
                Map<String, Object> queryInfo = new HashMap<>();
                boolean terminated = Boolean.TRUE.equals(row[0]);
                
                queryInfo.put("pid", row[1]);
                queryInfo.put("duracao", row[2] != null ? row[2].toString() : "0");
                queryInfo.put("estado", row[3]);
                queryInfo.put("query_preview", row[4]);
                queryInfo.put("encerrada", terminated);
                
                if (terminated) {
                    sucessos++;
                } else {
                    falhas++;
                }
                
                queriesEncerradas.add(queryInfo);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("total_encontradas", results.size());
            response.put("sucessos", sucessos);
            response.put("falhas", falhas);
            response.put("queries_encerradas", queriesEncerradas);
            response.put("timestamp", new Date());
            
            log.info("Encerradas {} queries com sucesso, {} falhas", sucessos, falhas);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao matar queries em massa", e);
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao encerrar queries");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @GetMapping("/health/dead-tuples")
    @Operation(summary = "Dead tuples por tabela", description = "Lista tabelas com dead tuples e percentual")
    public ResponseEntity<Map<String, Object>> deadTuples(
            @Parameter(description = "Percentual mínimo de dead tuples para listar")
            @RequestParam(required = false, defaultValue = "10") Integer percentualMinimo) {
        
        log.info("Consultando dead tuples (mínimo {}%)", percentualMinimo);
        
        String query = """
            SELECT 
                schemaname,
                relname,
                n_dead_tup,
                n_live_tup,
                ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) AS dead_ratio_pct,
                pg_size_pretty(pg_total_relation_size(schemaname||'.'||relname)) AS size,
                last_vacuum,
                last_autovacuum,
                autovacuum_count
            FROM pg_stat_user_tables
            WHERE n_dead_tup > 0 
              AND ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) >= ?
            ORDER BY dead_ratio_pct DESC NULLS LAST, n_dead_tup DESC
            LIMIT 50;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query)
                    .setParameter(1, percentualMinimo)
                    .getResultList();
            
            List<Map<String, Object>> tabelas = new ArrayList<>();
            
            for (Object[] row : results) {
                Map<String, Object> tabelaInfo = new HashMap<>();
                tabelaInfo.put("schema", row[0]);
                tabelaInfo.put("tabela", row[1]);
                tabelaInfo.put("dead_tuples", row[2]);
                tabelaInfo.put("live_tuples", row[3]);
                tabelaInfo.put("dead_ratio_pct", row[4]);
                tabelaInfo.put("tamanho", row[5]);
                tabelaInfo.put("ultimo_vacuum", row[6]);
                tabelaInfo.put("ultimo_autovacuum", row[7]);
                tabelaInfo.put("autovacuum_count", row[8]);
                
                tabelas.add(tabelaInfo);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("total", tabelas.size());
            response.put("filtro_percentual_minimo", percentualMinimo);
            response.put("tabelas", tabelas);
            response.put("timestamp", new Date());
            
            log.info("Encontradas {} tabelas com dead tuples >{}%", tabelas.size(), percentualMinimo);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao consultar dead tuples", e);
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao consultar banco de dados");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @GetMapping("/health/stats")
    @Operation(summary = "Estatísticas gerais", description = "Retorna estatísticas gerais do banco de dados")
    public ResponseEntity<Map<String, Object>> estatisticasGerais() {
        
        log.info("Consultando estatísticas gerais do banco");
        
        String query = """
            SELECT 
                COUNT(*) as total_tables,
                pg_size_pretty(SUM(pg_total_relation_size(schemaname||'.'||relname))) as total_size,
                SUM(n_live_tup) as total_live_tuples,
                SUM(n_dead_tup) as total_dead_tuples,
                ROUND(100.0 * SUM(n_dead_tup) / NULLIF(SUM(n_live_tup) + SUM(n_dead_tup), 0), 2) as avg_dead_ratio,
                SUM(n_tup_ins) as total_inserts,
                SUM(n_tup_upd) as total_updates,
                SUM(n_tup_del) as total_deletes
            FROM pg_stat_user_tables;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            Map<String, Object> stats = new HashMap<>();
            
            if (!results.isEmpty()) {
                Object[] row = results.get(0);
                stats.put("total_tabelas", row[0]);
                stats.put("tamanho_total", row[1]);
                stats.put("total_registros_vivos", row[2]);
                stats.put("total_dead_tuples", row[3]);
                stats.put("media_dead_ratio_pct", row[4]);
                stats.put("total_inserts", row[5]);
                stats.put("total_updates", row[6]);
                stats.put("total_deletes", row[7]);
            }
            
            
            String queryConexoes = """
                SELECT 
                    COUNT(*) as total,
                    COUNT(*) FILTER (WHERE state = 'active') as ativas,
                    COUNT(*) FILTER (WHERE state = 'idle') as idle,
                    COUNT(*) FILTER (WHERE state = 'idle in transaction') as idle_in_transaction
                FROM pg_stat_activity
                WHERE pid != pg_backend_pid();
            """;
            
            @SuppressWarnings("unchecked")
            List<Object[]> conexoesResults = entityManager.createNativeQuery(queryConexoes).getResultList();
            
            if (!conexoesResults.isEmpty()) {
                Object[] conexoes = conexoesResults.get(0);
                Map<String, Object> conexoesInfo = new HashMap<>();
                conexoesInfo.put("total", conexoes[0]);
                conexoesInfo.put("ativas", conexoes[1]);
                conexoesInfo.put("idle", conexoes[2]);
                conexoesInfo.put("idle_in_transaction", conexoes[3]);
                stats.put("conexoes", conexoesInfo);
            }
            
            stats.put("timestamp", new Date());
            
            log.info("Estatísticas gerais consultadas com sucesso");
            
            return ResponseEntity.ok(stats);
            
        } catch (Exception e) {
            log.error("Erro ao consultar estatísticas", e);
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao consultar banco de dados");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @GetMapping("/health/largest-tables")
    @Operation(summary = "Maiores tabelas", description = "Lista as maiores tabelas por tamanho")
    public ResponseEntity<Map<String, Object>> maioresTabelas(
            @Parameter(description = "Número de tabelas a listar")
            @RequestParam(required = false, defaultValue = "20") Integer limite) {
        
        log.info("Consultando maiores tabelas (limite: {})", limite);
        
        String query = """
            SELECT 
                schemaname,
                relname,
                pg_size_pretty(pg_total_relation_size(schemaname||'.'||relname)) AS size,
                pg_total_relation_size(schemaname||'.'||relname) AS size_bytes,
                n_live_tup,
                n_dead_tup,
                ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) AS dead_ratio_pct
            FROM pg_stat_user_tables
            ORDER BY pg_total_relation_size(schemaname||'.'||relname) DESC
            LIMIT ?;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query)
                    .setParameter(1, limite)
                    .getResultList();
            
            List<Map<String, Object>> tabelas = new ArrayList<>();
            
            for (Object[] row : results) {
                Map<String, Object> tabelaInfo = new HashMap<>();
                tabelaInfo.put("schema", row[0]);
                tabelaInfo.put("tabela", row[1]);
                tabelaInfo.put("tamanho", row[2]);
                tabelaInfo.put("tamanho_bytes", row[3]);
                tabelaInfo.put("registros_vivos", row[4]);
                tabelaInfo.put("dead_tuples", row[5]);
                tabelaInfo.put("dead_ratio_pct", row[6]);
                
                tabelas.add(tabelaInfo);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("total", tabelas.size());
            response.put("limite", limite);
            response.put("tabelas", tabelas);
            response.put("timestamp", new Date());
            
            log.info("Retornadas {} maiores tabelas", tabelas.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao consultar maiores tabelas", e);
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao consultar banco de dados");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @PostMapping("/maintenance/vacuum/{schema}/{tabela}")
    @Operation(summary = "Executar VACUUM", description = "Executa VACUUM ANALYZE em uma tabela específica")
    public ResponseEntity<Map<String, Object>> executarVacuum(
            @Parameter(description = "Schema da tabela")
            @PathVariable String schema,
            @Parameter(description = "Nome da tabela")
            @PathVariable String tabela,
            @Parameter(description = "Executar VACUUM FULL (mais lento, bloqueia tabela)")
            @RequestParam(required = false, defaultValue = "false") Boolean full) {
        
        log.warn("Executando VACUUM {} em {}.{}", full ? "FULL" : "", schema, tabela);
        
        
        if (!schema.matches("^[a-zA-Z_][a-zA-Z0-9_]*$") || !tabela.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Nome de schema ou tabela inválido");
            return ResponseEntity.badRequest().body(error);
        }
        
        String vacuumCommand = full 
            ? String.format("VACUUM FULL ANALYZE %s.%s", schema, tabela)
            : String.format("VACUUM ANALYZE %s.%s", schema, tabela);
        
        try {
            long inicio = System.currentTimeMillis();
            entityManager.createNativeQuery(vacuumCommand).executeUpdate();
            long duracao = System.currentTimeMillis() - inicio;
            
            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", true);
            response.put("schema", schema);
            response.put("tabela", tabela);
            response.put("tipo", full ? "VACUUM FULL" : "VACUUM");
            response.put("duracao_ms", duracao);
            response.put("timestamp", new Date());
            
            log.info("VACUUM {} em {}.{} executado em {}ms", full ? "FULL" : "", schema, tabela, duracao);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao executar VACUUM em {}.{}", schema, tabela, e);
            Map<String, Object> error = new HashMap<>();
            error.put("sucesso", false);
            error.put("erro", "Erro ao executar VACUUM");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @GetMapping("/locks")
    @Operation(summary = "Locks ativos", description = "Lista todos os locks ativos no banco de dados")
    public ResponseEntity<Map<String, Object>> listarLocks() {
        
        log.info("Consultando locks ativos no banco");
        
        String query = """
            SELECT 
                l.locktype,
                l.database,
                l.relation::regclass as tabela,
                l.mode,
                l.granted,
                a.pid,
                a.usename,
                a.application_name,
                now() - a.query_start AS duration,
                LEFT(a.query, 100) as query_preview
            FROM pg_locks l
            LEFT JOIN pg_stat_activity a ON l.pid = a.pid
            WHERE l.pid != pg_backend_pid()
            ORDER BY duration DESC NULLS LAST
            LIMIT 100;
        """;
        
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
            
            List<Map<String, Object>> locks = new ArrayList<>();
            
            for (Object[] row : results) {
                Map<String, Object> lockInfo = new HashMap<>();
                lockInfo.put("tipo_lock", row[0]);
                lockInfo.put("database", row[1]);
                lockInfo.put("tabela", row[2]);
                lockInfo.put("modo", row[3]);
                lockInfo.put("concedido", row[4]);
                lockInfo.put("pid", row[5]);
                lockInfo.put("usuario", row[6]);
                lockInfo.put("aplicacao", row[7]);
                lockInfo.put("duracao", row[8]);
                lockInfo.put("query_preview", row[9]);
                
                locks.add(lockInfo);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("total", locks.size());
            response.put("locks", locks);
            response.put("timestamp", new Date());
            
            log.info("Encontrados {} locks ativos", locks.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao consultar locks", e);
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao consultar banco de dados");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
