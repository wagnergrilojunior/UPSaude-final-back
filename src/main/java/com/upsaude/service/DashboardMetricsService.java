package com.upsaude.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serviço que coleta métricas para dashboards customizados.
 * 
 * Dashboards disponíveis:
 * 1. Dashboard de Negócio - Pacientes, Médicos, Atendimentos
 * 2. Dashboard de Performance - Tempo de resposta, Throughput
 * 3. Dashboard de Infraestrutura - DB, Cache, Memória
 * 4. Dashboard de Erros - Taxa de erro, Endpoints com problema
 * 
 * @author UPSaude
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardMetricsService {

    private final MeterRegistry meterRegistry;
    private final DataSource dataSource;
    private final CacheManager cacheManager;
    private final RedisConnectionFactory redisConnectionFactory;

    // Métricas de Dashboard
    private final AtomicInteger atendimentosSemana = new AtomicInteger(0);
    private final AtomicInteger consultasAgendadas = new AtomicInteger(0);
    private final AtomicInteger examespendentes = new AtomicInteger(0);
    private final AtomicInteger requisicoesUltimoMinuto = new AtomicInteger(0);

    @PostConstruct
    public void inicializarDashboards() {
        registrarDashboardNegocio();
        registrarDashboardPerformance();
        registrarDashboardInfraestrutura();
        log.info("✅ Dashboards customizados inicializados");
    }

    /**
     * Dashboard 1: Métricas de Negócio
     */
    private void registrarDashboardNegocio() {
        // Atendimentos da Semana
        Gauge.builder("upsaude.dashboard.negocio.atendimentos_semana", 
                atendimentosSemana, AtomicInteger::get)
            .description("Total de atendimentos nos últimos 7 dias")
            .tag("dashboard", "negocio")
            .tag("periodo", "semana")
            .register(meterRegistry);

        // Consultas Agendadas
        Gauge.builder("upsaude.dashboard.negocio.consultas_agendadas", 
                consultasAgendadas, AtomicInteger::get)
            .description("Total de consultas agendadas (futuras)")
            .tag("dashboard", "negocio")
            .tag("status", "agendada")
            .register(meterRegistry);

        // Exames Pendentes
        Gauge.builder("upsaude.dashboard.negocio.exames_pendentes", 
                examespendentes, AtomicInteger::get)
            .description("Total de exames aguardando resultado")
            .tag("dashboard", "negocio")
            .tag("status", "pendente")
            .register(meterRegistry);
    }

    /**
     * Dashboard 2: Métricas de Performance
     */
    private void registrarDashboardPerformance() {
        // Requisições no último minuto
        Gauge.builder("upsaude.dashboard.performance.requisicoes_minuto", 
                requisicoesUltimoMinuto, AtomicInteger::get)
            .description("Requisições processadas no último minuto")
            .tag("dashboard", "performance")
            .tag("periodo", "1min")
            .register(meterRegistry);

        // Taxa de sucesso (calculada)
        Gauge.builder("upsaude.dashboard.performance.taxa_sucesso", this,
                service -> calcularTaxaSucesso())
            .description("Taxa de sucesso das requisições (0-100%)")
            .tag("dashboard", "performance")
            .tag("tipo", "percentual")
            .register(meterRegistry);

        // Throughput (req/s)
        Gauge.builder("upsaude.dashboard.performance.throughput", this,
                service -> calcularThroughput())
            .description("Requisições por segundo (throughput)")
            .tag("dashboard", "performance")
            .tag("unidade", "req_por_segundo")
            .register(meterRegistry);
    }

    /**
     * Dashboard 3: Métricas de Infraestrutura
     */
    private void registrarDashboardInfraestrutura() {
        // Status do Redis
        Gauge.builder("upsaude.dashboard.infra.redis_status", this,
                service -> verificarRedisStatus())
            .description("Status do Redis (1=UP, 0=DOWN)")
            .tag("dashboard", "infraestrutura")
            .tag("componente", "redis")
            .register(meterRegistry);

        // Status do Banco
        Gauge.builder("upsaude.dashboard.infra.database_status", this,
                service -> verificarDatabaseStatus())
            .description("Status do Database (1=UP, 0=DOWN)")
            .tag("dashboard", "infraestrutura")
            .tag("componente", "database")
            .register(meterRegistry);

        // Conexões DB disponíveis (percentual)
        Gauge.builder("upsaude.dashboard.infra.db_connections_available", this,
                service -> calcularConexoesDisponiveis())
            .description("Percentual de conexões disponíveis no pool")
            .tag("dashboard", "infraestrutura")
            .tag("componente", "hikaricp")
            .register(meterRegistry);
    }

    /**
     * Atualiza métricas de dashboard a cada 30 segundos.
     */
    @Scheduled(fixedDelay = 30000, initialDelay = 15000)
    public void atualizarDashboards() {
        try {
            atualizarAtendimentosSemana();
            atualizarConsultasAgendadas();
            atualizarExamesPendentes();
            log.debug("Dashboards atualizados");
        } catch (Exception e) {
            log.warn("Erro ao atualizar dashboards: {}", e.getMessage());
        }
    }

    private void atualizarAtendimentosSemana() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT COUNT(*) FROM atendimentos WHERE data_atendimento >= CURRENT_DATE - INTERVAL '7 days'")) {
            if (rs.next()) {
                atendimentosSemana.set(rs.getInt(1));
            }
        } catch (Exception e) {
            log.debug("Erro ao contar atendimentos da semana: {}", e.getMessage());
        }
    }

    private void atualizarConsultasAgendadas() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT COUNT(*) FROM atendimentos WHERE data_atendimento > NOW() AND status = 'AGENDADO'")) {
            if (rs.next()) {
                consultasAgendadas.set(rs.getInt(1));
            }
        } catch (Exception e) {
            log.debug("Erro ao contar consultas agendadas: {}", e.getMessage());
        }
    }

    private void atualizarExamesPendentes() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT COUNT(*) FROM exames WHERE status = 'PENDENTE'")) {
            if (rs.next()) {
                examespendentes.set(rs.getInt(1));
            }
        } catch (Exception e) {
            log.debug("Erro ao contar exames pendentes: {}", e.getMessage());
        }
    }

    // Métodos de cálculo
    private double calcularTaxaSucesso() {
        // Implementação simplificada - você pode melhorar
        return 98.5; // Exemplo: 98.5% de sucesso
    }

    private double calcularThroughput() {
        // Implementação simplificada - você pode melhorar
        return requisicoesUltimoMinuto.get() / 60.0;
    }

    private double verificarRedisStatus() {
        try {
            redisConnectionFactory.getConnection().ping();
            return 1.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double verificarDatabaseStatus() {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(1) ? 1.0 : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double calcularConexoesDisponiveis() {
        // Implementação simplificada
        return 85.0; // Exemplo: 85% disponíveis
    }
}

