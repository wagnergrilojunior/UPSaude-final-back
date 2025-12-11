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

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsExampleService {

    private final MeterRegistry meterRegistry;
    private final Random random = new Random();

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

    @Counted(
        value = "upsaude.example.counted.method",
        description = "Contador de invocações de método exemplo com @Counted",
        extraTags = {"operation", "exemplo"}
    )
    public String exemploMetodoCounted(boolean success) {
        if (success) {

            Counter.builder("upsaude.example.counted.success")
                .description("Contador de operações bem-sucedidas")
                .tag("status", "success")
                .register(meterRegistry)
                .increment();
            return "Operação bem-sucedida";
        } else {

            Counter.builder("upsaude.example.counted.failure")
                .description("Contador de operações falhadas")
                .tag("status", "failure")
                .register(meterRegistry)
                .increment();
            return "Operação falhou";
        }
    }

    @Observed(
        name = "upsaude.example.observed.method",
        contextualName = "exemplo-operacao-observada"
    )
    public String exemploMetodoObserved(String operation) {
        log.info("Executando operação observada: {}", operation);

        try {
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "Operação " + operation + " concluída";
    }

    public String exemploMetricasProgramaticas() {

        Timer.Sample sample = Timer.start(meterRegistry);

        try {

            Thread.sleep(random.nextInt(200));

            Counter.builder("upsaude.example.programmatic.total")
                .description("Contador programático de requisições")
                .register(meterRegistry)
                .increment();

            return "Processamento concluído";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            Counter.builder("upsaude.example.programmatic.failed")
                .description("Contador programático de falhas")
                .register(meterRegistry)
                .increment();

            return "Processamento interrompido";
        } finally {

            sample.stop(Timer.builder("upsaude.example.programmatic.duration")
                .description("Duração de processamento programático")
                .register(meterRegistry));
        }
    }

    @Timed(value = "upsaude.example.combined.timed")
    @Counted(value = "upsaude.example.combined.counted")
    @Observed(name = "upsaude.example.combined.observed")
    public String exemploMetricasCombinadas(String operacao) {
        log.info("Executando operação combinada: {}", operacao);

        try {
            Thread.sleep(random.nextInt(150));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "Operação combinada " + operacao + " concluída";
    }
}
