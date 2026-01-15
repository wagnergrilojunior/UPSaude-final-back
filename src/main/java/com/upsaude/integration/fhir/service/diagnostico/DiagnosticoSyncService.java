package com.upsaude.integration.fhir.service.diagnostico;

import com.upsaude.entity.referencia.Ciap2;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.config.FhirResourceNames;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.referencia.Ciap2Repository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosticoSyncService {

    private static final int BATCH_SIZE = 500;

    private final FhirClient fhirClient;
    private final FhirSyncLogService syncLogService;

    private final Cid10SubcategoriasRepository cid10Repository;
    private final Ciap2Repository ciap2Repository;

    public record SyncResult(String recurso, int processados, int criados, int atualizados, int falhas) {
    }

    @Transactional
    public SyncResult syncCid10() {
        log.info("Iniciando sincronização de CID-10 via FHIR ({})", FhirResourceNames.CID10);
        FhirSyncLog logEntry = syncLogService.iniciarSincronizacao(FhirResourceNames.CID10, null);

        List<ConceptDTO> concepts = fetchConcepts(FhirResourceNames.CID10);
        log.info("Total de conceitos CID-10 a processar: {}", concepts.size());

        int criados = 0;
        int atualizados = 0;
        int falhas = 0;

        // Processar em lotes para melhor performance
        for (int i = 0; i < concepts.size(); i += BATCH_SIZE) {
            int fim = Math.min(i + BATCH_SIZE, concepts.size());
            List<ConceptDTO> batch = concepts.subList(i, fim);

            try {
                // Buscar existentes em batch
                List<String> codigos = batch.stream().map(ConceptDTO::getCode).collect(Collectors.toList());
                Map<String, Cid10Subcategorias> existentes = cid10Repository.findBySubcatIn(codigos).stream()
                        .collect(Collectors.toMap(Cid10Subcategorias::getSubcat, cid -> cid));

                List<Cid10Subcategorias> toSave = new ArrayList<>();
                for (ConceptDTO concept : batch) {
                    try {
                        Cid10Subcategorias cid = existentes.getOrDefault(concept.getCode(), new Cid10Subcategorias());
                        boolean isNew = cid.getId() == null;

                        cid.setSubcat(concept.getCode());
                        cid.setDescricao(concept.getDisplay());
                        cid.setActive(true);

                        toSave.add(cid);
                        if (isNew)
                            criados++;
                        else
                            atualizados++;
                    } catch (Exception e) {
                        log.error("Erro ao processar CID {}: {}", concept.getCode(), e.getMessage());
                        falhas++;
                    }
                }

                // Salvar batch
                if (!toSave.isEmpty()) {
                    cid10Repository.saveAll(toSave);
                    log.debug("Batch CID-10 processado: {}-{} ({} itens)", i + 1, fim, toSave.size());
                }
            } catch (Exception e) {
                log.error("Erro ao processar batch CID-10 ({}-{}): {}", i + 1, fim, e.getMessage());
                falhas += batch.size();
            }
        }

        String mensagemErro = falhas > 0 ? "Alguns itens falharam na sincronização" : null;
        syncLogService.concluirSincronizacao(logEntry.getId(), concepts.size(), criados, atualizados, falhas,
                mensagemErro);

        log.info("Sincronização CID-10 concluída: {} processados, {} criados, {} atualizados, {} falhas",
                concepts.size(), criados, atualizados, falhas);
        return new SyncResult(FhirResourceNames.CID10, concepts.size(), criados, atualizados, falhas);
    }

    @Transactional
    public SyncResult syncCiap2() {
        log.info("Iniciando sincronização de CIAP-2 via FHIR ({})", FhirResourceNames.CIAP2);
        FhirSyncLog logEntry = syncLogService.iniciarSincronizacao(FhirResourceNames.CIAP2, null);

        List<ConceptDTO> concepts = fetchConcepts(FhirResourceNames.CIAP2);
        log.info("Total de conceitos CIAP-2 a processar: {}", concepts.size());

        int criados = 0;
        int atualizados = 0;
        int falhas = 0;

        // Processar em lotes para melhor performance
        for (int i = 0; i < concepts.size(); i += BATCH_SIZE) {
            int fim = Math.min(i + BATCH_SIZE, concepts.size());
            List<ConceptDTO> batch = concepts.subList(i, fim);

            try {
                // Buscar existentes individualmente (CIAP2 não tem findByCodigoIn ainda)
                // Para CIAP2, manter busca individual mas salvar em batch
                List<Ciap2> toSave = new ArrayList<>();
                for (ConceptDTO concept : batch) {
                    try {
                        Ciap2 ciap = ciap2Repository.findByCodigo(concept.getCode())
                                .orElse(new Ciap2());
                        boolean isNew = ciap.getId() == null;

                        ciap.setCodigo(concept.getCode());
                        ciap.setDescricao(concept.getDisplay());

                        if (concept.getCode().length() > 0 && Character.isLetter(concept.getCode().charAt(0))) {
                            ciap.setCapitulo(String.valueOf(concept.getCode().charAt(0)));
                        }

                        ciap.setAtivo(true);
                        ciap.setDataSincronizacao(OffsetDateTime.now());

                        toSave.add(ciap);
                        if (isNew)
                            criados++;
                        else
                            atualizados++;
                    } catch (Exception e) {
                        log.error("Erro ao processar CIAP {}: {}", concept.getCode(), e.getMessage());
                        falhas++;
                    }
                }

                // Salvar batch
                if (!toSave.isEmpty()) {
                    ciap2Repository.saveAll(toSave);
                    log.debug("Batch CIAP-2 processado: {}-{} ({} itens)", i + 1, fim, toSave.size());
                }
            } catch (Exception e) {
                log.error("Erro ao processar batch CIAP-2 ({}-{}): {}", i + 1, fim, e.getMessage());
                falhas += batch.size();
            }
        }

        String mensagemErro = falhas > 0 ? "Alguns itens falharam na sincronização" : null;
        syncLogService.concluirSincronizacao(logEntry.getId(), concepts.size(), criados, atualizados, falhas,
                mensagemErro);

        log.info("Sincronização CIAP-2 concluída: {} processados, {} criados, {} atualizados, {} falhas",
                concepts.size(), criados, atualizados, falhas);
        return new SyncResult(FhirResourceNames.CIAP2, concepts.size(), criados, atualizados, falhas);
    }

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Buscando conceitos para recurso: {}", recurso);
        List<ConceptDTO> concepts = new ArrayList<>();

        try {
            ValueSetDTO valueSet = fhirClient.getValueSet(recurso);
            if (valueSet != null && valueSet.getCompose() != null && valueSet.getCompose().getInclude() != null) {
                for (ValueSetDTO.IncludeDTO include : valueSet.getCompose().getInclude()) {
                    if (include.getConcept() != null && !include.getConcept().isEmpty()) {
                        for (ConceptReferenceDTO ref : include.getConcept()) {
                            concepts.add(ConceptDTO.builder()
                                    .code(ref.getCode())
                                    .display(ref.getDisplay())
                                    .build());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Falha ao buscar ValueSet {}: {}. Tentando CodeSystem...", recurso, e.getMessage());
        }

        if (concepts.isEmpty()) {
            try {
                CodeSystemDTO codeSystem = fhirClient.getCodeSystem(recurso);
                if (codeSystem != null && codeSystem.getConcept() != null) {
                    concepts.addAll(codeSystem.getConcept());
                }
            } catch (Exception e) {
                log.error("Falha ao buscar CodeSystem {}: {}", recurso, e.getMessage());
            }
        }

        log.info("Total de conceitos encontrados para {}: {}", recurso, concepts.size());
        return concepts;
    }
}
