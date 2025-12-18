package com.upsaude.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.config.SigtapProperties;
import com.upsaude.entity.sigtap.SigtapCompatibilidade;
import com.upsaude.entity.sigtap.SigtapCompatibilidadePossivel;
import com.upsaude.entity.sigtap.SigtapFormaOrganizacao;
import com.upsaude.entity.sigtap.SigtapGrupo;
import com.upsaude.entity.sigtap.SigtapProcedimento;
import com.upsaude.entity.sigtap.SigtapProcedimentoDetalhe;
import com.upsaude.entity.sigtap.SigtapSubgrupo;
import com.upsaude.exception.SigtapSoapException;
import com.upsaude.integration.sigtap.soap.client.CompatibilidadePossivelSoapClient;
import com.upsaude.integration.sigtap.soap.client.CompatibilidadeSoapClient;
import com.upsaude.integration.sigtap.soap.client.NivelAgregacaoSoapClient;
import com.upsaude.integration.sigtap.soap.client.ProcedimentoSoapClient;
import com.upsaude.integration.sigtap.wsdl.BaseProcedimentoType;
import com.upsaude.integration.sigtap.wsdl.CompatibilidadePossivelType;
import com.upsaude.integration.sigtap.wsdl.CompatibilidadeType;
import com.upsaude.integration.sigtap.wsdl.FormaOrganizacaoType;
import com.upsaude.integration.sigtap.wsdl.GrupoType;
import com.upsaude.integration.sigtap.wsdl.PaginacaoType;
import com.upsaude.integration.sigtap.wsdl.ProcedimentoType;
import com.upsaude.integration.sigtap.wsdl.ResponseDetalharProcedimento;
import com.upsaude.integration.sigtap.wsdl.ResponseListarCompatibilidadesPossiveis;
import com.upsaude.integration.sigtap.wsdl.ResponseListarFormaOrganizacao;
import com.upsaude.integration.sigtap.wsdl.ResponseListarGrupos;
import com.upsaude.integration.sigtap.wsdl.ResponseListarSubgrupos;
import com.upsaude.integration.sigtap.wsdl.ResponsePesquisarCompatibilidades;
import com.upsaude.integration.sigtap.wsdl.ResponsePesquisarProcedimentos;
import com.upsaude.entity.sigtap.SigtapSyncStatus;
import com.upsaude.repository.referencia.sigtap.SigtapCompatibilidadePossivelRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCompatibilidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapFormaOrganizacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapGrupoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoDetalheRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapSubgrupoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapSyncStatusRepository;
import com.upsaude.service.SigtapSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigtapSyncServiceImpl implements SigtapSyncService {

    private static final int PAGE_SIZE = 100; // limite do servi?o SIGTAP
    private static final DateTimeFormatter COMPETENCIA_FMT = DateTimeFormatter.ofPattern("yyyyMM");

    private final SigtapProperties properties;
    private final ObjectMapper objectMapper;

    private final NivelAgregacaoSoapClient nivelAgregacaoSoapClient;
    private final ProcedimentoSoapClient procedimentoSoapClient;
    private final CompatibilidadePossivelSoapClient compatibilidadePossivelSoapClient;
    private final CompatibilidadeSoapClient compatibilidadeSoapClient;

    private final SigtapGrupoRepository grupoRepository;
    private final SigtapSubgrupoRepository subgrupoRepository;
    private final SigtapFormaOrganizacaoRepository formaOrganizacaoRepository;
    private final SigtapProcedimentoRepository procedimentoRepository;
    private final SigtapProcedimentoDetalheRepository procedimentoDetalheRepository;
    private final SigtapCompatibilidadePossivelRepository compatibilidadePossivelRepository;
    private final SigtapCompatibilidadeRepository compatibilidadeRepository;
    private final SigtapSyncStatusRepository syncStatusRepository;

    // Configuração de retry
    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_RETRY_DELAY_MS = 1000; // 1 segundo
    private static final double RETRY_BACKOFF_MULTIPLIER = 2.0;

    @Override
    public void sincronizarTudo(String competencia) {
        String comp = normalizarCompetencia(competencia);
        log.info("SIGTAP sync iniciando competencia={}", comp);

        Optional<SigtapSyncStatus> statusOpt = syncStatusRepository.findByCompetenciaAndStatus(comp, "em_andamento")
                .or(() -> syncStatusRepository.findByCompetenciaAndStatus(comp, "pausada"));

        String etapaInicial = "grupos";
        if (statusOpt.isPresent()) {
            SigtapSyncStatus status = statusOpt.get();
            etapaInicial = status.getEtapaAtual();
            log.info("SIGTAP retomando sincronização competencia={} etapa={}", comp, etapaInicial);
        } else {
            // Cria novo status
            criarStatusSync(comp, "grupos", "em_andamento", null, 0L);
        }

        try {
            // Define ordem das etapas
            List<String> etapas = List.of("grupos", "subgrupos", "formas_organizacao", "procedimentos", 
                    "detalhes", "compatibilidades_possiveis", "compatibilidades");
            
            int indiceInicial = etapas.indexOf(etapaInicial);
            if (indiceInicial < 0) indiceInicial = 0;

            // Executa etapas a partir do checkpoint
            for (int i = indiceInicial; i < etapas.size(); i++) {
                String etapa = etapas.get(i);
                atualizarStatusSync(comp, etapa, "em_andamento", null, null);

                switch (etapa) {
                    case "grupos":
                        sincronizarGruposComRetry();
                        break;
                    case "subgrupos":
                        sincronizarSubgruposComRetry();
                        break;
                    case "formas_organizacao":
                        sincronizarFormasOrganizacaoComRetry();
                        break;
                    case "procedimentos":
                        sincronizarProcedimentosComRetry(comp);
                        break;
                    case "detalhes":
                        detalharProcedimentosComRetry(comp);
                        break;
                    case "compatibilidades_possiveis":
                        sincronizarCompatibilidadesPossiveisComRetry();
                        break;
                    case "compatibilidades":
                        sincronizarCompatibilidadesComRetry(comp);
                        break;
                }
            }

            atualizarStatusSync(comp, "compatibilidades", "concluida", null, null);
            log.info("SIGTAP sync finalizado competencia={}", comp);

        } catch (Exception e) {
            log.error("SIGTAP sync erro competencia={}", comp, e);
            atualizarStatusSync(comp, etapaInicial, "erro", e.getMessage(), null);
            throw e;
        }
    }

    @Override
    public void sincronizarGrupos() {
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio sincronizarGrupos");

        long soapStart = System.currentTimeMillis();
        ResponseListarGrupos response = executarComRetry(
                () -> nivelAgregacaoSoapClient.listarGrupos(),
                "listarGrupos"
        );
        long soapEnd = System.currentTimeMillis();
        debugLog("soap_call", "listarGrupos", "A", soapEnd - soapStart, Map.of());
        
        // Coleta todos os códigos primeiro
        List<String> codigos = response.getGrupo().stream()
                .map(GrupoType::getCodigo)
                .filter(c -> c != null && !c.isBlank())
                .toList();
        
        // Batch lookup de todas as entidades existentes
        long lookupStart = System.currentTimeMillis();
        Map<String, SigtapGrupo> existentes = new java.util.HashMap<>();
        if (!codigos.isEmpty()) {
            grupoRepository.findByCodigoOficialIn(codigos).forEach(g -> existentes.put(g.getCodigoOficial(), g));
        }
        long lookupEnd = System.currentTimeMillis();
        debugLog("batch_lookup", "findByCodigoOficialIn", "A", lookupEnd - lookupStart, Map.of("size", codigos.size()));
        
        List<SigtapGrupo> batch = new java.util.ArrayList<>();
        for (GrupoType g : response.getGrupo()) {
            String codigo = g.getCodigo();
            if (codigo == null || codigo.isBlank()) continue;
            
            SigtapGrupo entity = existentes.getOrDefault(codigo, new SigtapGrupo());
            entity.setCodigoOficial(codigo);
            entity.setNome(g.getNome());
            batch.add(entity);
        }
        
        if (!batch.isEmpty()) {
            long batchStart = System.currentTimeMillis();
            saveBatchGrupos(batch);
            long batchEnd = System.currentTimeMillis();
            debugLog("batch_save", "saveBatchGrupos", "A", batchEnd - batchStart, Map.of("size", batch.size()));
        }

        log.info("SIGTAP etapa=fim sincronizarGrupos registros={} duracao_ms={}", batch.size(), System.currentTimeMillis() - inicio);
    }

    @Override
    public void sincronizarSubgrupos() {
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio sincronizarSubgrupos");

        List<SigtapGrupo> grupos = grupoRepository.findAll();
        List<SigtapSubgrupo> batch = new java.util.ArrayList<>();
        int batchSize = 100;

        for (SigtapGrupo grupo : grupos) {
            long soapStart = System.currentTimeMillis();
            ResponseListarSubgrupos response = executarComRetry(
                    () -> nivelAgregacaoSoapClient.listarSubgrupos(grupo.getCodigoOficial()),
                    "listarSubgrupos"
            );
            long soapEnd = System.currentTimeMillis();
            debugLog("soap_call", "listarSubgrupos", "A", soapEnd - soapStart, Map.of("grupo", grupo.getCodigoOficial()));
            
            // Coleta códigos do grupo atual
            List<String> codigos = response.getSubgrupo().stream()
                    .map(sg -> sg.getCodigo())
                    .filter(c -> c != null && !c.isBlank())
                    .toList();
            
            // Batch lookup
            long lookupStart = System.currentTimeMillis();
            Map<String, SigtapSubgrupo> existentes = new java.util.HashMap<>();
            if (!codigos.isEmpty()) {
                subgrupoRepository.findByGrupoCodigoOficialAndCodigoOficialIn(grupo.getCodigoOficial(), codigos)
                        .forEach(sg -> existentes.put(sg.getCodigoOficial(), sg));
            }
            long lookupEnd = System.currentTimeMillis();
            debugLog("batch_lookup", "findByGrupoCodigoOficialAndCodigoOficialIn", "A", lookupEnd - lookupStart, Map.of("size", codigos.size()));
            
            for (var sg : response.getSubgrupo()) {
                if (sg.getCodigo() == null || sg.getCodigo().isBlank()) continue;
                
                SigtapSubgrupo entity = existentes.getOrDefault(sg.getCodigo(), new SigtapSubgrupo());
                entity.setGrupo(grupo);
                entity.setCodigoOficial(sg.getCodigo());
                entity.setNome(sg.getNome());
                batch.add(entity);
                
                if (batch.size() >= batchSize) {
                    long batchStart = System.currentTimeMillis();
                    saveBatchSubgrupos(batch);
                    long batchEnd = System.currentTimeMillis();
                    debugLog("batch_save", "saveBatchSubgrupos", "A", batchEnd - batchStart, Map.of("size", batch.size()));
                    batch.clear();
                }
            }
        }
        
        if (!batch.isEmpty()) {
            long batchStart = System.currentTimeMillis();
            saveBatchSubgrupos(batch);
            long batchEnd = System.currentTimeMillis();
            debugLog("batch_save", "saveBatchSubgrupos", "A", batchEnd - batchStart, Map.of("size", batch.size()));
        }

        log.info("SIGTAP etapa=fim sincronizarSubgrupos registros={} duracao_ms={}", batch.size(), System.currentTimeMillis() - inicio);
    }

    @Override
    public void sincronizarFormasOrganizacao() {
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio sincronizarFormasOrganizacao");

        List<SigtapSubgrupo> subgrupos = subgrupoRepository.findAll();
        List<SigtapFormaOrganizacao> batch = new java.util.ArrayList<>();
        int batchSize = 100;

        for (SigtapSubgrupo subgrupo : subgrupos) {
            long soapStart = System.currentTimeMillis();
            ResponseListarFormaOrganizacao response = executarComRetry(
                    () -> nivelAgregacaoSoapClient.listarFormaOrganizacao(subgrupo.getCodigoOficial()),
                    "listarFormaOrganizacao"
            );
            long soapEnd = System.currentTimeMillis();
            debugLog("soap_call", "listarFormaOrganizacao", "A", soapEnd - soapStart, Map.of("subgrupo", subgrupo.getCodigoOficial()));
            
            // Coleta códigos do subgrupo atual
            List<String> codigos = response.getFormaOrganizacao().stream()
                    .map(FormaOrganizacaoType::getCodigo)
                    .filter(c -> c != null && !c.isBlank())
                    .toList();
            
            // Batch lookup
            long lookupStart = System.currentTimeMillis();
            Map<String, SigtapFormaOrganizacao> existentes = new java.util.HashMap<>();
            if (!codigos.isEmpty()) {
                formaOrganizacaoRepository.findBySubgrupoCodigoOficialAndCodigoOficialIn(subgrupo.getCodigoOficial(), codigos)
                        .forEach(fo -> existentes.put(fo.getCodigoOficial(), fo));
            }
            long lookupEnd = System.currentTimeMillis();
            debugLog("batch_lookup", "findBySubgrupoCodigoOficialAndCodigoOficialIn", "A", lookupEnd - lookupStart, Map.of("size", codigos.size()));
            
            for (FormaOrganizacaoType fo : response.getFormaOrganizacao()) {
                if (fo.getCodigo() == null || fo.getCodigo().isBlank()) continue;
                
                SigtapFormaOrganizacao entity = existentes.getOrDefault(fo.getCodigo(), new SigtapFormaOrganizacao());
                entity.setSubgrupo(subgrupo);
                entity.setCodigoOficial(fo.getCodigo());
                entity.setNome(fo.getNome());
                batch.add(entity);
                
                if (batch.size() >= batchSize) {
                    long batchStart = System.currentTimeMillis();
                    saveBatchFormaOrganizacao(batch);
                    long batchEnd = System.currentTimeMillis();
                    debugLog("batch_save", "saveBatchFormaOrganizacao", "A", batchEnd - batchStart, Map.of("size", batch.size()));
                    batch.clear();
                }
            }
        }
        
        if (!batch.isEmpty()) {
            long batchStart = System.currentTimeMillis();
            saveBatchFormaOrganizacao(batch);
            long batchEnd = System.currentTimeMillis();
            debugLog("batch_save", "saveBatchFormaOrganizacao", "A", batchEnd - batchStart, Map.of("size", batch.size()));
        }

        log.info("SIGTAP etapa=fim sincronizarFormasOrganizacao registros={} duracao_ms={}", batch.size(), System.currentTimeMillis() - inicio);
    }

    @Override
    public void sincronizarProcedimentos(String competencia) {
        String comp = normalizarCompetencia(competencia);
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio sincronizarProcedimentos competencia={}", comp);

        List<SigtapGrupo> grupos = grupoRepository.findAll();
        int detalhados = 0;

        for (SigtapGrupo grupo : grupos) {
            List<SigtapSubgrupo> subgrupos = subgrupoRepository.findByGrupoCodigoOficial(grupo.getCodigoOficial());
            for (SigtapSubgrupo subgrupo : subgrupos) {
                int registroInicial = 1;
                while (true) {
                    ResponsePesquisarProcedimentos resp = procedimentoSoapClient.pesquisarProcedimentos(
                            grupo.getCodigoOficial(),
                            subgrupo.getCodigoOficial(),
                            comp,
                            registroInicial,
                            PAGE_SIZE
                    );
                    List<BaseProcedimentoType> lista = resp.getResultadosPesquisaProcedimentos().getBaseProcedimento();
                    if (lista == null || lista.isEmpty()) {
                        break;
                    }

                    for (BaseProcedimentoType base : lista) {
                        try {
                            detalharEUpsertProcedimento(base.getCodigo(), comp);
                            detalhados++;
                        } catch (SigtapSoapException e) {
                            log.warn("SIGTAP falha ao detalhar procedimento codigo={} competencia={} (vai tentar novamente na etapa de detalhamento)", base.getCodigo(), comp);
                        }
                    }

                    PaginacaoType pag = resp.getResultadosPesquisaProcedimentos().getPaginacao();
                    if (pag == null || pag.getTotalRegistros() == null) {
                        if (lista.size() < PAGE_SIZE) break;
                        registroInicial += PAGE_SIZE;
                        continue;
                    }
                    int total = pag.getTotalRegistros().intValue();
                    registroInicial += PAGE_SIZE;
                    if (registroInicial > total) break;
                }
            }
        }

        log.info("SIGTAP etapa=fim sincronizarProcedimentos competencia={} procedimentos_detalhados={} duracao_ms={}",
                comp, detalhados, System.currentTimeMillis() - inicio);
    }

    @Override
    public void detalharProcedimentos(String competencia) {
        String comp = normalizarCompetencia(competencia);
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio detalharProcedimentos competencia={}", comp);

        // Estrat?gia: garantir que todo procedimento existente tenha um detalhe.
        // Em caso de falhas na etapa anterior, esta etapa reexecuta o detalhamento.
        List<SigtapProcedimento> procedimentos = procedimentoRepository.findAll();
        int detalhados = 0;

        for (SigtapProcedimento p : procedimentos) {
            Optional<SigtapProcedimentoDetalhe> det = procedimentoDetalheRepository.findByProcedimentoId(p.getId());
            if (det.isPresent()) continue;
            try {
                detalharEUpsertProcedimento(p.getCodigoOficial(), comp);
                detalhados++;
            } catch (Exception e) {
                log.warn("SIGTAP falha ao detalhar (retry) procedimento codigo={} competencia={}", p.getCodigoOficial(), comp, e);
            }
        }

        log.info("SIGTAP etapa=fim detalharProcedimentos competencia={} detalhados={} duracao_ms={}",
                comp, detalhados, System.currentTimeMillis() - inicio);
    }

    @Override
    public void sincronizarCompatibilidadesPossiveis() {
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio sincronizarCompatibilidadesPossiveis");

        ResponseListarCompatibilidadesPossiveis response = compatibilidadePossivelSoapClient.listarCompatibilidadesPossiveis();
        int upserts = 0;

        for (CompatibilidadePossivelType cp : response.getCompatibilidadePossivel()) {
            if (cp.getCodigo() == null || cp.getCodigo().isBlank()) continue;
            saveCompatibilidadePossivel(cp);
            upserts++;
        }

        log.info("SIGTAP etapa=fim sincronizarCompatibilidadesPossiveis registros={} duracao_ms={}", upserts, System.currentTimeMillis() - inicio);
    }

    @Override
    public void sincronizarCompatibilidades(String competencia) {
        String comp = normalizarCompetencia(competencia);
        long inicio = System.currentTimeMillis();
        log.info("SIGTAP etapa=inicio sincronizarCompatibilidades competencia={}", comp);

        int registroInicial = 1;
        int upserts = 0;

        while (true) {
            ResponsePesquisarCompatibilidades resp = compatibilidadeSoapClient.pesquisarCompatibilidades(
                    null, comp, null, null, null, registroInicial, PAGE_SIZE
            );
            List<CompatibilidadeType> lista = resp.getResultadosPesquisaCompatibilidades().getCompatibilidade();
            if (lista == null || lista.isEmpty()) break;

            for (CompatibilidadeType c : lista) {
                SigtapCompatibilidadePossivel cp = upsertCompatibilidadePossivel(c.getCompatibilidadePossivel());
                SigtapProcedimento principal = upsertProcedimentoBase(c.getProcedimentoPrincipal(), comp);
                SigtapProcedimento secundario = upsertProcedimentoBase(c.getProcedimentoSecundario(), comp);

                saveCompatibilidade(cp, principal, secundario, c);
                upserts++;
            }

            PaginacaoType pag = resp.getResultadosPesquisaCompatibilidades().getPaginacao();
            if (pag == null || pag.getTotalRegistros() == null) {
                if (lista.size() < PAGE_SIZE) break;
                registroInicial += PAGE_SIZE;
                continue;
            }
            int total = pag.getTotalRegistros().intValue();
            registroInicial += PAGE_SIZE;
            if (registroInicial > total) break;
        }

        log.info("SIGTAP etapa=fim sincronizarCompatibilidades competencia={} registros={} duracao_ms={}",
                comp, upserts, System.currentTimeMillis() - inicio);
    }

    private void detalharEUpsertProcedimento(String codigoProcedimento, String competencia) {
        if (codigoProcedimento == null || codigoProcedimento.isBlank()) return;
        ResponseDetalharProcedimento resp = procedimentoSoapClient.detalharProcedimento(codigoProcedimento, competencia);
        ProcedimentoType proc = resp.getResultadosDetalhaProcedimentos().getProcedimento();
        if (proc == null) return;

        SigtapFormaOrganizacao forma = upsertFormaOrganizacao(proc.getFormaOrganizacao());

        saveProcedimentoCompleto(proc, forma);
    }

    private SigtapProcedimento upsertProcedimentoBase(BaseProcedimentoType base, String competenciaFallback) {
        if (base == null || base.getCodigo() == null || base.getCodigo().isBlank()) {
            // n?o deveria acontecer, mas evitamos NPE
            SigtapProcedimento p = new SigtapProcedimento();
            p.setCodigoOficial("DESCONHECIDO");
            p.setCompetenciaInicial(competenciaFallback);
            return saveProcedimento(p);
        }

        String compIni = competenciaFallback;
        Optional<SigtapProcedimento> existente = procedimentoRepository.findTopByCodigoOficialOrderByCompetenciaInicialDesc(base.getCodigo());
        if (existente.isPresent()) {
            SigtapProcedimento p = existente.get();
            if (p.getNome() == null && base.getNome() != null) {
                p.setNome(base.getNome());
                return saveProcedimento(p);
            }
            return p;
        }

        SigtapProcedimento novo = new SigtapProcedimento();
        novo.setCodigoOficial(base.getCodigo());
        novo.setNome(base.getNome());
        novo.setCompetenciaInicial(compIni);
        return saveProcedimento(novo);
    }

    private SigtapCompatibilidadePossivel upsertCompatibilidadePossivel(CompatibilidadePossivelType cp) {
        if (cp == null || cp.getCodigo() == null || cp.getCodigo().isBlank()) {
            SigtapCompatibilidadePossivel entity = new SigtapCompatibilidadePossivel();
            entity.setCodigoOficial("0");
            return saveCompatibilidadePossivelEntity(entity);
        }
        return compatibilidadePossivelRepository.findByCodigoOficial(cp.getCodigo())
                .orElseGet(() -> {
                    SigtapCompatibilidadePossivel entity = new SigtapCompatibilidadePossivel();
                    entity.setCodigoOficial(cp.getCodigo());
                    entity.setTipoCompatibilidade(cp.getTipoCompatibilidade() == null ? null : cp.getTipoCompatibilidade().value());
                    entity.setInstrumentoRegistroPrincipal(toJsonQuiet(cp.getInstrumentoRegistroPrincipal()));
                    entity.setInstrumentoRegistroSecundario(toJsonQuiet(cp.getInstrumentoRegistroSecundario()));
                    return saveCompatibilidadePossivelEntity(entity);
                });
    }

    private SigtapFormaOrganizacao upsertFormaOrganizacao(FormaOrganizacaoType formaType) {
        if (formaType == null || formaType.getCodigo() == null || formaType.getCodigo().isBlank()) {
            return null;
        }
        SigtapSubgrupo subgrupo = null;
        if (formaType.getSubgrupo() != null && formaType.getSubgrupo().getCodigo() != null) {
            SigtapGrupo grupo = null;
            if (formaType.getSubgrupo().getGrupo() != null && formaType.getSubgrupo().getGrupo().getCodigo() != null) {
                grupo = grupoRepository.findByCodigoOficial(formaType.getSubgrupo().getGrupo().getCodigo())
                        .orElseGet(SigtapGrupo::new);
                grupo.setCodigoOficial(formaType.getSubgrupo().getGrupo().getCodigo());
                grupo.setNome(formaType.getSubgrupo().getGrupo().getNome());
                grupo = saveGrupo(grupo.getCodigoOficial(), grupo.getNome());
            }
            if (grupo != null) {
                subgrupo = subgrupoRepository.findByGrupoCodigoOficialAndCodigoOficial(grupo.getCodigoOficial(), formaType.getSubgrupo().getCodigo())
                        .orElseGet(SigtapSubgrupo::new);
                subgrupo.setGrupo(grupo);
                subgrupo.setCodigoOficial(formaType.getSubgrupo().getCodigo());
                subgrupo.setNome(formaType.getSubgrupo().getNome());
                subgrupo = saveSubgrupo(grupo, subgrupo.getCodigoOficial(), subgrupo.getNome());
            }
        }

        if (subgrupo == null) {
            return null;
        }

        SigtapFormaOrganizacao forma = formaOrganizacaoRepository
                .findBySubgrupoCodigoOficialAndCodigoOficial(subgrupo.getCodigoOficial(), formaType.getCodigo())
                .orElseGet(SigtapFormaOrganizacao::new);
        forma.setSubgrupo(subgrupo);
        forma.setCodigoOficial(formaType.getCodigo());
        forma.setNome(formaType.getNome());
        return saveFormaOrganizacao(subgrupo, forma.getCodigoOficial(), forma.getNome());
    }

    private String normalizarCompetencia(String competencia) {
        String comp = competencia;
        if (comp == null || comp.isBlank()) {
            comp = properties.getSync().getDefaultCompetencia();
        }
        if (comp == null || comp.isBlank()) {
            comp = YearMonth.now().minusMonths(1).format(COMPETENCIA_FMT);
        }
        comp = comp.replaceAll("[^0-9]", "");
        if (!comp.matches("\\d{6}")) {
            throw new IllegalArgumentException("Competência inválida (esperado AAAAMM): " + competencia);
        }
        return comp;
    }

    private String toJsonQuiet(Object obj) {
        if (obj == null) return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private void debugLog(String event, String location, String hypothesisId, long duration, Map<String, Object> data) {
        try (FileWriter fw = new FileWriter("/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back/.cursor/debug.log", true)) {
            fw.write(String.format("{\"sessionId\":\"debug-session\",\"runId\":\"run1\",\"hypothesisId\":\"%s\",\"location\":\"%s\",\"message\":\"%s\",\"data\":%s,\"timestamp\":%d}\n",
                hypothesisId, location, event, objectMapper.writeValueAsString(data), System.currentTimeMillis()));
        } catch (IOException e) {
        }
    }

    @Transactional
    private SigtapGrupo saveGrupo(String codigo, String nome) {
        SigtapGrupo entity = grupoRepository.findByCodigoOficial(codigo).orElseGet(SigtapGrupo::new);
        entity.setCodigoOficial(codigo);
        entity.setNome(nome);
        return grupoRepository.save(entity);
    }

    @Transactional
    private void saveBatchGrupos(List<SigtapGrupo> entities) {
        grupoRepository.saveAll(entities);
    }

    @Transactional
    private SigtapSubgrupo saveSubgrupo(SigtapGrupo grupo, String codigo, String nome) {
        SigtapSubgrupo entity = subgrupoRepository
                .findByGrupoCodigoOficialAndCodigoOficial(grupo.getCodigoOficial(), codigo)
                .orElseGet(SigtapSubgrupo::new);
        entity.setGrupo(grupo);
        entity.setCodigoOficial(codigo);
        entity.setNome(nome);
        return subgrupoRepository.save(entity);
    }

    @Transactional
    private void saveBatchSubgrupos(List<SigtapSubgrupo> entities) {
        subgrupoRepository.saveAll(entities);
    }

    @Transactional
    private SigtapFormaOrganizacao saveFormaOrganizacao(SigtapSubgrupo subgrupo, String codigo, String nome) {
        SigtapFormaOrganizacao entity = formaOrganizacaoRepository
                .findBySubgrupoCodigoOficialAndCodigoOficial(subgrupo.getCodigoOficial(), codigo)
                .orElseGet(SigtapFormaOrganizacao::new);
        entity.setSubgrupo(subgrupo);
        entity.setCodigoOficial(codigo);
        entity.setNome(nome);
        return formaOrganizacaoRepository.save(entity);
    }

    @Transactional
    private void saveBatchFormaOrganizacao(List<SigtapFormaOrganizacao> entities) {
        formaOrganizacaoRepository.saveAll(entities);
    }

    @Transactional
    private SigtapProcedimento saveProcedimento(SigtapProcedimento entity) {
        return procedimentoRepository.save(entity);
    }

    @Transactional
    private void saveProcedimentoCompleto(ProcedimentoType proc, SigtapFormaOrganizacao forma) {
        String compIni = proc.getCompetenciaInicial();
        SigtapProcedimento entity = procedimentoRepository
                .findByCodigoOficialAndCompetenciaInicial(proc.getCodigo(), compIni)
                .orElseGet(SigtapProcedimento::new);

        entity.setCodigoOficial(proc.getCodigo());
        entity.setNome(proc.getNome());
        entity.setCompetenciaInicial(compIni);
        entity.setCompetenciaFinal(proc.getCompetenciaFinal());
        entity.setFormaOrganizacao(forma);
        entity.setSexoPermitido(proc.getSexoPermitido());
        entity.setIdadeMinima(proc.getIdadeMinimaPermitida() == null ? null : proc.getIdadeMinimaPermitida().getQuantidadeLimite());
        entity.setIdadeMaxima(proc.getIdadeMaximaPermitida() == null ? null : proc.getIdadeMaximaPermitida().getQuantidadeLimite());
        entity.setMediaDiasInternacao(proc.getMediaPermanencia());
        entity.setQuantidadeMaximaDias(proc.getTempoPermanencia());
        entity.setLimiteMaximo(proc.getQuantidadeMaxima());
        entity.setValorServicoHospitalar(proc.getValorSH());
        entity.setValorServicoAmbulatorial(proc.getValorSA());
        entity.setValorServicoProfissional(proc.getValorSP());
        entity = procedimentoRepository.save(entity);

        SigtapProcedimentoDetalhe detalhe = procedimentoDetalheRepository.findByProcedimentoId(entity.getId())
                .orElseGet(SigtapProcedimentoDetalhe::new);
        detalhe.setProcedimento(entity);
        detalhe.setCompetenciaInicial(proc.getCompetenciaInicial());
        detalhe.setCompetenciaFinal(proc.getCompetenciaFinal());
        detalhe.setDescricaoCompleta(proc.getDescricao());

        detalhe.setCids(toJsonQuiet(proc.getCIDsVinculados()));
        detalhe.setCbos(toJsonQuiet(proc.getCBOsVinculados()));
        detalhe.setCategoriasCbo(toJsonQuiet(proc.getCategoriasCBOVinculadas()));
        detalhe.setTiposLeito(toJsonQuiet(proc.getTiposLeitoVinculados()));
        detalhe.setServicosClassificacoes(toJsonQuiet(proc.getServicosClassificacoesVinculados()));
        detalhe.setHabilitacoes(toJsonQuiet(proc.getHabilitacoesVinculadas()));
        detalhe.setGruposHabilitacao(toJsonQuiet(proc.getGruposHabilitacaoVinculados()));
        detalhe.setIncrementos(toJsonQuiet(proc.getIncrementosVinculados()));
        detalhe.setComponentesRede(toJsonQuiet(proc.getComponentesRedesVinculados()));
        detalhe.setOrigensSigtap(toJsonQuiet(proc.getOrigensSIGTAP()));
        detalhe.setOrigensSiaSih(toJsonQuiet(proc.getOrigensSIASIH()));
        detalhe.setRegrasCondicionadas(toJsonQuiet(proc.getRegrasCondicionadasVinculadas()));
        detalhe.setRenases(toJsonQuiet(proc.getRENASESVinculadas()));
        detalhe.setTuss(toJsonQuiet(proc.getTUSSVinculadas()));

        procedimentoDetalheRepository.save(detalhe);
    }

    @Transactional
    private SigtapCompatibilidadePossivel saveCompatibilidadePossivel(CompatibilidadePossivelType cp) {
        SigtapCompatibilidadePossivel entity = compatibilidadePossivelRepository
                .findByCodigoOficial(cp.getCodigo())
                .orElseGet(SigtapCompatibilidadePossivel::new);
        entity.setCodigoOficial(cp.getCodigo());
        entity.setTipoCompatibilidade(cp.getTipoCompatibilidade() == null ? null : cp.getTipoCompatibilidade().value());
        entity.setInstrumentoRegistroPrincipal(toJsonQuiet(cp.getInstrumentoRegistroPrincipal()));
        entity.setInstrumentoRegistroSecundario(toJsonQuiet(cp.getInstrumentoRegistroSecundario()));
        return compatibilidadePossivelRepository.save(entity);
    }

    @Transactional
    private SigtapCompatibilidadePossivel saveCompatibilidadePossivelEntity(SigtapCompatibilidadePossivel entity) {
        return compatibilidadePossivelRepository.save(entity);
    }

    @Transactional
    private void saveCompatibilidade(SigtapCompatibilidadePossivel cp, SigtapProcedimento principal,
                                     SigtapProcedimento secundario, CompatibilidadeType c) {
        String compIni = c.getCompetenciaInicial();
        SigtapCompatibilidade entity = compatibilidadeRepository
                .findByCompatibilidadePossivelIdAndProcedimentoPrincipalIdAndProcedimentoSecundarioIdAndCompetenciaInicial(
                        cp.getId(), principal.getId(), secundario.getId(), compIni
                )
                .orElseGet(SigtapCompatibilidade::new);
        entity.setCompatibilidadePossivel(cp);
        entity.setProcedimentoPrincipal(principal);
        entity.setProcedimentoSecundario(secundario);
        entity.setCompetenciaInicial(compIni);
        entity.setCompetenciaFinal(c.getCompetenciaFinal());
        entity.setQuantidadePermitida(c.getQuantidadePermitida() == null ? null : c.getQuantidadePermitida().intValue());
        entity.setDocumentoPublicacao(toJsonQuiet(c.getDocumentoPublicacao()));
        entity.setDocumentoRevogacao(toJsonQuiet(c.getDocumentoRevogacao()));
        compatibilidadeRepository.save(entity);
    }

    private <T> T executarComRetry(java.util.function.Supplier<T> operacao, String operacaoNome) {
        int tentativas = 0;
        Exception ultimoErro = null;

        while (tentativas < MAX_RETRIES) {
            try {
                return operacao.get();
            } catch (SigtapSoapException e) {
                ultimoErro = e;
                tentativas++;

                String mensagemErro = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                boolean erroRecuperavel = mensagemErro.contains("circuit") ||
                        mensagemErro.contains("timeout") ||
                        mensagemErro.contains("connection") ||
                        (e.getCause() != null && e.getCause().getMessage() != null &&
                                e.getCause().getMessage().toLowerCase().contains("circuit"));

                if (!erroRecuperavel || tentativas >= MAX_RETRIES) {
                    log.error("SIGTAP erro não recuperável ou tentativas esgotadas operacao={} tentativa={}/{}",
                            operacaoNome, tentativas, MAX_RETRIES, e);
                    throw e;
                }

                long delay = (long) (INITIAL_RETRY_DELAY_MS * Math.pow(RETRY_BACKOFF_MULTIPLIER, tentativas - 1));
                log.warn("SIGTAP erro recuperável operacao={} tentativa={}/{} aguardando {}ms antes de retry",
                        operacaoNome, tentativas, MAX_RETRIES, delay, e);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new SigtapSoapException("Retry interrompido para " + operacaoNome, ie);
                }
            }
        }

        throw new SigtapSoapException("Falha após " + MAX_RETRIES + " tentativas em " + operacaoNome, ultimoErro);
    }

    /**
     * Executa uma operação void com retry.
     */
    private void executarComRetryVoid(Runnable operacao, String operacaoNome) {
        executarComRetry(() -> {
            operacao.run();
            return null;
        }, operacaoNome);
    }

    @Transactional
    private void criarStatusSync(String competencia, String etapa, String status, String progresso, Long totalRegistros) {
        SigtapSyncStatus syncStatus = new SigtapSyncStatus();
        syncStatus.setCompetencia(competencia);
        syncStatus.setEtapaAtual(etapa);
        syncStatus.setStatus(status);
        syncStatus.setProgressoAtual(progresso);
        syncStatus.setTotalRegistrosProcessados(totalRegistros);
        syncStatus.setTentativasErro(0);
        syncStatusRepository.save(syncStatus);
    }

    /**
     * Atualiza o status de sincronização existente.
     */
    @Transactional
    private void atualizarStatusSync(String competencia, String etapa, String status, String erro, Long totalRegistros) {
        Optional<SigtapSyncStatus> statusOpt = syncStatusRepository.findFirstByCompetenciaOrderByCreatedAtDesc(competencia);
        SigtapSyncStatus syncStatus = statusOpt.orElseGet(SigtapSyncStatus::new);

        syncStatus.setCompetencia(competencia);
        syncStatus.setEtapaAtual(etapa);
        syncStatus.setStatus(status);
        syncStatus.setUltimoErro(erro);
        syncStatus.setUltimaTentativaEm(OffsetDateTime.now());
        if (totalRegistros != null) {
            syncStatus.setTotalRegistrosProcessados(totalRegistros);
        }
        if (erro != null) {
            syncStatus.setTentativasErro((syncStatus.getTentativasErro() != null ? syncStatus.getTentativasErro() : 0) + 1);
        }

        syncStatusRepository.save(syncStatus);
    }

    private void sincronizarGruposComRetry() {
        executarComRetryVoid(() -> sincronizarGrupos(), "sincronizarGrupos");
    }

    private void sincronizarSubgruposComRetry() {
        executarComRetryVoid(() -> sincronizarSubgrupos(), "sincronizarSubgrupos");
    }

    private void sincronizarFormasOrganizacaoComRetry() {
        executarComRetryVoid(() -> sincronizarFormasOrganizacao(), "sincronizarFormasOrganizacao");
    }

    private void sincronizarProcedimentosComRetry(String competencia) {
        executarComRetryVoid(() -> sincronizarProcedimentos(competencia), "sincronizarProcedimentos");
    }

    private void detalharProcedimentosComRetry(String competencia) {
        executarComRetryVoid(() -> detalharProcedimentos(competencia), "detalharProcedimentos");
    }

    private void sincronizarCompatibilidadesPossiveisComRetry() {
        executarComRetryVoid(() -> sincronizarCompatibilidadesPossiveis(), "sincronizarCompatibilidadesPossiveis");
    }

    private void sincronizarCompatibilidadesComRetry(String competencia) {
        executarComRetryVoid(() -> sincronizarCompatibilidades(competencia), "sincronizarCompatibilidades");
    }
}

