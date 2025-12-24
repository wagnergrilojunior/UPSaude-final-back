package com.upsaude.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Executor dedicado para processamento pesado de importações.
 * Isola jobs das threads HTTP (Tomcat).
 */
@Configuration
@Data
public class ImportJobExecutorConfig {

    @Value("${import.job.executor.core-pool-size:1}")
    private int corePoolSize;

    @Value("${import.job.executor.max-pool-size:1}")
    private int maxPoolSize;

    @Value("${import.job.executor.queue-capacity:10}")
    private int queueCapacity;

    @Value("${import.job.executor.await-termination-seconds:60}")
    private int awaitTerminationSeconds;

    @Bean(name = "importJobExecutor")
    public ThreadPoolTaskExecutor importJobExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("import-job-");

        // Se a fila do executor estiver cheia, rejeita (o job continua no banco e será reprocessado).
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.initialize();
        return executor;
    }
}


