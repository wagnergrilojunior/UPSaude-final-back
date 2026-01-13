package com.upsaude.integration.fhir.service.vacinacao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.entity.vacinacao.EstrategiaVacinacao;
import com.upsaude.entity.vacinacao.FabricanteImunobiologico;
import com.upsaude.entity.vacinacao.Imunobiologico;
import com.upsaude.entity.vacinacao.LocalAplicacao;
import com.upsaude.entity.vacinacao.TipoDose;
import com.upsaude.entity.vacinacao.ViaAdministracao;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.config.FhirResourceNames;
import com.upsaude.integration.fhir.service.FhirCacheService;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.vacinacao.EstrategiaVacinacaoRepository;
import com.upsaude.repository.vacinacao.FabricanteImunobiologicoRepository;
import com.upsaude.repository.vacinacao.ImunobiologicoRepository;
import com.upsaude.repository.vacinacao.LocalAplicacaoRepository;
import com.upsaude.repository.vacinacao.TipoDoseRepository;
import com.upsaude.repository.vacinacao.ViaAdministracaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacinacaoSyncService {

    private final FhirClient fhirClient;
    private final FhirCacheService cacheService;
    private final FhirSyncLogService syncLogService;

    private final ImunobiologicoRepository imunobiologicoRepository;
    private final FabricanteImunobiologicoRepository fabricanteRepository;
    private final TipoDoseRepository tipoDoseRepository;
    private final LocalAplicacaoRepository localAplicacaoRepository;
    private final ViaAdministracaoRepository viaAdministracaoRepository;
    private final EstrategiaVacinacaoRepository estrategiaRepository;

    private static final String RECURSO_IMUNOBIOLOGICO = FhirResourceNames.IMUNOBIOLOGICO;
    private static final String RECURSO_FABRICANTE = FhirResourceNames.FABRICANTE_PNI;
    private static final String RECURSO_DOSE = FhirResourceNames.DOSE;
    private static final String RECURSO_LOCAL = FhirResourceNames.LOCAL_APLICACAO;
    private static final String RECURSO_VIA = FhirResourceNames.VIA_ADMINISTRACAO;
    private static final String RECURSO_ESTRATEGIA = FhirResourceNames.ESTRATEGIA_VACINACAO;

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Tentando buscar conceitos para recurso: {}", recurso);

        // 1. Tentar buscar ValueSet (mais comum ter a lista completa aqui no padrão MS)
        try {
            ValueSetDTO valueSet = fhirClient.getValueSet(recurso);
            List<ConceptDTO> concepts = new ArrayList<>();

            if (valueSet.getCompose() != null && valueSet.getCompose().getInclude() != null) {
                valueSet.getCompose().getInclude().forEach(include -> {
                    if (include.getConcept() != null) {
                        for (ConceptReferenceDTO ref : include.getConcept()) {
                            concepts.add(ConceptDTO.builder()
                                    .code(ref.getCode())
                                    .display(ref.getDisplay())
                                    .definition(ref.getDisplay()) // ValueSet geralmente não tem definition, usamos
                                                                  // display
                                    .build());
                        }
                    }
                });
            }

            if (!concepts.isEmpty()) {
                log.info("Conceitos encontrados via ValueSet: {}", concepts.size());
                return concepts;
            }
        } catch (Exception e) {
            log.warn("Falha ao buscar ValueSet para {}, tentando CodeSystem. Erro: {}", recurso, e.getMessage());
        }

        // 2. Fallback para CodeSystem
        try {
            CodeSystemDTO codeSystem = fhirClient.getCodeSystem(recurso);
            if (codeSystem.getConcept() != null && !codeSystem.getConcept().isEmpty()) {
                log.info("Conceitos encontrados via CodeSystem: {}", codeSystem.getConcept().size());
                return codeSystem.getConcept();
            }
        } catch (Exception e) {
            log.warn("Falha ao buscar CodeSystem para {}: {}", recurso, e.getMessage());
        }

        return new ArrayList<>();
    }

    // --- Métodos de Consulta Externa (Live FHIR) ---

    public List<ConceptDTO> consultarImunobiologicosExternos() {
        return fetchConcepts(RECURSO_IMUNOBIOLOGICO);
    }

    public List<ConceptDTO> consultarFabricantesExternos() {
        return fetchConcepts(RECURSO_FABRICANTE);
    }

    public List<ConceptDTO> consultarTiposDoseExternos() {
        return fetchConcepts(RECURSO_DOSE);
    }

    public List<ConceptDTO> consultarLocaisAplicacaoExternos() {
        return fetchConcepts(RECURSO_LOCAL);
    }

    public List<ConceptDTO> consultarViasAdministracaoExternos() {
        return fetchConcepts(RECURSO_VIA);
    }

    public List<ConceptDTO> consultarEstrategiasExternos() {
        return fetchConcepts(RECURSO_ESTRATEGIA);
    }

    @Transactional
    public SyncResult sincronizarImunobiologicos(UUID usuarioId) {
        String recurso = RECURSO_IMUNOBIOLOGICO;
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);

            if (concepts == null || concepts.isEmpty()) {
                syncLogService.concluirSincronizacao(syncLog.getId(), 0, 0, 0);
                return new SyncResult(recurso, 0, 0, 0);
            }

            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                Imunobiologico existing = imunobiologicoRepository.findByCodigoFhir(concept.getCode()).orElse(null);

                if (existing == null) {
                    Imunobiologico novo = Imunobiologico.builder()
                            .codigoFhir(concept.getCode())
                            .nome(concept.getDisplay())
                            .descricao(concept.getDefinition())
                            .codigoSistema(recurso) // Usando o nome do recurso como sistema
                            .dataSincronizacao(OffsetDateTime.now())
                            .build();
                    imunobiologicoRepository.save(novo);
                    novos++;
                } else {
                    existing.setNome(concept.getDisplay());
                    existing.setDescricao(concept.getDefinition());
                    existing.setDataSincronizacao(OffsetDateTime.now());
                    imunobiologicoRepository.save(existing);
                    atualizados++;
                }
            }

            cacheService.evictByPattern("imunobiologico");
            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

            log.info("Sincronização de imunobiológicos concluída: {} novos, {} atualizados", novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de imunobiológicos: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarFabricantes(UUID usuarioId) {
        String recurso = RECURSO_FABRICANTE;
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);

            if (concepts == null || concepts.isEmpty()) {
                syncLogService.concluirSincronizacao(syncLog.getId(), 0, 0, 0);
                return new SyncResult(recurso, 0, 0, 0);
            }

            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                FabricanteImunobiologico existing = fabricanteRepository.findByCodigoFhir(concept.getCode())
                        .orElse(null);

                if (existing == null) {
                    FabricanteImunobiologico novo = FabricanteImunobiologico.builder()
                            .codigoFhir(concept.getCode())
                            .nome(concept.getDisplay())
                            .dataSincronizacao(OffsetDateTime.now())
                            .build();
                    fabricanteRepository.save(novo);
                    novos++;
                } else {
                    existing.setNome(concept.getDisplay());
                    existing.setDataSincronizacao(OffsetDateTime.now());
                    fabricanteRepository.save(existing);
                    atualizados++;
                }
            }

            cacheService.evictByPattern("fabricante");
            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

            log.info("Sincronização de fabricantes concluída: {} novos, {} atualizados", novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de fabricantes: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarTiposDose(UUID usuarioId) {
        String recurso = RECURSO_DOSE;
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);

            if (concepts == null || concepts.isEmpty()) {
                syncLogService.concluirSincronizacao(syncLog.getId(), 0, 0, 0);
                return new SyncResult(recurso, 0, 0, 0);
            }

            int novos = 0;
            int atualizados = 0;
            int ordem = 1;

            for (ConceptDTO concept : concepts) {
                TipoDose existing = tipoDoseRepository.findByCodigoFhir(concept.getCode()).orElse(null);

                if (existing == null) {
                    TipoDose novo = TipoDose.builder()
                            .codigoFhir(concept.getCode())
                            .nome(concept.getDisplay())
                            .descricao(concept.getDefinition())
                            .ordemSequencia(ordem++)
                            .dataSincronizacao(OffsetDateTime.now())
                            .build();
                    tipoDoseRepository.save(novo);
                    novos++;
                } else {
                    existing.setNome(concept.getDisplay());
                    existing.setDescricao(concept.getDefinition());
                    existing.setDataSincronizacao(OffsetDateTime.now());
                    tipoDoseRepository.save(existing);
                    atualizados++;
                    ordem++;
                }
            }

            cacheService.evictByPattern("dose");
            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

            log.info("Sincronização de tipos de dose concluída: {} novos, {} atualizados", novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de tipos de dose: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarLocaisAplicacao(UUID usuarioId) {
        String recurso = RECURSO_LOCAL;
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);

            if (concepts == null || concepts.isEmpty()) {
                syncLogService.concluirSincronizacao(syncLog.getId(), 0, 0, 0);
                return new SyncResult(recurso, 0, 0, 0);
            }

            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                LocalAplicacao existing = localAplicacaoRepository.findByCodigoFhir(concept.getCode()).orElse(null);

                if (existing == null) {
                    LocalAplicacao novo = LocalAplicacao.builder()
                            .codigoFhir(concept.getCode())
                            .nome(concept.getDisplay())
                            .descricao(concept.getDefinition())
                            .dataSincronizacao(OffsetDateTime.now())
                            .build();
                    localAplicacaoRepository.save(novo);
                    novos++;
                } else {
                    existing.setNome(concept.getDisplay());
                    existing.setDescricao(concept.getDefinition());
                    existing.setDataSincronizacao(OffsetDateTime.now());
                    localAplicacaoRepository.save(existing);
                    atualizados++;
                }
            }

            cacheService.evictByPattern("local");
            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

            log.info("Sincronização de locais de aplicação concluída: {} novos, {} atualizados", novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de locais de aplicação: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarViasAdministracao(UUID usuarioId) {
        String recurso = RECURSO_VIA;
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);

            if (concepts == null || concepts.isEmpty()) {
                syncLogService.concluirSincronizacao(syncLog.getId(), 0, 0, 0);
                return new SyncResult(recurso, 0, 0, 0);
            }

            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                ViaAdministracao existing = viaAdministracaoRepository.findByCodigoFhir(concept.getCode()).orElse(null);

                if (existing == null) {
                    ViaAdministracao novo = ViaAdministracao.builder()
                            .codigoFhir(concept.getCode())
                            .nome(concept.getDisplay())
                            .descricao(concept.getDefinition())
                            .dataSincronizacao(OffsetDateTime.now())
                            .build();
                    viaAdministracaoRepository.save(novo);
                    novos++;
                } else {
                    existing.setNome(concept.getDisplay());
                    existing.setDescricao(concept.getDefinition());
                    existing.setDataSincronizacao(OffsetDateTime.now());
                    viaAdministracaoRepository.save(existing);
                    atualizados++;
                }
            }

            cacheService.evictByPattern("via");
            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

            log.info("Sincronização de vias de administração concluída: {} novos, {} atualizados", novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de vias de administração: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarEstrategias(UUID usuarioId) {
        String recurso = RECURSO_ESTRATEGIA;
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);

            if (concepts == null || concepts.isEmpty()) {
                syncLogService.concluirSincronizacao(syncLog.getId(), 0, 0, 0);
                return new SyncResult(recurso, 0, 0, 0);
            }

            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                EstrategiaVacinacao existing = estrategiaRepository.findByCodigoFhir(concept.getCode()).orElse(null);

                if (existing == null) {
                    EstrategiaVacinacao novo = EstrategiaVacinacao.builder()
                            .codigoFhir(concept.getCode())
                            .nome(concept.getDisplay())
                            .descricao(concept.getDefinition())
                            .dataSincronizacao(OffsetDateTime.now())
                            .build();
                    estrategiaRepository.save(novo);
                    novos++;
                } else {
                    existing.setNome(concept.getDisplay());
                    existing.setDescricao(concept.getDefinition());
                    existing.setDataSincronizacao(OffsetDateTime.now());
                    estrategiaRepository.save(existing);
                    atualizados++;
                }
            }

            cacheService.evictByPattern("estrategia");
            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

            log.info("Sincronização de estratégias de vacinação concluída: {} novos, {} atualizados", novos,
                    atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de estratégias: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    public record SyncResult(String recurso, int totalEncontrados, int novosInseridos, int atualizados) {
    }
}
