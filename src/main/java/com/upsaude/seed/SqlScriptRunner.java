package com.upsaude.seed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Executor de scripts SQL para popular o banco de dados com dados de teste.
 * Executa todos os scripts SQL na pasta db/seed em ordem numérica.
 * Roda apenas quando app.seed.enabled=true.
 * 
 * @author UPSaúde
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = false)
public class SqlScriptRunner implements CommandLineRunner {

    private final DataSource dataSource;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("=== Iniciando execução de scripts SQL de seed ===");
        
        try {
            List<Resource> scripts = carregarScripts();
            log.info("Encontrados {} scripts SQL para executar", scripts.size());
            
            for (Resource script : scripts) {
                executarScript(script);
            }
            
            log.info("=== Execução de scripts SQL concluída com sucesso ===");
        } catch (Exception e) {
            log.error("Erro ao executar scripts SQL de seed", e);
            throw new RuntimeException("Falha ao executar scripts SQL", e);
        }
    }

    private List<Resource> carregarScripts() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:db/seed/*.sql");
        
        return Arrays.stream(resources)
                .sorted(Comparator.comparing(this::obterNomeArquivo))
                .collect(Collectors.toList());
    }

    private String obterNomeArquivo(Resource resource) {
        try {
            return resource.getFilename();
        } catch (Exception e) {
            return "";
        }
    }

    private void executarScript(Resource script) {
        try {
            log.info("Executando script: {}", script.getFilename());
            
            // Usar ScriptUtils do Spring para executar scripts SQL completos
            // Isso lida corretamente com blocos DO $$ ... $$ e múltiplos comandos
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, script);
                log.info("Script {} executado com sucesso", script.getFilename());
            }
        } catch (Exception e) {
            log.error("Erro ao executar script {}", script.getFilename(), e);
            throw new RuntimeException("Falha ao executar script: " + script.getFilename(), e);
        }
    }
}

