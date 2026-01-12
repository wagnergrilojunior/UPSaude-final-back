package com.upsaude.integration.fhir.service.diagnostico;

import com.upsaude.entity.referencia.Ciap2;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.entity.fhir.FhirSyncLog;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosticoSyncService {

    private final FhirClient fhirClient;
    private final FhirSyncLogService syncLogService;

    private final Cid10SubcategoriasRepository cid10Repository;
    private final Ciap2Repository ciap2Repository;

    public record SyncResult(String recurso, int processados, int criados, int atualizados, int falhas) {
    }

    @Transactional
    public SyncResult syncCid10() {
        log.info("Iniciando sincronização de CID-10 via FHIR (BRCID10)");
        FhirSyncLog logEntry = syncLogService.iniciarSincronizacao("BRCID10", null);

        List<ConceptDTO> concepts = fetchConcepts("BRCID10");

        int criados = 0;
        int atualizados = 0;
        int falhas = 0;

        for (ConceptDTO concept : concepts) {
            try {
                Cid10Subcategorias cid = cid10Repository.findBySubcat(concept.getCode())
                        .orElse(new Cid10Subcategorias());

                boolean isNew = cid.getId() == null;
                cid.setSubcat(concept.getCode());
                cid.setDescricao(concept.getDisplay());
                cid.setActive(true);
                cid.setDataSincronizacao(OffsetDateTime.now());

                cid10Repository.save(cid);
                if (isNew)
                    criados++;
                else
                    atualizados++;
            } catch (Exception e) {
                log.error("Erro ao sincronizar CID {}: {}", concept.getCode(), e.getMessage());
                falhas++;
            }
        }

        syncLogService.concluirSincronizacao(logEntry.getId(), concepts.size(), criados, atualizados);
        if (falhas > 0) {
            syncLogService.registrarErro(logEntry.getId(), "Alguns itens falharam na sincronização", falhas);
        }

        return new SyncResult("BRCID10", concepts.size(), criados, atualizados, falhas);
    }

    @Transactional
    public SyncResult syncCiap2() {
        log.info("Iniciando sincronização de CIAP-2 via FHIR (BRCIAP2)");
        FhirSyncLog logEntry = syncLogService.iniciarSincronizacao("BRCIAP2", null);

        List<ConceptDTO> concepts = fetchConcepts("BRCIAP2");

        int criados = 0;
        int atualizados = 0;
        int falhas = 0;

        for (ConceptDTO concept : concepts) {
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

                ciap2Repository.save(ciap);
                if (isNew)
                    criados++;
                else
                    atualizados++;
            } catch (Exception e) {
                log.error("Erro ao sincronizar CIAP {}: {}", concept.getCode(), e.getMessage());
                falhas++;
            }
        }

        syncLogService.concluirSincronizacao(logEntry.getId(), concepts.size(), criados, atualizados);
        if (falhas > 0) {
            syncLogService.registrarErro(logEntry.getId(), "Alguns itens falharam na sincronização", falhas);
        }

        return new SyncResult("BRCIAP2", concepts.size(), criados, atualizados, falhas);
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
