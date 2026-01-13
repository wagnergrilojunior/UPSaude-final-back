package com.upsaude.integration.fhir.service.exame;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.entity.referencia.exame.CatalogoExame;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.config.FhirResourceNames;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.referencia.exame.CatalogoExameRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExameSyncService {

    private final FhirClient fhirClient;
    private final FhirSyncLogService syncLogService;
    private final CatalogoExameRepository catalogoExameRepository;

    private static final String SOURCE_LOINC = "LOINC";
    private static final String SOURCE_GAL = "GAL";

    private static final String RECURSO_LOINC = FhirResourceNames.NOME_EXAME_LOINC;
    private static final String RECURSO_GAL = FhirResourceNames.NOME_EXAME_GAL;

    @Transactional
    public FhirSyncLog sincronizarExamesLoinc() {
        log.info("Iniciando sincronização de Exames LOINC");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(RECURSO_LOINC, null);
        int novos = 0;
        int atualizados = 0;

        try {
            List<ConceptDTO> concepts = fetchConcepts(RECURSO_LOINC);
            log.info("Total de conceitos LOINC encontrados: {}", concepts.size());

            for (ConceptDTO concept : concepts) {
                CatalogoExame exame = catalogoExameRepository.findBySourceAndCode(SOURCE_LOINC, concept.getCode())
                        .orElse(CatalogoExame.builder()
                                .sourceSystem(SOURCE_LOINC)
                                .externalCode(concept.getCode())
                                .build());

                boolean isNew = exame.getId() == null;

                exame.setNome(concept.getDisplay());
                exame.setDescricao(concept.getDefinition());
                exame.setCodigoLoinc(concept.getCode());
                exame.setFhirCodeSystem(RECURSO_LOINC);
                exame.setLastSyncAt(OffsetDateTime.now());

                if (concept.getProperty() != null) {
                    concept.getProperty().stream()
                            .filter(p -> "category".equalsIgnoreCase(p.getCode()))
                            .findFirst()
                            .ifPresent(p -> exame.setCategoria(p.getValueString()));
                }

                catalogoExameRepository.save(exame);

                if (isNew) {
                    novos++;
                } else {
                    atualizados++;
                }
            }

            log.info("Sincronização LOINC concluída. Novos: {}, Atualizados: {}", novos, atualizados);
            return syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar Exames LOINC", e);
            return syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
        }
    }

    @Transactional
    public FhirSyncLog sincronizarExamesGal() {
        log.info("Iniciando sincronização de Exames GAL");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(RECURSO_GAL, null);
        int novos = 0;
        int atualizados = 0;

        try {
            List<ConceptDTO> concepts = fetchConcepts(RECURSO_GAL);
            log.info("Total de conceitos GAL encontrados: {}", concepts.size());

            for (ConceptDTO concept : concepts) {
                CatalogoExame exame = catalogoExameRepository.findBySourceAndCode(SOURCE_GAL, concept.getCode())
                        .orElse(CatalogoExame.builder()
                                .sourceSystem(SOURCE_GAL)
                                .externalCode(concept.getCode())
                                .build());

                boolean isNew = exame.getId() == null;

                exame.setNome(concept.getDisplay());
                exame.setDescricao(concept.getDefinition());
                exame.setCodigoGal(concept.getCode());
                exame.setFhirCodeSystem(RECURSO_GAL);
                exame.setLastSyncAt(OffsetDateTime.now());

                catalogoExameRepository.save(exame);

                if (isNew) {
                    novos++;
                } else {
                    atualizados++;
                }
            }

            log.info("Sincronização GAL concluída. Novos: {}, Atualizados: {}", novos, atualizados);
            return syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar Exames GAL", e);
            return syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
        }
    }

    @Transactional
    public List<FhirSyncLog> sincronizarTodos() {
        log.info("Iniciando sincronização completa de Exames");
        List<FhirSyncLog> logs = new ArrayList<>();

        logs.add(sincronizarExamesLoinc());
        logs.add(sincronizarExamesGal());

        return logs;
    }

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Tentando buscar conceitos para recurso: {}", recurso);

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
                                    .definition(ref.getDisplay())
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
}
