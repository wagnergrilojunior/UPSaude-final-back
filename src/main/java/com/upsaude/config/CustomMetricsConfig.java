package com.upsaude.config;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Configuração de métricas customizadas para dashboards do Spring Boot Admin.
 * 
 * Métricas criadas:
 * - Total de pacientes cadastrados
 * - Total de médicos cadastrados
 * - Total de usuários cadastrados
 * - Taxa de requisições por endpoint
 * - Tempo de resposta médio
 * - Taxa de erros
 * - Conexões de banco ativas
 * - Hit rate do cache
 * 
 * @author UPSaude
 */
@Slf4j
@Configuration
public class CustomMetricsConfig {

    /**
     * Customiza tags globais das métricas.
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
            .commonTags("sistema", "UPSaude")
            .commonTags("tipo", "gestao_saude");
    }

    /**
     * Componente que atualiza métricas de negócio periodicamente.
     */
    @Slf4j
    @Component
    public static class BusinessMetricsCollector {

        private final MeterRegistry meterRegistry;
        private final DataSource dataSource;
        
        // Contadores atômicos
        private final AtomicLong totalPacientes = new AtomicLong(0);
        private final AtomicLong totalMedicos = new AtomicLong(0);
        private final AtomicLong totalUsuarios = new AtomicLong(0);
        private final AtomicLong totalAtendimentosHoje = new AtomicLong(0);

        public BusinessMetricsCollector(MeterRegistry meterRegistry, DataSource dataSource) {
            this.meterRegistry = meterRegistry;
            this.dataSource = dataSource;
            
            // Registra gauges que serão atualizados periodicamente
            registrarMetricasDeNegocio();
        }

        /**
         * Registra métricas de negócio como Gauges.
         */
        private void registrarMetricasDeNegocio() {
            // Total de Pacientes
            Gauge.builder("upsaude.pacientes.total", totalPacientes, AtomicLong::get)
                .description("Total de pacientes cadastrados no sistema")
                .tag("tipo", "negocio")
                .tag("entidade", "paciente")
                .register(meterRegistry);

            // Total de Médicos
            Gauge.builder("upsaude.medicos.total", totalMedicos, AtomicLong::get)
                .description("Total de médicos cadastrados no sistema")
                .tag("tipo", "negocio")
                .tag("entidade", "medico")
                .register(meterRegistry);

            // Total de Usuários
            Gauge.builder("upsaude.usuarios.total", totalUsuarios, AtomicLong::get)
                .description("Total de usuários cadastrados no sistema")
                .tag("tipo", "negocio")
                .tag("entidade", "usuario")
                .register(meterRegistry);

            // Total de Atendimentos Hoje
            Gauge.builder("upsaude.atendimentos.hoje", totalAtendimentosHoje, AtomicLong::get)
                .description("Total de atendimentos realizados hoje")
                .tag("tipo", "negocio")
                .tag("periodo", "hoje")
                .register(meterRegistry);

            log.info("✅ Métricas de negócio registradas com sucesso");
        }

        /**
         * Atualiza métricas de negócio a cada 60 segundos.
         * Consulta banco de dados para obter totais atualizados.
         */
        @Scheduled(fixedDelay = 60000, initialDelay = 10000)
        public void atualizarMetricasDeNegocio() {
            try {
                atualizarTotalPacientes();
                atualizarTotalMedicos();
                atualizarTotalUsuarios();
                atualizarAtendimentosHoje();
                log.debug("Métricas de negócio atualizadas");
            } catch (Exception e) {
                log.warn("Erro ao atualizar métricas de negócio: {}", e.getMessage());
            }
        }

        private void atualizarTotalPacientes() {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM pacientes")) {
                if (rs.next()) {
                    totalPacientes.set(rs.getLong(1));
                }
            } catch (Exception e) {
                log.debug("Erro ao contar pacientes: {}", e.getMessage());
            }
        }

        private void atualizarTotalMedicos() {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM medicos")) {
                if (rs.next()) {
                    totalMedicos.set(rs.getLong(1));
                }
            } catch (Exception e) {
                log.debug("Erro ao contar médicos: {}", e.getMessage());
            }
        }

        private void atualizarTotalUsuarios() {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM usuarios")) {
                if (rs.next()) {
                    totalUsuarios.set(rs.getLong(1));
                }
            } catch (Exception e) {
                log.debug("Erro ao contar usuários: {}", e.getMessage());
            }
        }

        private void atualizarAtendimentosHoje() {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                     "SELECT COUNT(*) FROM atendimentos WHERE DATE(data_atendimento) = CURRENT_DATE")) {
                if (rs.next()) {
                    totalAtendimentosHoje.set(rs.getLong(1));
                }
            } catch (Exception e) {
                log.debug("Erro ao contar atendimentos: {}", e.getMessage());
            }
        }
    }

    /**
     * Contador de requisições por endpoint (top N).
     */
    @Bean
    public Counter requisicoesPacientesCounter(MeterRegistry registry) {
        return Counter.builder("upsaude.endpoint.chamadas")
            .description("Total de chamadas ao endpoint de pacientes")
            .tag("endpoint", "/api/v1/pacientes")
            .tag("tipo", "crud")
            .register(registry);
    }

    @Bean
    public Counter requisicoesMedicosCounter(MeterRegistry registry) {
        return Counter.builder("upsaude.endpoint.chamadas")
            .description("Total de chamadas ao endpoint de médicos")
            .tag("endpoint", "/api/v1/medicos")
            .tag("tipo", "crud")
            .register(registry);
    }

    @Bean
    public Counter requisicoesAtendimentosCounter(MeterRegistry registry) {
        return Counter.builder("upsaude.endpoint.chamadas")
            .description("Total de chamadas ao endpoint de atendimentos")
            .tag("endpoint", "/api/v1/atendimentos")
            .tag("tipo", "operacao")
            .register(registry);
    }

    /**
     * Timer para medir latência de operações críticas.
     */
    @Bean
    public Timer operacoesCriticasTimer(MeterRegistry registry) {
        return Timer.builder("upsaude.operacoes.criticas")
            .description("Tempo de execução de operações críticas")
            .tag("tipo", "performance")
            .publishPercentiles(0.5, 0.95, 0.99)
            .publishPercentileHistogram()
            .register(registry);
    }

    /**
     * Gauge para monitorar uso de recursos.
     */
    @Bean
    public MeterBinder recursosGauge(DataSource dataSource) {
        return (registry) -> {
            // Monitora pool de conexões
            Gauge.builder("upsaude.database.pool.usage", dataSource, ds -> {
                try (Connection conn = ds.getConnection()) {
                    return conn.isValid(1) ? 1.0 : 0.0;
                } catch (Exception e) {
                    return 0.0;
                }
            })
            .description("Status do pool de conexões (1=OK, 0=Erro)")
            .tag("recurso", "database")
            .register(registry);
        };
    }
}

