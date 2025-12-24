package com.upsaude.service.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Serviço responsável por definir ordem de processamento e prioridades dos arquivos SIGTAP.
 */
@Slf4j
@Service
public class SigtapJobOrchestrator {

    private static final Map<String, Integer> MAPA_PRIORIDADES = criarMapaPrioridades();

    /**
     * Calcula a prioridade de um arquivo baseado no seu nome.
     * Maior número = maior prioridade (será processado primeiro).
     */
    public int calcularPrioridade(String nomeArquivo) {
        if (nomeArquivo == null) {
            return 0;
        }

        String nomeNormalizado = nomeArquivo.toLowerCase().trim();
        Integer prioridade = MAPA_PRIORIDADES.get(nomeNormalizado);

        if (prioridade != null) {
            return prioridade;
        }

        // Prioridade padrão para arquivos não mapeados (menor prioridade)
        log.warn("Arquivo não mapeado na ordem de prioridades: {}. Usando prioridade padrão 0.", nomeArquivo);
        return 0;
    }

    /**
     * Ordena arquivos por prioridade (maior primeiro).
     */
    public List<ArquivoComPrioridade> ordenarArquivos(List<SigtapZipExtractionService.ArquivoPar> pares) {
        if (pares == null || pares.isEmpty()) {
            return Collections.emptyList();
        }

        List<ArquivoComPrioridade> arquivosComPrioridade = new ArrayList<>();

        for (SigtapZipExtractionService.ArquivoPar par : pares) {
            String nomeArquivo = par.getArquivoDados().getNome();
            int prioridade = calcularPrioridade(nomeArquivo);

            ArquivoComPrioridade acp = new ArquivoComPrioridade();
            acp.setPar(par);
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
        return new HashMap<>(MAPA_PRIORIDADES);
    }

    /**
     * Cria o mapa de prioridades baseado nas dependências dos arquivos SIGTAP.
     */
    private static Map<String, Integer> criarMapaPrioridades() {
        Map<String, Integer> mapa = new HashMap<>();

        // Prioridade 1000 (maior) - Tabelas base sem dependências
        int prioridadeBase = 1000;
        mapa.put("tb_grupo.txt", prioridadeBase);
        mapa.put("tb_sub_grupo.txt", prioridadeBase);
        mapa.put("tb_financiamento.txt", prioridadeBase);
        mapa.put("tb_rubrica.txt", prioridadeBase);
        mapa.put("tb_modalidade.txt", prioridadeBase);
        mapa.put("tb_registro.txt", prioridadeBase);
        mapa.put("tb_tipo_leito.txt", prioridadeBase);
        mapa.put("tb_servico.txt", prioridadeBase);
        mapa.put("tb_servico_classificacao.txt", prioridadeBase);
        mapa.put("tb_ocupacao.txt", prioridadeBase);
        mapa.put("tb_habilitacao.txt", prioridadeBase);
        mapa.put("tb_grupo_habilitacao.txt", prioridadeBase);
        mapa.put("tb_regra_condicionada.txt", prioridadeBase);
        mapa.put("tb_renases.txt", prioridadeBase);
        mapa.put("tb_tuss.txt", prioridadeBase);
        mapa.put("tb_componente_rede.txt", prioridadeBase);
        mapa.put("tb_rede_atencao.txt", prioridadeBase);
        mapa.put("tb_sia_sih.txt", prioridadeBase);
        mapa.put("tb_cid.txt", prioridadeBase);

        // Prioridade 800 - Tabelas que dependem das base
        int prioridadeDependentes = 800;
        mapa.put("tb_procedimento.txt", prioridadeDependentes);
        mapa.put("tb_forma_organizacao.txt", prioridadeDependentes);
        mapa.put("tb_detalhe.txt", prioridadeDependentes);
        mapa.put("tb_descricao.txt", prioridadeDependentes);
        mapa.put("tb_descricao_detalhe.txt", prioridadeDependentes);

        // Prioridade 500 - Relações simples
        int prioridadeRelacoesSimples = 500;
        mapa.put("rl_procedimento_modalidade.txt", prioridadeRelacoesSimples);
        mapa.put("rl_procedimento_registro.txt", prioridadeRelacoesSimples);
        mapa.put("rl_procedimento_comp_rede.txt", prioridadeRelacoesSimples);
        mapa.put("rl_procedimento_origem.txt", prioridadeRelacoesSimples);
        mapa.put("rl_procedimento_regra_cond.txt", prioridadeRelacoesSimples);
        mapa.put("rl_procedimento_renases.txt", prioridadeRelacoesSimples);
        mapa.put("rl_procedimento_tuss.txt", prioridadeRelacoesSimples);

        // Prioridade 300 - Relações intermediárias
        int prioridadeRelacoesIntermediarias = 300;
        mapa.put("rl_procedimento_cid.txt", prioridadeRelacoesIntermediarias);
        mapa.put("rl_procedimento_ocupacao.txt", prioridadeRelacoesIntermediarias);
        mapa.put("rl_procedimento_habilitacao.txt", prioridadeRelacoesIntermediarias);
        mapa.put("rl_procedimento_leito.txt", prioridadeRelacoesIntermediarias);
        mapa.put("rl_procedimento_servico.txt", prioridadeRelacoesIntermediarias);
        mapa.put("rl_procedimento_incremento.txt", prioridadeRelacoesIntermediarias);
        mapa.put("rl_procedimento_sia_sih.txt", prioridadeRelacoesIntermediarias);

        // Prioridade 100 (menor) - Relações complexas
        int prioridadeRelacoesComplexas = 100;
        mapa.put("rl_procedimento_detalhe.txt", prioridadeRelacoesComplexas);
        mapa.put("rl_excecao_compatibilidade.txt", prioridadeRelacoesComplexas);

        return Collections.unmodifiableMap(mapa);
    }

    /**
     * Representa um arquivo com sua prioridade calculada.
     */
    public static class ArquivoComPrioridade {
        private SigtapZipExtractionService.ArquivoPar par;
        private int prioridade;
        private String nomeArquivo;

        public SigtapZipExtractionService.ArquivoPar getPar() {
            return par;
        }

        public void setPar(SigtapZipExtractionService.ArquivoPar par) {
            this.par = par;
        }

        public int getPrioridade() {
            return prioridade;
        }

        public void setPrioridade(int prioridade) {
            this.prioridade = prioridade;
        }

        public String getNomeArquivo() {
            return nomeArquivo;
        }

        public void setNomeArquivo(String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
        }
    }
}

