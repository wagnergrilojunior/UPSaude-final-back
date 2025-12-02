package com.upsaude.util;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Classe de exemplo demonstrando o uso de métricas personalizadas com:
 * - @Timed: mede o tempo de execução de métodos
 * - @Counted: conta invocações de métodos
 * - @Observed: observabilidade com Micrometer Observation API
 * 
 * Esta classe serve como referência para implementar métricas em outros serviços.
 * 
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsExampleService {

    private final MeterRegistry meterRegistry;
    private final Random random = new Random();

    /**
     * Exemplo de uso da anotação @Timed.
     * Esta anotação automaticamente registra o tempo de execução do método.
     * 
     * @param delay Simula um processamento que leva tempo
     * @return Resultado do processamento
     */
    @Timed(
        value = "upsaude.example.timed.method",
        description = "Tempo de execução de método exemplo com @Timed",
        percentiles = {0.5, 0.9, 0.95, 0.99}
    )
    public String exemploMetodoTimed(long delay) {
        try {
            Thread.sleep(delay);
            return "Processamento concluído em " + delay + "ms";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Processamento interrompido";
        }
    }

    /**
     * Exemplo de uso da anotação @Counted.
     * Esta anotação automaticamente conta quantas vezes o método foi chamado.
     * 
     * @param success Indica se a operação foi bem-sucedida
     * @return Resultado da operação
     */
    @Counted(
        value = "upsaude.example.counted.method",
        description = "Contador de invocações de método exemplo com @Counted",
        extraTags = {"operation", "exemplo"}
    )
    public String exemploMetodoCounted(boolean success) {
        if (success) {
            // Incrementa contador de sucessos
            Counter.builder("upsaude.example.counted.success")
                .description("Contador de operações bem-sucedidas")
                .tag("status", "success")
                .register(meterRegistry)
                .increment();
            return "Operação bem-sucedida";
        } else {
            // Incrementa contador de falhas
            Counter.builder("upsaude.example.counted.failure")
                .description("Contador de operações falhadas")
                .tag("status", "failure")
                .register(meterRegistry)
                .increment();
            return "Operação falhou";
        }
    }

    /**
     * Exemplo de uso da anotação @Observed.
     * Esta anotação fornece observabilidade completa com spans e contexto.
     * 
     * @param operation Nome da operação
     * @return Resultado da operação
     */
    @Observed(
        name = "upsaude.example.observed.method",
        contextualName = "exemplo-operacao-observada"
    )
    public String exemploMetodoObserved(String operation) {
        log.info("Executando operação observada: {}", operation);
        
        // Simula processamento
        try {
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return "Operação " + operation + " concluída";
    }

    /**
     * Exemplo de uso programático de métricas (sem anotações).
     * Útil quando você precisa de controle mais fino sobre as métricas.
     */
    public String exemploMetricasProgramaticas() {
        // Usa o Timer registrado na MetricsConfig
        Timer.Sample sample = Timer.start(meterRegistry);
        
        try {
            // Simula processamento
            Thread.sleep(random.nextInt(200));
            
            // Incrementa contador de requisições totais
            Counter.builder("upsaude.example.programmatic.total")
                .description("Contador programático de requisições")
                .register(meterRegistry)
                .increment();
            
            return "Processamento concluído";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            
            // Incrementa contador de falhas
            Counter.builder("upsaude.example.programmatic.failed")
                .description("Contador programático de falhas")
                .register(meterRegistry)
                .increment();
            
            return "Processamento interrompido";
        } finally {
            // Registra o tempo decorrido
            sample.stop(Timer.builder("upsaude.example.programmatic.duration")
                .description("Duração de processamento programático")
                .register(meterRegistry));
        }
    }

    /**
     * Exemplo combinando múltiplas métricas.
     */
    @Timed(value = "upsaude.example.combined.timed")
    @Counted(value = "upsaude.example.combined.counted")
    @Observed(name = "upsaude.example.combined.observed")
    public String exemploMetricasCombinadas(String operacao) {
        log.info("Executando operação combinada: {}", operacao);
        
        // Simula processamento
        try {
            Thread.sleep(random.nextInt(150));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return "Operação combinada " + operacao + " concluída";
    }
}

