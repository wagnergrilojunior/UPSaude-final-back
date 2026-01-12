package com.upsaude.integration.fhir.service.profissional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.entity.referencia.profissional.ConselhoProfissional;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.referencia.profissional.ConselhoProfissionalRepository;
import com.upsaude.repository.referencia.sigtap.SigtapOcupacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfissionalSyncService {

    private final FhirClient fhirClient;
    private final FhirSyncLogService syncLogService;

    private final SigtapOcupacaoRepository sigtapOcupacaoRepository;
    private final ConselhoProfissionalRepository conselhoRepository;

    private static final String RECURSO_CBO = "BRCBO";
    private static final String RECURSO_CONSELHO = "BRConselhoProfissional";

    @Transactional
    public FhirSyncLog sincronizarCBO() {
        log.info("Iniciando sincronização de CBO (Classificação Brasileira de Ocupações)");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(RECURSO_CBO, null);
        int novos = 0;
        int atualizados = 0;

        try {
            List<ConceptDTO> concepts = fetchConcepts(RECURSO_CBO);
            log.info("Total de ocupações CBO encontradas: {}", concepts.size());

            for (ConceptDTO concept : concepts) {
                // Tentar encontrar por código CBO completo ou código oficial
                SigtapOcupacao ocupacao = sigtapOcupacaoRepository.findByCodigoCbo(concept.getCode())
                        .or(() -> sigtapOcupacaoRepository
                                .findByCodigo(concept.getCode().substring(0, Math.min(6, concept.getCode().length()))))
                        .orElse(new SigtapOcupacao());

                boolean isNew = ocupacao.getId() == null;

                // Preencher campos básicos
                if (concept.getCode().length() <= 6) {
                    ocupacao.setCodigoOficial(concept.getCode());
                    ocupacao.setCodigoCboCompleto(concept.getCode());
                } else {
                    ocupacao.setCodigoOficial(concept.getCode().substring(0, 6));
                    ocupacao.setCodigoCboCompleto(concept.getCode());
                }

                ocupacao.setNome(concept.getDisplay());
                ocupacao.setDescricaoFhir(concept.getDefinition());

                // Extrair hierarquia do CBO se disponível nas properties
                if (concept.getProperty() != null) {
                    concept.getProperty().forEach(prop -> {
                        switch (prop.getCode().toLowerCase()) {
                            case "grandegrupo":
                                ocupacao.setGrandeGrupo(prop.getValueString());
                                break;
                            case "subgrupoprincipal":
                                ocupacao.setSubgrupoPrincipal(prop.getValueString());
                                break;
                            case "subgrupo":
                                ocupacao.setSubgrupo(prop.getValueString());
                                break;
                            case "familia":
                                ocupacao.setFamilia(prop.getValueString());
                                break;
                        }
                    });
                }

                sigtapOcupacaoRepository.save(ocupacao);

                if (isNew) {
                    novos++;
                } else {
                    atualizados++;
                }
            }

            log.info("Sincronização CBO concluída. Novos: {}, Atualizados: {}", novos, atualizados);
            return syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar CBO", e);
            return syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
        }
    }

    @Transactional
    public FhirSyncLog sincronizarConselhos() {
        log.info("Iniciando sincronização de Conselhos Profissionais");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(RECURSO_CONSELHO, null);
        int novos = 0;
        int atualizados = 0;

        try {
            List<ConceptDTO> concepts = fetchConcepts(RECURSO_CONSELHO);
            log.info("Total de conselhos encontrados: {}", concepts.size());

            for (ConceptDTO concept : concepts) {
                ConselhoProfissional conselho = conselhoRepository.findByCodigo(concept.getCode())
                        .orElse(ConselhoProfissional.builder()
                                .codigo(concept.getCode())
                                .build());

                boolean isNew = conselho.getId() == null;

                conselho.setNome(concept.getDisplay());
                conselho.setDescricao(concept.getDefinition());

                // Extrair sigla e UF do código ou properties
                if (concept.getProperty() != null) {
                    concept.getProperty().forEach(prop -> {
                        switch (prop.getCode().toLowerCase()) {
                            case "sigla":
                                conselho.setSigla(prop.getValueString());
                                break;
                            case "uf":
                                conselho.setUf(prop.getValueString());
                                break;
                            case "tipo":
                                conselho.setTipo(prop.getValueString());
                                break;
                        }
                    });
                }

                // Se não tiver sigla nas properties, tentar extrair do código
                if (conselho.getSigla() == null && concept.getCode().contains("-")) {
                    String[] parts = concept.getCode().split("-");
                    if (parts.length >= 1) {
                        conselho.setSigla(parts[0]);
                    }
                    if (parts.length >= 2) {
                        conselho.setUf(parts[1]);
                    }
                }

                conselhoRepository.save(conselho);

                if (isNew) {
                    novos++;
                } else {
                    atualizados++;
                }
            }

            log.info("Sincronização Conselhos concluída. Novos: {}, Atualizados: {}", novos, atualizados);
            return syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar Conselhos", e);
            return syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
        }
    }

    @Transactional
    public List<FhirSyncLog> sincronizarTodos() {
        log.info("Iniciando sincronização completa de dados de Profissionais");
        List<FhirSyncLog> logs = new ArrayList<>();

        logs.add(sincronizarCBO());
        logs.add(sincronizarConselhos());

        return logs;
    }

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Tentando buscar conceitos para recurso: {}", recurso);

        // 1. Tentar buscar ValueSet
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
}
