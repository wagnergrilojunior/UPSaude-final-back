package com.upsaude.integration.fhir.service.farmacia;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.farmacia.PrincipioAtivo;
import com.upsaude.entity.farmacia.Medicamento;
import com.upsaude.entity.farmacia.UnidadeMedida;
import com.upsaude.entity.vacinacao.ViaAdministracao;
import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.config.FhirResourceNames;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.farmacia.PrincipioAtivoRepository;
import com.upsaude.repository.farmacia.MedicamentoRepository;
import com.upsaude.repository.farmacia.UnidadeMedidaRepository;
import com.upsaude.repository.vacinacao.ViaAdministracaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicamentoSyncService {

    private final FhirClient fhirClient;
    private final FhirSyncLogService syncLogService;

    private final PrincipioAtivoRepository principioAtivoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UnidadeMedidaRepository unidadeMedidaRepository;
    private final ViaAdministracaoRepository viaAdministracaoRepository;

    private static final String RECURSO_VTM = FhirResourceNames.OBM_VTM;
    private static final String RECURSO_MEDICAMENTO = FhirResourceNames.MEDICAMENTO;
    private static final String RECURSO_UNIDADE = FhirResourceNames.UNIDADE_MEDIDA;
    private static final String RECURSO_VIA = FhirResourceNames.VIA_ADMINISTRACAO;

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Tentando buscar conceitos para recurso: {}", recurso);
        List<ConceptDTO> concepts = new ArrayList<>();

        // 1. Tentar buscar ValueSet (padrão principal)
        try {
            ValueSetDTO valueSet = fhirClient.getValueSet(recurso);
            if (valueSet != null && valueSet.getCompose() != null && valueSet.getCompose().getInclude() != null) {
                for (ValueSetDTO.IncludeDTO include : valueSet.getCompose().getInclude()) {
                    if (include.getConcept() != null && !include.getConcept().isEmpty()) {
                        for (ConceptReferenceDTO ref : include.getConcept()) {
                            concepts.add(ConceptDTO.builder()
                                    .code(ref.getCode())
                                    .display(ref.getDisplay())
                                    .definition(ref.getDisplay())
                                    .build());
                        }
                    } else if (include.getSystem() != null) {
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

        // 2. Se não encontrou nada via ValueSet, tenta CodeSystem direto
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
    public List<SyncResult> sincronizarTudo(UUID usuarioId) {
        List<SyncResult> results = new ArrayList<>();
        results.add(sincronizarPrincipiosAtivos(usuarioId));
        results.add(sincronizarUnidadesMedida(usuarioId));
        results.add(sincronizarViasAdministracao(usuarioId));
        results.add(sincronizarMedicamentos(usuarioId));
        return results;
    }

    @Transactional
    public SyncResult sincronizarPrincipiosAtivos(UUID usuarioId) {
        String recurso = RECURSO_VTM;
        log.info("Sincronizando Princípios Ativos (VTM)...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                PrincipioAtivo entity = principioAtivoRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new PrincipioAtivo());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                principioAtivoRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);
        } catch (Exception e) {
            log.error("Erro na sincronização de Princípios Ativos: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarUnidadesMedida(UUID usuarioId) {
        String recurso = RECURSO_UNIDADE;
        log.info("Sincronizando Unidades de Medida...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                UnidadeMedida entity = unidadeMedidaRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new UnidadeMedida());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setSigla(concept.getCode());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                unidadeMedidaRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);
        } catch (Exception e) {
            log.error("Erro na sincronização de Unidades de Medida: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarViasAdministracao(UUID usuarioId) {
        String recurso = RECURSO_VIA;
        log.info("Sincronizando Vias de Administração...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                ViaAdministracao entity = viaAdministracaoRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new ViaAdministracao());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setDescricao(concept.getDefinition());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                viaAdministracaoRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);
        } catch (Exception e) {
            log.error("Erro na sincronização de Vias de Administração: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    @Transactional
    public SyncResult sincronizarMedicamentos(UUID usuarioId) {
        String recurso = RECURSO_MEDICAMENTO;
        log.info("Sincronizando Medicamentos (BRMedicamento)...");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao(recurso, usuarioId);

        try {
            List<ConceptDTO> concepts = fetchConcepts(recurso);
            int novos = 0;
            int atualizados = 0;

            for (ConceptDTO concept : concepts) {
                Medicamento entity = medicamentoRepository.findByCodigoFhir(concept.getCode())
                        .orElse(new Medicamento());

                boolean isNew = entity.getId() == null;
                entity.setCodigoFhir(concept.getCode());
                entity.setNome(concept.getDisplay());
                entity.setAtivo(true);
                entity.setDataSincronizacao(OffsetDateTime.now());

                medicamentoRepository.save(entity);
                if (isNew)
                    novos++;
                else
                    atualizados++;
            }

            syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), novos, atualizados);
            return new SyncResult(recurso, concepts.size(), novos, atualizados);
        } catch (Exception e) {
            log.error("Erro na sincronização de Medicamentos: {}", e.getMessage());
            syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
            throw e;
        }
    }

    public record SyncResult(String recurso, int totalEncontrados, int novosInseridos, int atualizados) {
    }
}
