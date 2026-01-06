package com.upsaude.service.impl.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.referencia.sia.SiaPa;
import com.upsaude.importacao.sia.file.SiaPaCsvParser;
import com.upsaude.mapper.sia.SiaPaEntityMapper;
import com.upsaude.repository.referencia.sia.SiaPaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("legacy-import")
@Deprecated
public class SiaPaFileImportServiceImpl {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Value("${sia.import.base-path:data_import/sia}")
    private String basePath;

    @Value("${sia.import.batch-size:1000}")
    private int batchSize;

    private final SiaPaCsvParser csvParser;
    private final SiaPaEntityMapper entityMapper;
    private final SiaPaRepository siaPaRepository;

    public SiaPaFileImportServiceImpl(
            SiaPaCsvParser csvParser,
            SiaPaEntityMapper entityMapper,
            SiaPaRepository siaPaRepository) {
        this.csvParser = csvParser;
        this.entityMapper = entityMapper;
        this.siaPaRepository = siaPaRepository;
    }

    public ImportResult importarMes(String ano, String uf, String mes) {
        log.info("Iniciando importação SIA-SUS PA: {}/{}/{}", ano, uf, mes);
        ImportResult result = new ImportResult(ano, uf, mes);

        Path mesPath = Paths.get(basePath, ano, uf, mes);
        if (!Files.exists(mesPath) || !Files.isDirectory(mesPath)) {
            throw new IllegalArgumentException("Pasta do mês não encontrada: " + mesPath);
        }

        try {

            try (var paths = Files.list(mesPath)) {
                List<Path> arquivosCsv = paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".csv"))
                    .sorted()
                    .toList();

                if (arquivosCsv.isEmpty()) {
                    log.warn("Nenhum arquivo CSV encontrado em: {}", mesPath);
                    result.addErro("Nenhum arquivo CSV encontrado");
                    return result;
                }

                String competencia = extrairCompetencia(ano, mes);

                for (Path arquivoCsv : arquivosCsv) {
                    importarArquivo(arquivoCsv, competencia, uf, result);
                }
            }

            log.info("Importação SIA-SUS PA {}/{}/{} concluída. Total: {} linhas processadas, {} erros", 
                    ano, uf, mes, result.getTotalLinhasProcessadas(), result.getTotalErros());
        } catch (Exception e) {
            log.error("Erro ao importar mês {}/{}/{}: {}", ano, uf, mes, e.getMessage(), e);
            result.addErro("Erro geral: " + e.getMessage());
        }

        return result;
    }

    private String extrairCompetencia(String ano, String mes) {
        return ano + mes;
    }

    private void importarArquivo(Path arquivoPath, String competencia, String uf, ImportResult result) {
        String nomeArquivo = arquivoPath.getFileName().toString();
        log.info("Processando arquivo: {}", nomeArquivo);

        try {
            ImportContext context = new ImportContext(arquivoPath, competencia, uf);
            int linhasProcessadas = importarComBatch(context);
            result.addLinhasProcessadas(nomeArquivo, linhasProcessadas);
            log.info("Arquivo {} importado: {} linhas processadas", nomeArquivo, linhasProcessadas);
        } catch (Exception e) {
            log.error("Erro ao importar arquivo {}: {}", nomeArquivo, e.getMessage(), e);
            result.addErro(nomeArquivo + ": " + e.getMessage());
        }
    }

    @Transactional(timeout = 7200) 
    private int importarComBatch(ImportContext context) {
        List<SiaPa> batch = new ArrayList<>();
        int totalLinhas = 0;
        int linhasComErro = 0;
        int linhasLidas = 0;
        long inicioTempo = System.currentTimeMillis();
        List<String> errosDetalhados = new ArrayList<>();
        String[] headers = null;

        try (BufferedReader reader = Files.newBufferedReader(context.arquivoPath, UTF_8)) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                linhasLidas++;

                if (linhasLidas % 10000 == 0) {
                    log.info("Processando arquivo {}: {} linhas lidas, {} processadas, {} erros", 
                            context.arquivoPath.getFileName(), linhasLidas, totalLinhas, linhasComErro);
                }

                try {
                    if (linha.trim().isEmpty()) {
                        continue;
                    }

                    if (primeiraLinha) {
                        headers = parseCsvHeaders(linha);
                        primeiraLinha = false;
                        continue;
                    }

                    Map<String, String> fields = csvParser.parseLine(linha, headers);

                    if (!validarCamposBasicos(fields, linhasLidas, errosDetalhados)) {
                        linhasComErro++;
                        continue;
                    }

                    SiaPa entity;
                    try {
                        entity = entityMapper.mapToSiaPa(fields, context.competencia, context.uf);
                    } catch (Exception e) {
                        linhasComErro++;
                        String erroMsg = String.format("Linha %d: Erro ao mapear entidade: %s", linhasLidas, e.getMessage());
                        errosDetalhados.add(erroMsg);
                        log.debug("Erro ao mapear linha {}: {}", linhasLidas, e.getMessage());
                        continue;
                    }

                    if (entity == null) {
                        linhasComErro++;
                        errosDetalhados.add(String.format("Linha %d: Entidade nula após mapeamento", linhasLidas));
                        continue;
                    }

                    batch.add(entity);

                    if (batch.size() >= batchSize) {
                        try {
                            siaPaRepository.saveAll(batch);
                            totalLinhas += batch.size();
                            batch.clear();
                        } catch (Exception e) {
                            log.error("Erro ao salvar batch no arquivo {} (linhas {}-{}): {}", 
                                    context.arquivoPath.getFileName(), 
                                    linhasLidas - batchSize + 1, 
                                    linhasLidas, 
                                    e.getMessage(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                        }
                    }
                } catch (Exception e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.debug("Erro ao processar linha {}: {}", linhasLidas, e.getMessage());

                    if (errosDetalhados.size() > 100) {
                        errosDetalhados.remove(0);
                    }
                }
            }

            if (!batch.isEmpty()) {
                try {
                    siaPaRepository.saveAll(batch);
                    totalLinhas += batch.size();
                } catch (Exception e) {
                    log.error("Erro ao salvar batch final no arquivo {}: {}", 
                            context.arquivoPath.getFileName(), e.getMessage(), e);
                    linhasComErro += batch.size();
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler arquivo {}: {}", context.arquivoPath, e.getMessage(), e);
            throw new RuntimeException("Erro ao ler arquivo: " + context.arquivoPath, e);
        }

        long tempoDecorrido = System.currentTimeMillis() - inicioTempo;

        if (linhasComErro > 0) {
            log.warn("Arquivo {} concluído: {} linhas processadas, {} erros, tempo: {}ms", 
                    context.arquivoPath.getFileName(), totalLinhas, linhasComErro, tempoDecorrido);

            int errosParaLogar = Math.min(10, errosDetalhados.size());
            for (int i = 0; i < errosParaLogar; i++) {
                log.warn("  Erro {}: {}", i + 1, errosDetalhados.get(i));
            }
            if (errosDetalhados.size() > 10) {
                log.warn("  ... e mais {} erros (total: {})", 
                        errosDetalhados.size() - 10, errosDetalhados.size());
            }
        } else {
            log.info("Arquivo {} concluído com sucesso: {} linhas processadas, tempo: {}ms", 
                    context.arquivoPath.getFileName(), totalLinhas, tempoDecorrido);
        }

        return totalLinhas;
    }

    private String[] parseCsvHeaders(String linhaHeader) {
        if (linhaHeader == null || linhaHeader.trim().isEmpty()) {
            throw new IllegalArgumentException("Cabeçalho CSV vazio");
        }

        java.util.List<String> headersList = new java.util.ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean dentroAspas = false;

        for (int i = 0; i < linhaHeader.length(); i++) {
            char c = linhaHeader.charAt(i);

            if (c == '"') {
                if (dentroAspas && i + 1 < linhaHeader.length() && linhaHeader.charAt(i + 1) == '"') {

                    campoAtual.append('"');
                    i++; 
                } else {

                    dentroAspas = !dentroAspas;
                }
            } else if (c == ',' && !dentroAspas) {

                String header = campoAtual.toString().trim();

                if (header.startsWith("\"") && header.endsWith("\"") && header.length() >= 2) {
                    header = header.substring(1, header.length() - 1);
                }
                headersList.add(header.trim());
                campoAtual = new StringBuilder();
            } else {
                campoAtual.append(c);
            }
        }

        String header = campoAtual.toString().trim();

        if (header.startsWith("\"") && header.endsWith("\"") && header.length() >= 2) {
            header = header.substring(1, header.length() - 1);
        }
        headersList.add(header.trim());

        return headersList.toArray(new String[0]);
    }

    private boolean validarCamposBasicos(Map<String, String> fields, int numeroLinha, List<String> errosDetalhados) {
        boolean temCampos = fields.values().stream().anyMatch(v -> v != null && !v.trim().isEmpty());

        if (!temCampos) {
            errosDetalhados.add(String.format("Linha %d: Linha vazia ou sem campos válidos", numeroLinha));
            return false;
        }

        String procedimento = fields.get("PA_PROC_ID");
        if (procedimento == null || procedimento.trim().isEmpty()) {
            errosDetalhados.add(String.format("Linha %d: Campo PA_PROC_ID obrigatório ausente", numeroLinha));
            return false;
        }

        return true;
    }

    private static class ImportContext {
        final Path arquivoPath;
        final String competencia;
        final String uf;

        ImportContext(Path arquivoPath, String competencia, String uf) {
            this.arquivoPath = arquivoPath;
            this.competencia = competencia;
            this.uf = uf;
        }
    }

    public static class ImportResult {
        private final String ano;
        private final String uf;
        private final String mes;
        private int totalLinhasProcessadas = 0;
        private int totalErros = 0;
        private final java.util.Map<String, Integer> linhasPorArquivo = new java.util.HashMap<>();
        private final java.util.List<String> erros = new java.util.ArrayList<>();

        public ImportResult(String ano, String uf, String mes) {
            this.ano = ano;
            this.uf = uf;
            this.mes = mes;
        }

        public void addLinhasProcessadas(String arquivo, int linhas) {
            linhasPorArquivo.put(arquivo, linhas);
            totalLinhasProcessadas += linhas;
        }

        public void addErro(String erro) {
            erros.add(erro);
            totalErros++;
        }

        public String getAno() { return ano; }
        public String getUf() { return uf; }
        public String getMes() { return mes; }
        public int getTotalLinhasProcessadas() { return totalLinhasProcessadas; }
        public int getTotalErros() { return totalErros; }
        public java.util.Map<String, Integer> getLinhasPorArquivo() { return linhasPorArquivo; }
        public java.util.List<String> getErros() { return erros; }
    }
}
