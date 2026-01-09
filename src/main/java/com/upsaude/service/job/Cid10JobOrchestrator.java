package com.upsaude.service.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Serviço responsável por definir a ordem de processamento e prioridades
 * para os jobs de importação do CID-10/CID-O, baseando-se nas dependências entre os arquivos.
 */
@Slf4j
@Service
public class Cid10JobOrchestrator {

    private static final Map<String, Integer> PRIORIDADES_ARQUIVOS = criarMapaPrioridades();

    /**
     * Retorna a prioridade de um arquivo CID-10/CID-O com base em seu nome.
     * Arquivos não mapeados terão prioridade 0.
     *
     * @param nomeArquivo Nome do arquivo (ex: "CID-10-CAPITULOS.CSV").
     * @return Prioridade do job.
     */
    public int calcularPrioridade(String nomeArquivo) {
        if (nomeArquivo == null) {
            return 0;
        }

        String nomeNormalizado = nomeArquivo.toUpperCase().trim();
        Integer prioridade = PRIORIDADES_ARQUIVOS.get(nomeNormalizado);

        if (prioridade != null) {
            return prioridade;
        }

        // Prioridade padrão para arquivos não mapeados (menor prioridade)
        log.warn("Arquivo não mapeado na ordem de prioridades: {}. Usando prioridade padrão 0.", nomeArquivo);
        return 0;
    }

    /**
     * Ordena uma lista de arquivos com base em suas prioridades.
     *
     * @param arquivos Lista de ArquivoExtraido.
     * @return Lista de ArquivoComPrioridade ordenada.
     */
    public List<ArquivoComPrioridade> ordenarArquivos(List<Cid10ZipExtractionService.ArquivoExtraido> arquivos) {
        if (arquivos == null || arquivos.isEmpty()) {
            return Collections.emptyList();
        }

        List<ArquivoComPrioridade> arquivosComPrioridade = new ArrayList<>();

        for (Cid10ZipExtractionService.ArquivoExtraido arquivo : arquivos) {
            String nomeArquivo = arquivo.getNome();
            int prioridade = calcularPrioridade(nomeArquivo);

            ArquivoComPrioridade acp = new ArquivoComPrioridade();
            acp.setArquivo(arquivo);
            acp.setPrioridade(prioridade);
            acp.setNomeArquivo(nomeArquivo);

            arquivosComPrioridade.add(acp);
        }

        // Ordena por prioridade descendente (maior primeiro)
        arquivosComPrioridade.sort((a, b) -> Integer.compare(b.getPrioridade(), a.getPrioridade()));

        log.info("Arquivos ordenados por prioridade. Total: {}", arquivosComPrioridade.size());
        for (ArquivoComPrioridade acp : arquivosComPrioridade) {
            log.debug("  {} - prioridade: {}", acp.getNomeArquivo(), acp.getPrioridade());
        }

        return arquivosComPrioridade;
    }

    /**
     * Retorna o mapa completo de prioridades.
     */
    public Map<String, Integer> obterMapaPrioridades() {
        return new HashMap<>(PRIORIDADES_ARQUIVOS);
    }

    /**
     * Cria o mapa de prioridades baseado nas dependências dos arquivos CID-10/CID-O.
     */
    private static Map<String, Integer> criarMapaPrioridades() {
        Map<String, Integer> mapa = new HashMap<>();

        // Prioridade 1000 (maior) - Tabela base sem dependências
        mapa.put("CID-10-CAPITULOS.CSV", 1000);

        // Prioridade 800 - Grupos (podem referenciar capítulos, mas são independentes)
        mapa.put("CID-10-GRUPOS.CSV", 800);

        // Prioridade 600 - Categorias principais (independentes)
        mapa.put("CID-10-CATEGORIAS.CSV", 600);

        // Prioridade 400 - Subcategorias (dependem de categorias - campo categoriaCat)
        mapa.put("CID-10-SUBCATEGORIAS.CSV", 400);

        // Prioridade 300 - CID-O Grupos (independente, é CID-O)
        mapa.put("CID-O-GRUPOS.CSV", 300);

        // Prioridade 200 (menor) - CID-O Categorias (independente, é CID-O)
        mapa.put("CID-O-CATEGORIAS.CSV", 200);

        return Collections.unmodifiableMap(mapa);
    }

    /**
     * Representa um arquivo com sua prioridade calculada.
     */
    @Data
    @AllArgsConstructor
    public static class ArquivoComPrioridade {
        private Cid10ZipExtractionService.ArquivoExtraido arquivo;
        private int prioridade;
        private String nomeArquivo;

        public ArquivoComPrioridade() {
        }
    }
}

