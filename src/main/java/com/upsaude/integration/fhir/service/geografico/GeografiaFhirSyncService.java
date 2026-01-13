package com.upsaude.integration.fhir.service.geografico;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.integration.fhir.client.FhirClient;
import com.upsaude.integration.fhir.dto.CodeSystemDTO;
import com.upsaude.integration.fhir.dto.ConceptDTO;
import com.upsaude.integration.fhir.config.FhirResourceNames;
import com.upsaude.integration.fhir.dto.ValueSetDTO;
import com.upsaude.integration.fhir.dto.ValueSetDTO.ConceptReferenceDTO;
import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeografiaFhirSyncService {

    private final FhirClient fhirClient;
    private final FhirSyncLogService syncLogService;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    private static final String RECURSO_DIVISAO_GEOGRAFICA = FhirResourceNames.DIVISAO_GEOGRAFICA_BRASIL;
    private static final String FHIR_CODE_SYSTEM = "http://www.saude.gov.br/fhir/r4/CodeSystem/BRDivisaoGeografica";

    @Transactional
    public FhirSyncLog sincronizarEstados() {
        log.info("Iniciando sincronização de Estados com FHIR BRDivisaoGeografica");
        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao("BRDivisaoGeografica-Estados", null);
        int atualizados = 0;

        try {
            List<ConceptDTO> concepts = fetchConcepts(RECURSO_DIVISAO_GEOGRAFICA);
            log.info("Total de conceitos geográficos encontrados: {}", concepts.size());

            for (ConceptDTO concept : concepts) {
                String code = concept.getCode();

                // Estados têm código de 2 dígitos
                if (code != null && code.length() == 2) {
                    estadosRepository.findByCodigoIbge(code).ifPresent(estado -> {
                        estado.setCodigoFhir(code);
                        estado.setFhirCodeSystem(FHIR_CODE_SYSTEM);
                        estado.setDataUltimaSincronizacaoFhir(OffsetDateTime.now());
                        estadosRepository.save(estado);
                    });
                    atualizados++;
                }
            }

            log.info("Sincronização de Estados concluída. Atualizados: {}", atualizados);
            return syncLogService.concluirSincronizacao(syncLog.getId(), concepts.size(), 0, atualizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar Estados", e);
            return syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
        }
    }

    @Transactional
    public FhirSyncLog sincronizarMunicipios() {
        log.info("Iniciando sincronização de Municípios com FHIR BRDivisaoGeografica");

        // Verificar se há municípios no banco antes de sincronizar
        long totalMunicipios = cidadesRepository.count();
        log.info("Total de municípios no banco de dados: {}", totalMunicipios);

        if (totalMunicipios == 0) {
            String mensagem = "Nenhum município encontrado no banco de dados. " +
                    "É necessário sincronizar os municípios do IBGE primeiro através do endpoint " +
                    "POST /v1/integracoes/ibge/sincronizar/municipios";
            log.warn(mensagem);
            FhirSyncLog syncLog = syncLogService.iniciarSincronizacao("BRDivisaoGeografica-Municipios", null);
            return syncLogService.registrarErro(syncLog.getId(), mensagem, 0);
        }

        FhirSyncLog syncLog = syncLogService.iniciarSincronizacao("BRDivisaoGeografica-Municipios", null);
        int municipiosEncontradosFhir = 0;
        int atualizados = 0;

        try {
            log.info("Buscando conceitos do FHIR para recurso: {}", RECURSO_DIVISAO_GEOGRAFICA);
            List<ConceptDTO> concepts = fetchConcepts(RECURSO_DIVISAO_GEOGRAFICA);
            log.info("Total de conceitos geográficos retornados do FHIR: {}", concepts.size());

            if (concepts.isEmpty()) {
                String mensagem = "Nenhum conceito retornado do servidor FHIR. Verifique a conexão e o recurso: "
                        + RECURSO_DIVISAO_GEOGRAFICA;
                log.warn(mensagem);
                return syncLogService.registrarErro(syncLog.getId(), mensagem, 0);
            }

            for (ConceptDTO concept : concepts) {
                String code = concept.getCode();

                // Municípios têm código de 6 ou 7 dígitos
                if (code != null && (code.length() == 6 || code.length() == 7)) {
                    municipiosEncontradosFhir++;
                    String codigoIbge = code.length() == 7 ? code.substring(0, 6) : code;

                    var cidadeOpt = cidadesRepository.findByCodigoIbge(codigoIbge);
                    if (cidadeOpt.isPresent()) {
                        var cidade = cidadeOpt.get();
                        cidade.setCodigoFhir(code);
                        cidade.setFhirCodeSystem(FHIR_CODE_SYSTEM);
                        cidade.setDataUltimaSincronizacaoFhir(OffsetDateTime.now());

                        // Extrair região de saúde das properties se disponível
                        if (concept.getProperty() != null) {
                            concept.getProperty().forEach(prop -> {
                                if ("regiao_saude".equalsIgnoreCase(prop.getCode())) {
                                    cidade.setRegiaoSaude(prop.getValueString());
                                }
                                if ("macrorregiao_saude".equalsIgnoreCase(prop.getCode())) {
                                    cidade.setMacrorregiaoSaude(prop.getValueString());
                                }
                            });
                        }

                        cidadesRepository.save(cidade);
                        atualizados++;

                        if (atualizados % 500 == 0) {
                            log.info("Progresso: {} municípios atualizados...", atualizados);
                        }
                    } else {
                        if (municipiosEncontradosFhir <= 10) {
                            log.debug("Município com código IBGE {} não encontrado no banco", codigoIbge);
                        }
                    }
                }
            }

            log.info("Sincronização de Municípios concluída:");
            log.info("  - Total de conceitos do FHIR: {}", concepts.size());
            log.info("  - Municípios no FHIR (6-7 dígitos): {}", municipiosEncontradosFhir);
            log.info("  - Municípios atualizados no banco: {}", atualizados);
            log.info("  - Municípios no banco não encontrados no FHIR: {}", municipiosEncontradosFhir - atualizados);

            return syncLogService.concluirSincronizacao(syncLog.getId(), municipiosEncontradosFhir, 0, atualizados);

        } catch (Exception e) {
            log.error("Erro ao sincronizar Municípios", e);
            return syncLogService.registrarErro(syncLog.getId(), e.getMessage(), 1);
        }
    }

    @Transactional
    public List<FhirSyncLog> sincronizarTodos() {
        log.info("Iniciando sincronização completa de Geografia");
        List<FhirSyncLog> logs = new ArrayList<>();
        logs.add(sincronizarEstados());
        logs.add(sincronizarMunicipios());
        return logs;
    }

    public List<ConceptDTO> consultarDivisoesGeograficasExternas() {
        return fetchConcepts(RECURSO_DIVISAO_GEOGRAFICA);
    }

    private List<ConceptDTO> fetchConcepts(String recurso) {
        log.info("Buscando conceitos FHIR para: {}", recurso);

        try {
            log.info("Tentando buscar ValueSet: {}", recurso);
            ValueSetDTO valueSet = fhirClient.getValueSet(recurso);
            List<ConceptDTO> concepts = new ArrayList<>();

            if (valueSet.getCompose() != null && valueSet.getCompose().getInclude() != null) {
                log.info("ValueSet encontrado. Processando includes...");
                valueSet.getCompose().getInclude().forEach(include -> {
                    if (include.getConcept() != null) {
                        log.info("Include encontrado com {} conceitos", include.getConcept().size());
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
                log.info("Total de {} conceitos extraídos do ValueSet", concepts.size());
                return concepts;
            } else {
                log.warn("ValueSet encontrado mas nenhum conceito extraído");
            }
        } catch (Exception e) {
            log.warn("Falha ao buscar ValueSet: {} - {}", e.getClass().getSimpleName(), e.getMessage());
        }

        try {
            log.info("Tentando buscar CodeSystem: {}", recurso);
            CodeSystemDTO codeSystem = fhirClient.getCodeSystem(recurso);
            if (codeSystem.getConcept() != null) {
                log.info("CodeSystem encontrado com {} conceitos", codeSystem.getConcept().size());
                return codeSystem.getConcept();
            } else {
                log.warn("CodeSystem encontrado mas sem conceitos");
            }
        } catch (Exception e) {
            log.error("Falha ao buscar CodeSystem: {} - {}", e.getClass().getSimpleName(), e.getMessage());
        }

        log.error("Nenhum conceito encontrado para o recurso: {}", recurso);
        return new ArrayList<>();
    }
}
