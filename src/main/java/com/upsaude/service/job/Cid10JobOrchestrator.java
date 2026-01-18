package com.upsaude.service.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class Cid10JobOrchestrator {

    private static final Map<String, Integer> PRIORIDADES_ARQUIVOS = criarMapaPrioridades();

    
    public int calcularPrioridade(String nomeArquivo) {
        if (nomeArquivo == null) {
            return 0;
        }

        String nomeNormalizado = nomeArquivo.toUpperCase().trim();
        Integer prioridade = PRIORIDADES_ARQUIVOS.get(nomeNormalizado);

        if (prioridade != null) {
            return prioridade;
        }

        
        log.warn("Arquivo não mapeado na ordem de prioridades: {}. Usando prioridade padrão 0.", nomeArquivo);
        return 0;
    }

    
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

        
        arquivosComPrioridade.sort((a, b) -> Integer.compare(b.getPrioridade(), a.getPrioridade()));

        log.info("Arquivos ordenados por prioridade. Total: {}", arquivosComPrioridade.size());
        for (ArquivoComPrioridade acp : arquivosComPrioridade) {
            log.debug("  {} - prioridade: {}", acp.getNomeArquivo(), acp.getPrioridade());
        }

        return arquivosComPrioridade;
    }

    
    public Map<String, Integer> obterMapaPrioridades() {
        return new HashMap<>(PRIORIDADES_ARQUIVOS);
    }

    
    private static Map<String, Integer> criarMapaPrioridades() {
        Map<String, Integer> mapa = new HashMap<>();

        
        mapa.put("CID-10-CAPITULOS.CSV", 1000);

        
        mapa.put("CID-10-GRUPOS.CSV", 800);

        
        mapa.put("CID-10-CATEGORIAS.CSV", 600);

        
        mapa.put("CID-10-SUBCATEGORIAS.CSV", 400);

        
        mapa.put("CID-O-GRUPOS.CSV", 300);

        
        mapa.put("CID-O-CATEGORIAS.CSV", 200);

        return Collections.unmodifiableMap(mapa);
    }

    
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

