package com.upsaude.integration.fhir.service.alergia;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.alergia.Alergeno;
import com.upsaude.entity.alergia.CategoriaAgenteAlergia;
import com.upsaude.entity.alergia.CriticidadeAlergia;
import com.upsaude.entity.alergia.ReacaoAdversaCatalogo;
import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.config.FhirResourceNames;
import com.upsaude.integration.fhir.service.FhirCacheService;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.alergia.AlergenoRepository;
import com.upsaude.repository.alergia.CategoriaAgenteAlergiaRepository;
import com.upsaude.repository.alergia.CriticidadeAlergiaRepository;
import com.upsaude.repository.alergia.ReacaoAdversaCatalogoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlergiaSyncService {

    private final FhirClient fhirClient;
    private final FhirCacheService cacheService;
    private final FhirSyncLogService syncLogService;

    private final AlergenoRepository alergenoRepository;
    private final ReacaoAdversaCatalogoRepository reacaoRepository;
    private final CriticidadeAlergiaRepository criticidadeRepository;
    private final CategoriaAgenteAlergiaRepository categoriaRepository;

    private static final String RECURSO_ALERGENO = FhirResourceNames.ALERGENOS;
    private static final String RECURSO_REACAO = FhirResourceNames.REACOES_ADVERSAS_MED_DRA;
    private static final String RECURSO_CRITICIDADE = FhirResourceNames.CRITICIDADE_ALERGIAS;
    private static final String RECURSO_CATEGORIA = FhirResourceNames.CATEGORIA_AGENTE_ALERGIAS;

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Tentando buscar conceitos para recurso: {}", recurso);
        List<ConceptDTO> concepts = new ArrayList<>();

        // 1. Tentar buscar ValueSet (padrão principal)
        try {
            ValueSetDTO valueSet = fhirClient.getValueSet(recurso);
            if (valueSet != null && valueSet.getCompose() != null && valueSet.getCompose().getInclude() != null) {
                for (ValueSetDTO.IncludeDTO include : valueSet.getCompose().getInclude()) {
                    if (include.getConcept() != null && !include.getConcept().isEmpty()) {
                        // Inclusão explícita de códigos
                        for (ConceptReferenceDTO ref : include.getConcept()) {
                            concepts.add(ConceptDTO.builder()
                                    .code(ref.getCode())
                                    .display(ref.getDisplay())
                                    .definition(ref.getDisplay())
                                    .build());
                        }
                    } else if (include.getSystem() != null) {
                        // Inclusão de um sistema inteiro (caso do BRAlergenos -> BRAlergenosCBARA)
                        log.info("ValueSet {} inclui sistema inteiro: {}. Buscando CodeSystem...", recurso,
                                include.getSystem());
                        String systemName = extractNameFromSystem(include.getSystem());
                        List<ConceptDTO> systemConcepts = fetchCodeSystemConcepts(systemName);
                        if (!systemConcepts.isEmpty()) {
                            concepts.addAll(systemConcepts);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Falha ao buscar ValueSet {}: {}", recurso, e.getMessage());
        }

        // 2. Se não encontrou nada via ValueSet, tenta CodeSystem direto pelo nome do
        // recurso
        if (concepts.isEmpty()) {
            concepts = fetchCodeSystemConcepts(recurso);
        }

        log.info("Total de conceitos encontrados para {}: {}", recurso, concepts.size());
        return concepts;
    }

    private String extractNameFromSystem(String system) {
        if (system == null)
            return null;
        if (system.contains("/")) {
            return system.substring(system.lastIndexOf("/") + 1);
        }
        return system;
    }

    private List<ConceptDTO> fetchCodeSystemConcepts(String name) {
        try {
            CodeSystemDTO codeSystem = fhirClient.getCodeSystem(name);
            if (codeSystem != null && codeSystem.getConcept() != null && !codeSystem.getConcept().isEmpty()) {
                log.info("Conceitos encontrados via CodeSystem {}: {}", name, codeSystem.getConcept().size());
                return codeSystem.getConcept();
            }
        } catch (Exception e) {
            log.warn("Falha ao buscar CodeSystem para {}: {}", name, e.getMessage());
        }
        return new ArrayList<>();
    }

    @Transactional
    public List<SyncResult> sincronizarTodos(UUID usuarioId) {
        log.info("Iniciando sincronização completa do módulo de Alergias");
        List<SyncResult> results = new ArrayList<>();
        results.add(sincronizarAlergenos(usuarioId));
        results.add(sincronizarReacoesAdversas(usuarioId));
        results.add(sincronizarCriticidade(usuarioId));
        results.add(sincronizarCategoriasAgente(usuarioId));
        log.info("Sincronização completa do módulo de Alergias finalizada");
        return results;
    }

    @Transactional
    public SyncResult sincronizarAlergenos(UUID usuarioId) {
        String recurso = RECURSO_ALERGENO;
        log.info("Sincronizando Alérgenos (CBARA)...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                Alergeno entity = alergenoRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new Alergeno());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setFonte("FHIR_CBARA");
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                alergenoRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de Alérgenos: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarReacoesAdversas(UUID usuarioId) {
        String recurso = RECURSO_REACAO;
        log.info("Sincronizando Reações Adversas (MedDRA)...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                ReacaoAdversaCatalogo entity = reacaoRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new ReacaoAdversaCatalogo());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                reacaoRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de Reações Adversas: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarCriticidade(UUID usuarioId) {
        String recurso = RECURSO_CRITICIDADE;
        log.info("Sincronizando Níveis de Criticidade...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                CriticidadeAlergia entity = criticidadeRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new CriticidadeAlergia());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setDescricao(concept.getDefinition());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                criticidadeRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de Criticidade: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarCategoriasAgente(UUID usuarioId) {
        String recurso = RECURSO_CATEGORIA;
        log.info("Sincronizando Categorias de Agente...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                CategoriaAgenteAlergia entity = categoriaRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new CategoriaAgenteAlergia());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setDescricao(concept.getDefinition());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                categoriaRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro na sincronização de Categorias de Agente: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    public List<ConceptDTO> consultarAlergenosExternos() {
        return fetchConcepts(RECURSO_ALERGENO);
    }

    public List<ConceptDTO> consultarReacoesAdversasExternas() {
        return fetchConcepts(RECURSO_REACAO);
    }

    public record SyncResult(String recurso, int totalEncontrados, int novosInseridos, int atualizados) {
    }
}
