package com.upsaude.integration.ibge.service;

import com.upsaude.api.response.integracoes.IbgeSincronizacaoResponse;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.integration.ibge.client.IbgeClient;
import com.upsaude.integration.ibge.dto.IbgeEstadoDTO;
import com.upsaude.integration.ibge.dto.IbgeMunicipioDTO;
import com.upsaude.integration.ibge.dto.IbgeProjecaoPopulacaoDTO;
import com.upsaude.integration.ibge.dto.IbgeRegiaoDTO;
import com.upsaude.integration.ibge.exception.IbgeIntegrationException;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementação do serviço de integração com IBGE
 */
@Slf4j
@Service
public class IbgeServiceImpl implements IbgeService {

    private final IbgeClient ibgeClient;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;
    private final PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public IbgeServiceImpl(IbgeClient ibgeClient,
                          EstadosRepository estadosRepository,
                          CidadesRepository cidadesRepository,
                          @Qualifier("apiTransactionManager") PlatformTransactionManager transactionManager) {
        this.ibgeClient = ibgeClient;
        this.estadosRepository = estadosRepository;
        this.cidadesRepository = cidadesRepository;
        this.transactionManager = transactionManager;
    }

    @PostConstruct
    private void init() {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    // Cache interno de regiões (mapeia código IBGE da região para nome)
    private final Map<Integer, String> cacheRegioes = new HashMap<>();
    
    // Tamanho do lote para processamento em batch
    private static final int BATCH_SIZE = 20;
    
    // Inicialização do cacheRegioes no construtor para evitar problemas de ordem
    {
        // Inicialização do cache vazio - será preenchido durante sincronização
    }

    @Override
    // NÃO usar @Transactional aqui - cada etapa tem sua própria transação
    // Uma transação única para toda a sincronização causaria timeout de conexão
    public IbgeSincronizacaoResponse sincronizarTudo(boolean sincronizarRegioes, boolean sincronizarEstados,
                                                      boolean sincronizarMunicipios, boolean atualizarPopulacao) {
        OffsetDateTime inicio = OffsetDateTime.now();
        IbgeSincronizacaoResponse response = IbgeSincronizacaoResponse.builder().build();

        try {
            // ETAPA 1: Sincronização de Regiões
            if (sincronizarRegioes) {
                log.info("Iniciando sincronização de regiões...");
                IbgeSincronizacaoResponse regioesResponse = sincronizarRegioes();
                response.setRegioesSincronizadas(regioesResponse.getRegioesSincronizadas());
                response.getRegioesErros().addAll(regioesResponse.getRegioesErros());
                response.getEtapasExecutadas().add("Regiões");
            }

            // ETAPA 2: Sincronização de Estados
            if (sincronizarEstados) {
                log.info("Iniciando sincronização de estados...");
                IbgeSincronizacaoResponse estadosResponse = sincronizarEstados();
                response.setEstadosSincronizados(estadosResponse.getEstadosSincronizados());
                response.getEstadosErros().addAll(estadosResponse.getEstadosErros());
                response.getEtapasExecutadas().add("Estados");
            }

            // ETAPA 3: Sincronização de Municípios
            if (sincronizarMunicipios) {
                log.info("Iniciando sincronização de municípios...");
                IbgeSincronizacaoResponse municipiosResponse = sincronizarMunicipios();
                response.setMunicipiosSincronizados(municipiosResponse.getMunicipiosSincronizados());
                response.getMunicipiosErros().addAll(municipiosResponse.getMunicipiosErros());
                response.getEtapasExecutadas().add("Municípios");
            }

            // ETAPA 4: Atualização de População
            if (atualizarPopulacao) {
                log.info("Iniciando atualização de população...");
                IbgeSincronizacaoResponse populacaoResponse = atualizarPopulacao();
                response.setPopulacaoAtualizada(populacaoResponse.getPopulacaoAtualizada());
                response.getPopulacaoErros().addAll(populacaoResponse.getPopulacaoErros());
                response.getEtapasExecutadas().add("População");
            }

        } catch (Exception e) {
            log.error("Erro durante sincronização completa: {}", e.getMessage(), e);
            throw new IbgeIntegrationException("Erro durante sincronização completa", e);
        } finally {
            OffsetDateTime fim = OffsetDateTime.now();
            response.setTempoExecucao(Duration.of(ChronoUnit.SECONDS.between(inicio, fim), java.time.temporal.ChronoUnit.SECONDS));
        }

        return response;
    }

    @Override
    // NÃO usar @Transactional aqui - apenas cache em memória, sem operações de banco
    public IbgeSincronizacaoResponse sincronizarRegioes() {
        IbgeSincronizacaoResponse response = IbgeSincronizacaoResponse.builder().build();
        cacheRegioes.clear();

        try {
            List<IbgeRegiaoDTO> regioes = ibgeClient.buscarRegioes();
            int sincronizadas = 0;

            for (IbgeRegiaoDTO regiao : regioes) {
                try {
                    cacheRegioes.put(regiao.getId(), regiao.getNome());
                    sincronizadas++;
                } catch (Exception e) {
                    log.error("Erro ao processar região {}: {}", regiao.getNome(), e.getMessage());
                    response.getRegioesErros().add("Região " + regiao.getNome() + ": " + e.getMessage());
                }
            }

            response.setRegioesSincronizadas(sincronizadas);
            log.info("Regiões sincronizadas: {}", sincronizadas);

        } catch (Exception e) {
            log.error("Erro ao sincronizar regiões: {}", e.getMessage(), e);
            response.getRegioesErros().add("Erro geral: " + e.getMessage());
            throw new IbgeIntegrationException("Erro ao sincronizar regiões", e);
        }

        return response;
    }

    @Override
    // OTIMIZAÇÃO: Busca todos os dados do IBGE primeiro, depois processa em lotes
    public IbgeSincronizacaoResponse sincronizarEstados() {
        IbgeSincronizacaoResponse response = IbgeSincronizacaoResponse.builder().build();

        try {
            // ETAPA 1: Buscar TODOS os estados do IBGE em memória (sem transação)
            log.info("Buscando todos os estados do IBGE...");
            List<IbgeEstadoDTO> estadosIbge = ibgeClient.buscarEstados();
            log.info("Total de {} estados recuperados do IBGE", estadosIbge.size());

            // ETAPA 2: Buscar estados existentes no banco (uma única consulta)
            log.info("Buscando estados existentes no banco...");
            Map<String, Estados> estadosExistentesPorCodigoIbge = buscarEstadosExistentes();
            Map<String, Estados> estadosExistentesPorSigla = buscarEstadosExistentesPorSigla();
            log.info("Total de {} estados encontrados no banco", estadosExistentesPorCodigoIbge.size());

            // ETAPA 3: Processar e salvar em lotes
            log.info("Processando e salvando estados em lotes de {}...", BATCH_SIZE);
            int sincronizados = 0;
            List<Estados> estadosParaSalvar = new ArrayList<>();

            for (int i = 0; i < estadosIbge.size(); i++) {
                IbgeEstadoDTO estadoIbge = estadosIbge.get(i);
                try {
                    Estados estado = prepararEstado(estadoIbge, estadosExistentesPorCodigoIbge, estadosExistentesPorSigla);
                    estadosParaSalvar.add(estado);

                    // Salva em lotes para evitar acúmulo de memória e timeout
                    if (estadosParaSalvar.size() >= BATCH_SIZE || i == estadosIbge.size() - 1) {
                        sincronizados += salvarLoteEstados(estadosParaSalvar, response);
                        estadosParaSalvar.clear();
                        
                        // Pequena pausa entre lotes
                        if (i < estadosIbge.size() - 1) {
                            Thread.sleep(100);
                        }
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.warn("Sincronização de estados interrompida");
                    break;
                } catch (Exception e) {
                    log.error("Erro ao processar estado {}: {}", estadoIbge.getNome(), e.getMessage());
                    response.getEstadosErros().add("Estado " + estadoIbge.getNome() + ": " + e.getMessage());
                }
            }

            response.setEstadosSincronizados(sincronizados);
            log.info("Estados sincronizados: {}", sincronizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar estados: {}", e.getMessage(), e);
            response.getEstadosErros().add("Erro geral: " + e.getMessage());
            throw new IbgeIntegrationException("Erro ao sincronizar estados", e);
        }

        return response;
    }

    /**
     * Busca todos os estados existentes no banco, indexados por código IBGE
     */
    @Transactional(readOnly = true)
    private Map<String, Estados> buscarEstadosExistentes() {
        List<Estados> estados = estadosRepository.findAll();
        Map<String, Estados> map = new HashMap<>();
        for (Estados estado : estados) {
            if (estado.getCodigoIbge() != null) {
                map.put(estado.getCodigoIbge(), estado);
            }
        }
        return map;
    }

    /**
     * Busca todos os estados existentes no banco, indexados por sigla
     */
    @Transactional(readOnly = true)
    private Map<String, Estados> buscarEstadosExistentesPorSigla() {
        List<Estados> estados = estadosRepository.findAll();
        Map<String, Estados> map = new HashMap<>();
        for (Estados estado : estados) {
            if (estado.getSigla() != null) {
                map.put(estado.getSigla(), estado);
            }
        }
        return map;
    }

    /**
     * Prepara um estado para salvar (sem salvar ainda)
     */
    private Estados prepararEstado(IbgeEstadoDTO estadoIbge, 
                                    Map<String, Estados> estadosPorCodigoIbge,
                                    Map<String, Estados> estadosPorSigla) {
        String codigoIbgeStr = String.valueOf(estadoIbge.getId());
        
        Estados estado;
        if (estadosPorCodigoIbge.containsKey(codigoIbgeStr)) {
            estado = estadosPorCodigoIbge.get(codigoIbgeStr);
        } else if (estadosPorSigla.containsKey(estadoIbge.getSigla())) {
            estado = estadosPorSigla.get(estadoIbge.getSigla());
        } else {
            estado = new Estados();
        }

        // Atualiza campos IBGE
        estado.setCodigoIbge(codigoIbgeStr);
        estado.setNomeOficialIbge(estadoIbge.getNome());
        estado.setSiglaIbge(estadoIbge.getSigla());
        
        // Associa região se disponível
        if (estadoIbge.getRegiao() != null) {
            String nomeRegiao = cacheRegioes.get(estadoIbge.getRegiao().getId());
            estado.setRegiaoIbge(nomeRegiao);
        }

        // Preserva campos locais se não existirem
        if (estado.getNome() == null) {
            estado.setNome(estadoIbge.getNome());
        }
        if (estado.getSigla() == null) {
            estado.setSigla(estadoIbge.getSigla());
        }
        if (estado.getActive() == null) {
            estado.setActive(true);
        }

        estado.setAtivoIbge(true);
        estado.setDataUltimaSincronizacaoIbge(OffsetDateTime.now());

        return estado;
    }

    /**
     * Salva um lote de estados em transações individuais para evitar timeout
     */
    private int salvarLoteEstados(List<Estados> estados, IbgeSincronizacaoResponse response) {
        int salvos = 0;
        for (Estados estado : estados) {
            try {
                salvarEstadoIndividual(estado);
                salvos++;
            } catch (Exception e) {
                log.error("Erro ao salvar estado {}: {}", estado.getNome(), e.getMessage());
                response.getEstadosErros().add("Estado " + estado.getNome() + ": " + e.getMessage());
            }
        }
        return salvos;
    }

    /**
     * Salva um estado individual em uma transação separada com flush imediato
     */
    @Transactional
    private void salvarEstadoIndividual(Estados estado) {
        estadosRepository.save(estado);
        entityManager.flush(); // Flush imediato para evitar batch
    }

    @Override
    // OTIMIZAÇÃO: Busca TODOS os dados do IBGE primeiro, armazena em memória, depois grava em lotes
    public IbgeSincronizacaoResponse sincronizarMunicipios() {
        IbgeSincronizacaoResponse response = IbgeSincronizacaoResponse.builder().build();

        try {
            // ETAPA 1: Buscar todos os estados do banco (uma única consulta)
            log.info("Buscando estados do banco...");
            List<Estados> estados = buscarEstadosComTransacao();
            Map<String, Estados> estadosPorSigla = new HashMap<>();
            for (Estados estado : estados) {
                if (estado.getSigla() != null) {
                    estadosPorSigla.put(estado.getSigla(), estado);
                }
            }
            log.info("Total de {} estados encontrados no banco", estados.size());

            // ETAPA 2: Buscar TODOS os municípios do IBGE e armazenar em memória
            log.info("Buscando todos os municípios do IBGE e armazenando em memória...");
            List<IbgeMunicipioDTO> todosMunicipiosIbge = new ArrayList<>();
            Map<String, String> ufPorMunicipio = new HashMap<>(); // Mapeia código IBGE do município para UF
            
            for (Estados estado : estados) {
                if (estado.getSigla() == null || estado.getSigla().isEmpty()) {
                    continue;
                }
                try {
                    List<IbgeMunicipioDTO> municipios = ibgeClient.buscarMunicipiosPorUf(estado.getSigla());
                    todosMunicipiosIbge.addAll(municipios);
                    
                    // Armazena a UF de cada município
                    for (IbgeMunicipioDTO municipio : municipios) {
                        ufPorMunicipio.put(String.valueOf(municipio.getId()), estado.getSigla());
                    }
                    
                    log.debug("UF {}: {} municípios recuperados do IBGE", estado.getSigla(), municipios.size());
                    
                    // Pequena pausa entre UFs para não sobrecarregar a API IBGE
                    Thread.sleep(50);
                } catch (Exception e) {
                    log.error("Erro ao buscar municípios da UF {} do IBGE: {}", estado.getSigla(), e.getMessage());
                    response.getMunicipiosErros().add("UF " + estado.getSigla() + " (busca IBGE): " + e.getMessage());
                }
            }
            log.info("Total de {} municípios recuperados do IBGE e armazenados em memória", todosMunicipiosIbge.size());

            // ETAPA 3: Buscar municípios existentes no banco (uma única consulta)
            log.info("Buscando municípios existentes no banco...");
            Map<String, Cidades> cidadesExistentesPorCodigoIbge = buscarCidadesExistentes();
            log.info("Total de {} municípios encontrados no banco", cidadesExistentesPorCodigoIbge.size());

            // ETAPA 4: Preparar TODAS as cidades em memória
            log.info("Preparando todas as cidades em memória...");
            List<Cidades> todasCidadesParaSalvar = new ArrayList<>();
            
            for (IbgeMunicipioDTO municipioIbge : todosMunicipiosIbge) {
                try {
                    String codigoIbgeStr = String.valueOf(municipioIbge.getId());
                    String siglaUf = ufPorMunicipio.get(codigoIbgeStr);
                    Estados estado = siglaUf != null ? estadosPorSigla.get(siglaUf) : null;
                    
                    if (estado == null) {
                        log.warn("Estado não encontrado para município {} (código IBGE: {})", municipioIbge.getNome(), codigoIbgeStr);
                        continue;
                    }
                    
                    Cidades cidade = prepararCidade(municipioIbge, estado, cidadesExistentesPorCodigoIbge);
                    todasCidadesParaSalvar.add(cidade);
                } catch (Exception e) {
                    log.error("Erro ao preparar município {}: {}", municipioIbge.getNome(), e.getMessage());
                    response.getMunicipiosErros().add("Município " + municipioIbge.getNome() + ": " + e.getMessage());
                }
            }
            log.info("Total de {} cidades preparadas em memória para salvar", todasCidadesParaSalvar.size());

            // ETAPA 5: Salvar em lotes maiores (sem transações individuais)
            log.info("Salvando cidades em lotes de {}...", BATCH_SIZE);
            int totalSincronizados = 0;
            
            for (int i = 0; i < todasCidadesParaSalvar.size(); i += BATCH_SIZE) {
                int fim = Math.min(i + BATCH_SIZE, todasCidadesParaSalvar.size());
                List<Cidades> lote = todasCidadesParaSalvar.subList(i, fim);
                
                try {
                    totalSincronizados += salvarLoteCidades(lote, response);
                    
                    // Log de progresso
                    if ((i + BATCH_SIZE) % (BATCH_SIZE * 10) == 0 || fim == todasCidadesParaSalvar.size()) {
                        log.info("Progresso: {}/{} municípios salvos", totalSincronizados, todasCidadesParaSalvar.size());
                    }
                    
                    // Pequena pausa entre lotes
                    if (fim < todasCidadesParaSalvar.size()) {
                        Thread.sleep(50);
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.warn("Sincronização de municípios interrompida");
                    break;
                } catch (Exception e) {
                    log.error("Erro ao salvar lote de municípios (índices {}-{}): {}", i, fim - 1, e.getMessage());
                    response.getMunicipiosErros().add("Lote " + (i / BATCH_SIZE + 1) + ": " + e.getMessage());
                }
            }

            response.setMunicipiosSincronizados(totalSincronizados);
            log.info("Total de municípios sincronizados: {}", totalSincronizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar municípios: {}", e.getMessage(), e);
            response.getMunicipiosErros().add("Erro geral: " + e.getMessage());
            throw new IbgeIntegrationException("Erro ao sincronizar municípios", e);
        }

        return response;
    }

    /**
     * Busca todas as cidades existentes no banco, indexadas por código IBGE
     */
    @Transactional(readOnly = true)
    private Map<String, Cidades> buscarCidadesExistentes() {
        List<Cidades> cidades = cidadesRepository.findAll();
        Map<String, Cidades> map = new HashMap<>();
        for (Cidades cidade : cidades) {
            if (cidade.getCodigoIbge() != null) {
                map.put(cidade.getCodigoIbge(), cidade);
            }
        }
        return map;
    }

    /**
     * Prepara uma cidade para salvar (sem salvar ainda)
     */
    private Cidades prepararCidade(IbgeMunicipioDTO municipioIbge, 
                                   Estados estado,
                                   Map<String, Cidades> cidadesPorCodigoIbge) {
        String codigoIbgeStr = String.valueOf(municipioIbge.getId());
        
        Cidades cidade;
        if (cidadesPorCodigoIbge.containsKey(codigoIbgeStr)) {
            cidade = cidadesPorCodigoIbge.get(codigoIbgeStr);
        } else {
            cidade = new Cidades();
        }

        // Atualiza campos IBGE
        cidade.setCodigoIbge(codigoIbgeStr);
        cidade.setNomeOficialIbge(municipioIbge.getNome());
        
        // Extrai UF do objeto aninhado
        if (municipioIbge.getMicrorregiao() != null &&
            municipioIbge.getMicrorregiao().getMesorregiao() != null &&
            municipioIbge.getMicrorregiao().getMesorregiao().getUf() != null) {
            cidade.setUfIbge(municipioIbge.getMicrorregiao().getMesorregiao().getUf().getSigla());
        }

        // Associa ao estado correto
        cidade.setEstado(estado);

        // Preserva campos locais se não existirem
        if (cidade.getNome() == null) {
            cidade.setNome(municipioIbge.getNome());
        }
        if (cidade.getActive() == null) {
            cidade.setActive(true);
        }

        cidade.setAtivoIbge(true);
        cidade.setDataUltimaSincronizacaoIbge(OffsetDateTime.now());

        return cidade;
    }

    /**
     * Salva um lote de cidades em uma única transação usando TransactionTemplate
     * Evita criar múltiplas transações que causam problemas com DataSource
     */
    private int salvarLoteCidades(List<Cidades> cidades, IbgeSincronizacaoResponse response) {
        return transactionTemplate.execute(status -> {
            int salvas = 0;
            try {
                for (Cidades cidade : cidades) {
                    try {
                        cidadesRepository.save(cidade);
                        salvas++;
                    } catch (Exception e) {
                        log.error("Erro ao salvar cidade {}: {}", cidade.getNome(), e.getMessage());
                        response.getMunicipiosErros().add("Município " + cidade.getNome() + ": " + e.getMessage());
                    }
                }
                
                // Flush e clear após salvar o lote para liberar memória do EntityManager
                entityManager.flush();
                entityManager.clear();
                
            } catch (Exception e) {
                log.error("Erro ao salvar lote de cidades: {}", e.getMessage());
                status.setRollbackOnly(); // Marca para rollback em caso de erro
            }
            return salvas;
        });
    }

    @Override
    // NÃO usar @Transactional aqui - processa cidade por cidade com transações separadas
    // Uma transação única para todas as cidades causaria timeout de conexão
    public IbgeSincronizacaoResponse atualizarPopulacao() {
        IbgeSincronizacaoResponse response = IbgeSincronizacaoResponse.builder().build();

        try {
            // Busca todas as cidades usando transação read-only para garantir que o EntityManager esteja disponível
            List<Cidades> cidades = buscarCidadesComTransacao();
            int atualizadas = 0;
            int processadas = 0;

            for (Cidades cidade : cidades) {
                if (cidade.getCodigoIbge() == null || cidade.getCodigoIbge().isEmpty()) {
                    continue;
                }

                try {
                    // Processa cada cidade em uma transação separada para evitar timeout
                    if (atualizarPopulacaoCidade(cidade, response)) {
                        atualizadas++;
                    }

                    processadas++;
                    // Log de progresso a cada 100 cidades
                    if (processadas % 100 == 0) {
                        log.debug("Processadas {} cidades, {} atualizadas...", processadas, atualizadas);
                    }

                } catch (Exception e) {
                    log.warn("Erro ao atualizar população do município {}: {}", cidade.getNome(), e.getMessage());
                    response.getPopulacaoErros().add("Município " + cidade.getNome() + ": " + e.getMessage());
                }
            }

            response.setPopulacaoAtualizada(atualizadas);
            log.info("População atualizada para {} municípios (de {} processados)", atualizadas, processadas);

        } catch (Exception e) {
            log.error("Erro ao atualizar população: {}", e.getMessage(), e);
            response.getPopulacaoErros().add("Erro geral: " + e.getMessage());
            throw new IbgeIntegrationException("Erro ao atualizar população", e);
        }

        return response;
    }

    /**
     * Busca todos os estados usando transação read-only
     */
    @Transactional(readOnly = true)
    private List<Estados> buscarEstadosComTransacao() {
        return estadosRepository.findAll();
    }

    /**
     * Busca todas as cidades usando transação read-only
     */
    @Transactional(readOnly = true)
    private List<Cidades> buscarCidadesComTransacao() {
        return cidadesRepository.findAll();
    }

    /**
     * Atualiza população de uma cidade em uma transação separada
     */
    @Transactional
    private boolean atualizarPopulacaoCidade(Cidades cidade, IbgeSincronizacaoResponse response) {
        try {
            IbgeProjecaoPopulacaoDTO projecao = ibgeClient.buscarProjecaoPopulacao(cidade.getCodigoIbge());
            
            if (projecao != null && projecao.getPopulacao() != null) {
                cidade.setPopulacaoEstimada(projecao.getPopulacao().intValue());
                cidadesRepository.save(cidade);
                return true;
            }
            return false;

        } catch (Exception e) {
            log.warn("Erro ao atualizar população do município {}: {}", cidade.getNome(), e.getMessage());
            response.getPopulacaoErros().add("Município " + cidade.getNome() + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public IbgeMunicipioDTO validarMunicipioPorCodigoIbge(String codigoIbge) {
        try {
            return ibgeClient.buscarMunicipioPorCodigoIbge(codigoIbge);
        } catch (Exception e) {
            log.error("Erro ao validar município por código IBGE {}: {}", codigoIbge, e.getMessage());
            throw new IbgeIntegrationException("Erro ao validar município por código IBGE: " + codigoIbge, e);
        }
    }
}

